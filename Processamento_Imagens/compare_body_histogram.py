# https://www.geeksforgeeks.org/measure-similarity-between-images-using-python-opencv/

import cv2

# test image
image = cv2.imread('../../Documentos/videos_cameras/frame0body0.png')
gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
histogram = cv2.calcHist([gray_image], [0], None, [256], [0, 256])

# data1 image
image = cv2.imread('../../Documentos/videos_cameras/frame1body0.png')
gray_image1 = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
histogram1 = cv2.calcHist([gray_image1], [0], None, [256], [0, 256])

difference = 0

# Euclidean Distance between data1 and test
i = 0
while i < len(histogram) and i < len(histogram1):
	difference += (histogram[i] - histogram1[i]) ** 2
	i += 1
difference = difference ** (1 / 2)

print(difference)

if difference < 5000:
	print("Probably the same person.")
