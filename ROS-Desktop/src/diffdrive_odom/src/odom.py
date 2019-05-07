#!/usr/bin/env python

import math
from math import sin, cos, pi

import rospy
import tf
from nav_msgs.msg import Odometry
from std_msgs.msg import Float32
from geometry_msgs.msg import Point, Pose, Quaternion, Twist, Vector3\

def lwheel_angular_vel_enc_callback( msg):
    lwheel_angular_vel_enc = msg.data

def rwheel_angular_vel_enc_callback( msg):
    rwheel_angular_vel_enc = msg.data


rospy.init_node('odometry_publisher')

lengthBetweenTwoWheels = 0.225;

odom_pub = rospy.Publisher("odom", Odometry, queue_size=50)
lwheel_angular_vel_enc_sub = rospy.Subscriber('lwheel_angular_vel_enc', Float32,lwheel_angular_vel_enc_callback)    
rwheel_angular_vel_enc_sub = rospy.Subscriber('rwheel_angular_vel_enc', Float32,rwheel_angular_vel_enc_callback) 
odom_broadcaster = tf.TransformBroadcaster()

x = 0.0
y = 0.0
th = 0.0

vx = 0.0
vy = 0.0
vth = 0.0
lwheel_angular_vel_enc = 0
rwheel_angular_vel_enc = 0
v_left = 0
v_right = 0

current_time = rospy.Time.now()
last_time = rospy.Time.now()


r = rospy.Rate(1.0)
while not rospy.is_shutdown():
    current_time = rospy.Time.now()
    
    v_left = (lwheel_angular_vel_enc*.03) / (2 * 3.14)
    v_right = (rwheel_angular_vel_enc*.03) / (2 * 3.14)
    
    vx = (v_left + v_right) / 2
    vy = 0.0
    vth = (v_left - v_right) / lengthBetweenTwoWheels

    # compute odometry in a typical way given the velocities of the robot
    dt = (current_time - last_time).to_sec()
    delta_x = (vx * cos(th) - vy * sin(th)) * dt
    delta_y = (vx * sin(th) + vy * cos(th)) * dt
    delta_th = vth * dt

    x += delta_x
    y += delta_y
    th += delta_th

    # since all odometry is 6DOF we'll need a quaternion created from yaw
    odom_quat = tf.transformations.quaternion_from_euler(0, 0, th)

    # first, we'll publish the transform over tf
    odom_broadcaster.sendTransform(
        (x, y, 0.),
        odom_quat,
        current_time,
        "base_link",
        "odom"
    )

    # next, we'll publish the odometry message over ROS
    odom = Odometry()
    odom.header.stamp = current_time
    odom.header.frame_id = "odom"

    # set the position
    odom.pose.pose = Pose(Point(x, y, 0.), Quaternion(*odom_quat))

    # set the velocity
    odom.child_frame_id = "base_link"
    odom.twist.twist = Twist(Vector3(vx, vy, 0), Vector3(0, 0, vth))

    # publish the message
    odom_pub.publish(odom)

    last_time = current_time
r.sleep()
