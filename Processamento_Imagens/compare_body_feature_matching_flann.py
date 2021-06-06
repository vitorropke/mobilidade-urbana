# https://docs.opencv.org/master/dc/dc3/tutorial_py_matcher.html

import cv2 as cv
import matplotlib.pyplot as plt
import sys

openFile = ""

img1 = cv.imread('../Arquivos/Imagens/frames/bones/frame0body0.png', cv.IMREAD_GRAYSCALE)  # queryImage

# Initiate SIFT detector
sift = cv.SIFT_create()

kp1, des1 = sift.detectAndCompute(img1, None)

# Verify if the image has not points
if des1 is None:
	print("Image 1 Null")
	sys.exit()

# FLANN parameters
FLANN_INDEX_KDTREE = 1

index_params = dict(algorithm=FLANN_INDEX_KDTREE, trees=5)
search_params = dict(checks=1)  # or pass empty dictionary

flann = cv.FlannBasedMatcher(index_params, search_params)

# Start on the maximum frame and goes until frame 1
imageNumber = 1799
while imageNumber > 0:
	bodyNumber = 0
	while True:
		# Try to access the file
		try:
			openFile = open('../Arquivos/Imagens/frames1/bones/frame{}body{}.png'.format(imageNumber, bodyNumber))
			
			img2 = cv.imread('../Arquivos/Imagens/frames1/bones/frame{}body{}.png'.format(imageNumber, bodyNumber),
			                 cv.IMREAD_GRAYSCALE)  # trainImage
			
			# find the keypoints and descriptors with SIFT
			kp2, des2 = sift.detectAndCompute(img2, None)
			
			if des2 is not None:
				matches = flann.knnMatch(des1, des2, k=2)
				
				# Need to draw only good matches, so create a mask
				matchesMask = [[0, 0] for i in range(len(matches))]
				
				
				# ratio test as per Lowe's paper
				for i, (m, n) in enumerate(matches):
					if m.distance < 0.7 * n.distance:
						matchesMask[i] = [1, 0]
				
				draw_params = dict(matchColor=(0, 255, 0), singlePointColor=(255, 0, 0), matchesMask=matchesMask,
				                   flags=cv.DrawMatchesFlags_DEFAULT)
				
				img3 = cv.drawMatchesKnn(img1, kp1, img2, kp2, matches, None, **draw_params)
				
				#plt.imshow(img3, ), plt.show()
				#cv.waitKey(0)
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