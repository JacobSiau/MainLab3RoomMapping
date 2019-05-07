#!/usr/bin/env python
import RPi.GPIO as GPIO #documentation https://sourceforge.net/p/raspberry-gpio-python/wiki/Examples/
import time
import rospy
from std_msgs.msg import String
from std_msgs.msg import Float32

LencB = 38
LencA = 40
countL = 0
dirL = -1

GPIO.setmode(GPIO.BOARD)#set pin locations
GPIO.setup(LencA, GPIO.IN)#set pins as inputs
GPIO.setup(LencB, GPIO.IN)



def callbackL(channelA):
    global countL
    global dirL
    countL = countL + 1
    if(GPIO.input(LencB)):
	dirL = -1
    else:
	dirL = 1
GPIO.add_event_detect(LencA, GPIO.RISING, callback=callbackL)

def talker ():
	Lenc_pub = rospy.Publisher('lwheel_enc_count', Float32, queue_size=10)
	dirL_pub = rospy.Publisher('ldirection', Float32, queue_size=10)
	rospy.init_node('lencoder', anonymous=True)
	rate = rospy.Rate(5) #10Hz
	while not rospy.is_shutdown():
		rospy.loginfo("lencoder")
		Lenc_pub.publish(countL)
		dirL_pub.publish(dirL)
		rate.sleep()
	


if __name__ == '__main__':

	try:
		talker()
	except rospy.ROSInterruptException:
		pass
