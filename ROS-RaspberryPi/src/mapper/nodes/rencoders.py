#!/usr/bin/env python
import RPi.GPIO as GPIO #documentation https://sourceforge.net/p/raspberry-gpio-python/wiki/Examples/
import time
import rospy
from std_msgs.msg import String
from std_msgs.msg import Float32

RencB = 19
RencA = 21
countR = 0
dirR = -1

GPIO.setmode(GPIO.BOARD)#set pin locations
GPIO.setup(RencA, GPIO.IN)#set pins as inputs
GPIO.setup(RencB, GPIO.IN)#set pins as inputs



def callbackR(channelA):
    global countR
    global dirR
    countR = countR + 1
    if(GPIO.input(RencB)):
	dirR = -1
    else:
	dirR = 1
GPIO.add_event_detect(RencA, GPIO.RISING, callback=callbackR)

def talker ():
	Renc_pub = rospy.Publisher('rwheel_enc_count', Float32, queue_size=10)
	dirR_pub = rospy.Publisher('rdirection', Float32, queue_size=10)
	rospy.init_node('rencoder', anonymous=True)
	rate = rospy.Rate(5) #10Hz
	while not rospy.is_shutdown():
		rospy.loginfo("rencoder")
		Renc_pub.publish(countR)
		dirR_pub.publish(dirR)
		rate.sleep()
	


if __name__ == '__main__':

	try:
		talker()
	except rospy.ROSInterruptException:
		pass
