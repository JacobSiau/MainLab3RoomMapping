
#!/usr/bin/env python
import RPi.GPIO as GPIO #documentation https://sourceforge.net/p/raspberry-gpio-python/wiki/Examples/
import time


class RoverMove:
	def __init__(self):
		self.Rpin = 5
		self.Lpin = 3
		self.ctrl_list_l = (29,31)
		self.ctrl_list_r = (33,35)
		self.forward = (1,0)
		self.backward = (0,1)
		self.countL = 0
		self.countR = 0

		GPIO.setwarnings(False)
		GPIO.setmode(GPIO.BOARD)#set pin locations
		GPIO.setup(self.Rpin, GPIO.OUT)#set pins as outputs
		GPIO.setup(self.Lpin, GPIO.OUT)
		GPIO.setup(self.ctrl_list_l, GPIO.OUT)
		GPIO.setup(self.ctrl_list_r, GPIO.OUT)

		self.Rpwm = GPIO.PWM(self.Rpin, 500)#freq
		self.Lpwm = GPIO.PWM(self.Lpin, 500)
		self.Rpwm.start(0)#duty cycle
		self.Lpwm.start(0)
	def moveL(self, direction, speed):
		speed = (speed*100)/255 
		self.Lpwm.ChangeDutyCycle(speed)
		if direction == 1:
			GPIO.output(self.ctrl_list_l, self.forward)
		else:
			GPIO.output(self.ctrl_list_l, self.backward)
	def moveR(self, direction, speed):
		speed = (speed*100) / 255
		self.Rpwm.ChangeDutyCycle(speed)
		if direction == 1:
			GPIO.output(self.ctrl_list_r, self.forward)
		else:
			GPIO.output(self.ctrl_list_r, self.backward)

#rovermove = RoverMove()
#rovermove.moveL(1,255)
#rovermove.moveR(0,255)
#time.sleep(2)
#rovermove.moveL(1,100)
#rovermove.moveR(1,100)
#time.sleep(2)
#rovermove.moveL(1,255)
#rovermove.moveR(1,255)
#time.sleep(2)
#rovermove.moveL(1,0)
#rovermove.moveR(1,0)
#time.sleep(2)
		

#GPIO.cleanup()