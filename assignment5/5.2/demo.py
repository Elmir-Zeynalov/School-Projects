"""
Example code to compute the n-th Fibonacci number
and output it to terminal.
"""
import re
import numpy

class Fibonacci (object) :
	def __init__(self) :
		pass

	def __call__(self, i) :
		# Compute the i-th Fibonacci number
		if i==0 :
			return None
		if i==1 or i==2 :
			return 1
		elif i==3 :
			return 2
		number = 1
		numberPrevious = 1
		for k in range(3, i+1) :
			number, numberPrevious = self.nextNumber(number, numberPrevious)
		return number

	def nextNumber(self, ip, ipp) :
		# Compute the next number in the series, given
		# ip  -- the previous number
		# ipp -- the previous previous number
		return ip+ipp, ip

	def outputNumber(self, i) :
		# Print the i-th Fibonacci number
		number = self(i)
		print("The %5d-th Fibonacci number is: %20d" % (i, number))

if __name__ == '__main__' :
	f = Fibonacci()
	for i in range(1, 11) :
		f.outputNumber(i)
