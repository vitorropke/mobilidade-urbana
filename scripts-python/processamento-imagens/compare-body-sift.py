# https://www.analyticsvidhya.com/blog/2019/10/detailed-guide-powerful-sift-technique-image-matching-python/

import cv2
import matplotlib.pyplot as plt
import sys

openFile = ""

# read images
img1 = cv2.imread('../Arquivos/Imagens/frames/bones/frame0body0.png')
img1 = cv2.cvtColor(img1, cv2.COLOR_BGR2GRAY)

# sift
sift = cv2.xfeatures2d.SIFT_create()

keypoints_1, descriptors_1 = sift.detectAndCompute(img1, None)

# Verify if the image has not points
if descriptors_1 is None:
	print("Image 1 Null")
	sys.exit()

# Start on the maximum frame and goes until frame 1
imageNumber = 1799
while imageNumber > 0:
	bodyNumber = 0
	while True:
		# Try to access the file
		try:
			openFile = open('../Arquivos/Imagens/frames1/bones/frame{}body{}.png'.format(imageNumber, bodyNumber))
			
			# img2 = cv2.imread('../Arquivos/Imagens/frames/bones/frame0body0.png')
			img2 = cv2.imread('../Arquivos/Imagens/frames1/bones/frame{}body{}.png'.format(imageNumber, bodyNumber))
			img2 = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)
			
			keypoints_2, descriptors_2 = sift.detectAndCompute(img2, None)
			
			# feature matching
			bf = cv2.BFMatcher(cv2.NORM_L1, crossCheck=True)
			
			if descriptors_2 is not None:
				matches = bf.match(descriptors_1, descriptors_2)
				matches = sorted(matches, key=lambda x: x.distance)
				
				# Print the number of matches
				print("frame{}body{}.png".format(imageNumber, bodyNumber))
				print(len(matches))
				print()
				
				# If the number of matches is bigger than 90, you find the person
				if len(matches) > 90:
					img3 = cv2.drawMatches(img1, keypoints_1, img2, keypoints_2, matches[:50], img2, flags=2)
					plt.imshow(img3), plt.show()
					cv2.waitKey(0)
					#sys.exit()
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
	
	imageNumber -= 1

print("Not found")
