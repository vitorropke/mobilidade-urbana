# https://docs.opencv.org/master/dc/dc3/tutorial_py_matcher.html

import cv2 as cv
import matplotlib.pyplot as plt
import sys

openFile = ""

img1 = cv.imread('../Arquivos/Imagens/frames/bones/frame0body0.png', cv.IMREAD_GRAYSCALE)  # queryImage

# Initiate ORB detector
orb = cv.ORB_create()

# create BFMatcher object
bf = cv.BFMatcher(cv.NORM_HAMMING, crossCheck=True)

kp1, des1 = orb.detectAndCompute(img1, None)

if des1 is None:
	print("Image 1 Null")
	sys.exit()

# Start on the maximum frame and goes regressive until frame 1
for imageNumber in range(1799, 0, -1):
	bodyNumber = 0
	while True:
		# Try to access the file
		try:
			openFile = open('../Arquivos/Imagens/frames1/bones/frame{}body{}.png'.format(imageNumber, bodyNumber))
			
			img2 = cv.imread('../Arquivos/Imagens/frames1/bones/frame{}body{}.png'.format(imageNumber, bodyNumber),
			                 cv.IMREAD_GRAYSCALE)  # trainImage
			
			# find the keypoints and descriptors with ORB
			kp2, des2 = orb.detectAndCompute(img2, None)
			
			if des2 is not None:
				# Match descriptors.
				matches = bf.match(des1, des2)
				
				# Print the number of matches
				print("frame{}body{}.png".format(imageNumber, bodyNumber))
				print(len(matches))
				print()
				
				# If the number of matches is bigger than 100, you find the person
				if len(matches) > 100:
					# Sort them in the order of their distance.
					matches = sorted(matches, key=lambda x: x.distance)
					
					# Draw first 10 matches.
					img3 = cv.drawMatches(img1, kp1, img2, kp2, matches[:10], None,
					                      flags=cv.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS)
					
					plt.imshow(img3), plt.show()
					cv.waitKey(0)
			else:
				print("Image 2 Null")
		
		# If the file doesn't exist, go to the next frame
		except FileNotFoundError:
			print("../Arquivos/Imagens/frames1/bones/frame{}body{}.png\nFile not accessible\n\n".format(imageNumber,
			                                                                                            bodyNumber))
			break
		finally:
			openFile.close()
			bodyNumber += 1

print("Not found")
