#!/usr/bin/python
#NOTES----------------------------------------
# ADD lticks_per_rot and rticks_per_rot to the lauch file
# R needs to be in meters as a float in launch
# gopigo needs to be changed to run in launch true/false
# everything changed has a comment -JP





import rospy
import roslib

import math 
import numpy

# Messages
from std_msgs.msg import Float32

# Query GoPiGo robot for left and right wheel encoders.
# Publish the estimated left and right angular wheel velocities
class WheelEncoderPublisher:
  def __init__(self):
    rospy.init_node('gopigo_state_updater')
    # Read in tangential velocity targets
    self.lwheel_angular_vel_motor_sub = rospy.Subscriber('lwheel_angular_vel_motor', Float32, self.lwheel_angular_vel_motor_callback)
    self.rwheel_angular_vel_motor_sub = rospy.Subscriber('rwheel_angular_vel_motor', Float32, self.rwheel_angular_vel_motor_callback)

    self.lwheel_enc_count_sub = rospy.Subscriber('lwheel_enc_count', Float32, self.lwheel_enc_count_callback)           #THIS IS THE TOPICS WE CREATED -JP
    self.rwheel_enc_count_sub = rospy.Subscriber('rwheel_enc_count', Float32, self.rwheel_enc_count_callback)


    self.rdirection_sub = rospy.Subscriber('rdirection', Float32,self.rdirection_callback)           #THIS IS THE TOPICS WE CREATED -JP
    self.ldirection_sub = rospy.Subscriber('ldirection', Float32,self.ldirection_callback)
    

    self.lwheel_angular_vel_control_pub = rospy.Subscriber('lwheel_angular_vel_control', Float32, self.lwheel_angular_vel_control_callback)
    self.rwheel_angular_vel_control_pub = rospy.Subscriber('rwheel_angular_vel_control', Float32, self.rwheel_angular_vel_control_callback)  


    self.lwheel_angular_vel_enc_pub = rospy.Publisher('lwheel_angular_vel_enc', Float32, queue_size=10)
    self.rwheel_angular_vel_enc_pub = rospy.Publisher('rwheel_angular_vel_enc', Float32, queue_size=10)
    self.rate = rospy.get_param('~rate', 10)
    self.err_tick_incr = rospy.get_param('~err_tick_incr',20) # Filter out clearly erroneous encoder readings
    self.time_prev_update = rospy.Time.now();
    self.rover_on = rospy.get_param('~rover_on',True)   #controls sim/actual run -JP
    self.lticks_per_rot = rospy.get_param('~lticks_per_rot',84.0) # -JP
    self.rticks_per_rot = rospy.get_param('~rticks_per_rot',84.0) # -JP

    if self.rover_on:
      #import gopigo   
      self.lwheel_encs = [0]*5    # initialize to zero  -JP 
      self.rwheel_encs = [0]*5
      self.lwheel_enc_count = 0
      self.rwheel_enc_count = 0

    self.R = rospy.get_param('~robot_wheel_radius', .03)

    # Need a little hack to incorporate direction wheels are spinning
    self.lwheel_dir = 1
    self.rwheel_dir = 1                  #change this to grab from topic down below if we want ours from our topic!!! -JP
    self.rwheel_angular_vel_control = 0
    self.lwheel_angular_vel_control = 0

  # Really bad hack to get motor spin direction
  def lwheel_angular_vel_motor_callback(self,msg):    #this just assumes that if the command is negative the rover is actually moving backwards
    if msg.data >= 0: self.lwheel_dir = 1
    else: self.lwheel_dir = -1
  # Really bad hack to get motor spin direction
  def rwheel_angular_vel_motor_callback(self,msg):
    if msg.data >= 0: self.rwheel_dir = 1
    else: self.rwheel_dir = -1
  def lwheel_angular_vel_control_callback(self,msg):
    self.lwheel_angular_vel_control = msg.data
  def rwheel_angular_vel_control_callback(self,msg):
    self.rwheel_angular_vel_control = msg.data

  def rdirection_callback(self,msg):   #call backs for direction!!!!!!!!!!! -JP
    self.rdirection = msg.data

  def ldirection_callback(self,msg):   #call backs for direction!!!!!!!!!!! -JP
    self.ldirection = msg.data

  def lwheel_enc_count_callback(self,msg): #Callbacks for enc count!!!!!!!!!!!!!! -JP
    self.lwheel_enc_count = msg.data

  def rwheel_enc_count_callback(self,msg):
    self.rwheel_enc_count = msg.data

  def enc_2_rads(self,enc_cms):
    prop_revolution = (enc_cms) / (2.0*math.pi*self.R)
    rads =  prop_revolution * (2*math.pi)
    return rads

  def update(self):
    if self.rover_on: # Running on actual robot
      #import gopigo
      lwheel_enc = self.lwheel_dir * (self.lwheel_enc_count / self.lticks_per_rot) * (2 * math.pi) #* .01 # cm's moved
      rwheel_enc = self.lwheel_dir * (self.rwheel_enc_count / self.rticks_per_rot) * (2 * math.pi) #* .01 # cm's moved    distance traveled in total cm -JP 

      self.lwheel_encs = self.lwheel_encs[1:] + [lwheel_enc]      #basically adds next encoder reading and deletes earlier list elements  -JP
      self.rwheel_encs = self.rwheel_encs[1:] + [rwheel_enc]

      # History of past three encoder reading
      time_curr_update = rospy.Time.now()
      dt = (time_curr_update - self.time_prev_update).to_sec()

      # Compute angular velocity in rad/s
      lwheel_enc_delta = abs(self.lwheel_encs[-1]) - abs(self.lwheel_encs[-2])    #takes 2nd last element - last element!!!!!!! -JP
      rwheel_enc_delta = abs(self.rwheel_encs[-1]) - abs(self.rwheel_encs[-2])    #delta is the change in encoder count
      lwheel_angular_vel_enc = lwheel_enc_delta / dt
      rwheel_angular_vel_enc = rwheel_enc_delta / dt

      # Adjust sign
      if self.lwheel_encs[-1] < 0: lwheel_angular_vel_enc = -lwheel_angular_vel_enc
      if self.rwheel_encs[-1] < 0: rwheel_angular_vel_enc = -rwheel_angular_vel_enc
      self.lwheel_angular_vel_enc_pub.publish(lwheel_angular_vel_enc)
      self.rwheel_angular_vel_enc_pub.publish(rwheel_angular_vel_enc)

      self.time_prev_update = time_curr_update

    else: # Running in simulation -- blindly copy from target assuming perfect execution
      self.lwheel_angular_vel_enc_pub.publish(self.lwheel_angular_vel_control)
      self.rwheel_angular_vel_enc_pub.publish(self.rwheel_angular_vel_control)
      

  def spin(self):
    rospy.loginfo("Start gopigo_state_updater")
    rate = rospy.Rate(self.rate)
    rospy.on_shutdown(self.shutdown)

    while not rospy.is_shutdown():
      self.update();
      rate.sleep()
    rospy.spin()

  def shutdown(self):
    rospy.loginfo("Stop gopigo_state_updater")
    # Stop message
    self.lwheel_angular_vel_enc_pub.publish(0)
    self.rwheel_angular_vel_enc_pub.publish(0)
    rospy.sleep(1)

def main():
  encoder_publisher = WheelEncoderPublisher();
  encoder_publisher.spin()

if __name__ == '__main__':
  main(); 


