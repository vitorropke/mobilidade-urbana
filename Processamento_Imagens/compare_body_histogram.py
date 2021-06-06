# https://www.geeksforgeeks.org/measure-similarity-between-images-using-python-opencv/

import cv2
import sys

openFile = ""

# test image
image = cv2.imread('../Arquivos/Imagens/frames/bones/frame0body0.png')
gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
histogram = cv2.calcHist([gray_image], [0], None, [256], [0, 256])

# Start on the maximum frame and goes until frame 1
imageNumber = 1799
while imageNumber > 0:
	bodyNumber = 0
	while True:
		# Try to access the file
		try:
			openFile = open('../Arquivos/Imagens/frames1/bones/frame{}body{}.png'.format(imageNumber, bodyNumber))
			
			# data1 image
			image = cv2.imread('../Arquivos/Imagens/frames1/bones/frame{}body{}.png'.format(imageNumber, bodyNumber))
			gray_image1 = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
			histogram1 = cv2.calcHist([gray_image1], [0], None, [256], [0, 256])
			
			difference = 0
			
			# Euclidean Distance between data1 and test
			i = 0
			while i < len(histogram) and i < len(histogram1):
				difference += (histogram[i] - histogram1[i]) ** 2
				i += 1
			difference = difference ** (1 / 2)
			
			print("frame{}body{}.png".format(imageNumber, bodyNumber))
			print(difference)
			print()
			
			if difference < 5000:
				print("Probably the same person.")
				cv2.imshow('../Arquivos/Imagens/frames/bones/frame0body0.png', gray_image)
				cv2.imshow('../Arquivos/Imagens/frames1/bones/frame{}body{}.png', gray_image1)  # show frame on window
				cv2.waitKey(0)
				#sys.exit()
		
		# If the file doesn't exist, go to the next frame
		except FileNotFoundError:
			print("../Arquivos/Imagens/frames1/bones/frame{}body{}.png\nFile not accessible\n\n".format(imageNumber, bodyNumber))
			break
		finally:
			openFile.close()
			bodyNumber += 1
	
	imageNumber -= 1

print("Not found")
