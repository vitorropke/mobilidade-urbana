# From Python
# It requires OpenCV installed for Python
import sys
import cv2
import os
from sys import platform
import argparse
import numpy as np

try:
	# Import Openpose (Windows/Ubuntu/OSX)
	dir_path = os.path.dirname(os.path.realpath(__file__))
	try:
		# Windows Import
		if platform == "win32":
			# Change these variables to point to the correct folder (Release/x64 etc.)
			sys.path.append(dir_path + '/../../Programas/openpose/python/openpose/Release')
			os.environ['PATH'] = os.environ['PATH'] + ';' + dir_path + '/../../x64/Release;' + dir_path + '/../../bin; '
		# import pyopenpose as op
		else:
			# Change these variables to point to the correct folder (Release/x64 etc.) sys.path.append(
			# '../../python'); If you run `make install` (default path is `/usr/local/python` for Ubuntu),
			# you can also access the OpenPose/python module from there. This will install OpenPose and the python
			# library at your desired installation path. Ensure that this is in your python path in order to use it.
			sys.path.append('/usr/local/python')
			from openpose import pyopenpose as op
	except ImportError as e:
		print('Error: OpenPose library could not be found. Did you enable `BUILD_PYTHON` in CMake and have this '
		      'Python script in the right folder?')
		raise e

	# Flags
	parser = argparse.ArgumentParser()
	parser.add_argument("--image_path", default="../../Programas/openpose/examples/media/COCO_val2014_000000000241.jpg",
	                    help="Process an image. "
	                         "Read all standard "
	                         "formats (jpg, png, "
	                         "bmp, etc.).")
	args = parser.parse_known_args()

	# Custom Params (refer to include/openpose/flags.hpp for more parameters)
	params = dict()
	params["model_folder"] = "../../Programas/openpose/models/"

	# Add others in path?
	for i in range(0, len(args[1])):
		curr_item = args[1][i]
		if i != len(args[1]) - 1:
			next_item = args[1][i + 1]
		else:
			next_item = "1"
		if "--" in curr_item and "--" in next_item:
			key = curr_item.replace('-', '')
			if key not in params:
				params[key] = "1"
		elif "--" in curr_item and "--" not in next_item:
			key = curr_item.replace('-', '')
			if key not in params:
				params[key] = next_item

	# Construct it from system arguments
	# op.init_argv(args[1])
	# oppython = op.OpenposePython()

	# Starting OpenPose
	opWrapper = op.WrapperPython()
	opWrapper.configure(params)
	opWrapper.start()

	# Process Image
	datum = op.Datum()
	imageToProcess = cv2.imread(args[0].image_path)
	datum.cvInputData = imageToProcess
	opWrapper.emplaceAndPop([datum])

	# Save the coordinates in a var
	coordinatesKeyPoints = datum.poseKeypoints

	# .shape take de dimensions of numpy.ndarray
	bodiesNumber = coordinatesKeyPoints.shape[0]

	# Define the coordinates that will be used for the polygon
	coordinatesKeyPointsPolygon = [[[0 for cartesianCoordinates in range(2)] for keyPoints in range(25)] for
	                               bodies in range(bodiesNumber)]

	# Swap the points to match with the polygon
	for bodies in range(bodiesNumber):
		coordinatesKeyPointsPolygon[bodies][0] = coordinatesKeyPoints[bodies][17]  # 0 -> 17
		coordinatesKeyPointsPolygon[bodies][1] = coordinatesKeyPoints[bodies][17]  # 1 -> 17
		coordinatesKeyPointsPolygon[bodies][2] = coordinatesKeyPoints[bodies][2]  # 2 -> 2
		coordinatesKeyPointsPolygon[bodies][3] = coordinatesKeyPoints[bodies][3]  # 3 -> 3
		coordinatesKeyPointsPolygon[bodies][4] = coordinatesKeyPoints[bodies][4]  # 4 -> 4
		coordinatesKeyPointsPolygon[bodies][5] = coordinatesKeyPoints[bodies][9]  # 5 -> 9
		coordinatesKeyPointsPolygon[bodies][6] = coordinatesKeyPoints[bodies][10]  # 6 -> 10
		coordinatesKeyPointsPolygon[bodies][7] = coordinatesKeyPoints[bodies][11]  # 7 -> 11
		coordinatesKeyPointsPolygon[bodies][8] = coordinatesKeyPoints[bodies][23]  # 8 -> 23
		coordinatesKeyPointsPolygon[bodies][9] = coordinatesKeyPoints[bodies][22]  # 9 -> 22
		coordinatesKeyPointsPolygon[bodies][10] = coordinatesKeyPoints[bodies][24]  # 10 -> 24
		coordinatesKeyPointsPolygon[bodies][11] = coordinatesKeyPoints[bodies][21]  # 11 -> 21
		coordinatesKeyPointsPolygon[bodies][12] = coordinatesKeyPoints[bodies][19]  # 12 -> 19
		coordinatesKeyPointsPolygon[bodies][13] = coordinatesKeyPoints[bodies][20]  # 13 -> 20
		coordinatesKeyPointsPolygon[bodies][14] = coordinatesKeyPoints[bodies][14]  # 14 -> 14
		coordinatesKeyPointsPolygon[bodies][15] = coordinatesKeyPoints[bodies][13]  # 15 -> 13
		coordinatesKeyPointsPolygon[bodies][16] = coordinatesKeyPoints[bodies][12]  # 16 -> 12
		coordinatesKeyPointsPolygon[bodies][17] = coordinatesKeyPoints[bodies][7]  # 17 -> 7
		coordinatesKeyPointsPolygon[bodies][18] = coordinatesKeyPoints[bodies][6]  # 18 -> 6
		coordinatesKeyPointsPolygon[bodies][19] = coordinatesKeyPoints[bodies][5]  # 19 -> 5
		coordinatesKeyPointsPolygon[bodies][20] = coordinatesKeyPoints[bodies][18]  # 20 -> 18
		coordinatesKeyPointsPolygon[bodies][21] = coordinatesKeyPoints[bodies][16]  # 21 -> 16
		coordinatesKeyPointsPolygon[bodies][22] = coordinatesKeyPoints[bodies][15]  # 22 -> 15
		coordinatesKeyPointsPolygon[bodies][23] = coordinatesKeyPoints[bodies][17]  # 23 -> 17
		coordinatesKeyPointsPolygon[bodies][24] = coordinatesKeyPoints[bodies][17]  # 24 -> 17

		# Resize some things

		# Resize the head
		# any means for any coordinate X or Y of the actual keyPoint
		if coordinatesKeyPointsPolygon[bodies][20].any() > 0:
			coordinatesKeyPointsPolygon[bodies][20][1] -= 20
		if coordinatesKeyPointsPolygon[bodies][21].any() > 0:
			coordinatesKeyPointsPolygon[bodies][21][1] -= 30
		if coordinatesKeyPointsPolygon[bodies][22].any() > 0:
			coordinatesKeyPointsPolygon[bodies][22][1] -= 30
		if coordinatesKeyPointsPolygon[bodies][23].any() > 0:
			coordinatesKeyPointsPolygon[bodies][23][1] -= 20
		"""
		# Resize the foot
		if coordinatesKeyPointsPolygon[bodies][7].any() != 0 or coordinatesKeyPointsPolygon[bodies][14].any() != 0:
			if coordinatesKeyPointsPolygon[bodies][7][0] > coordinatesKeyPointsPolygon[bodies][14][0]:  # the body is
				# on it backwards
				coordinatesKeyPointsPolygon[bodies][7][0] += 5
				coordinatesKeyPointsPolygon[bodies][7][1] -= 5

				if coordinatesKeyPointsPolygon[bodies][8].any() != 0:
					coordinatesKeyPointsPolygon[bodies][8][0] += 5
					coordinatesKeyPointsPolygon[bodies][8][1] -= 5
				if coordinatesKeyPointsPolygon[bodies][9].any() != 0:
					coordinatesKeyPointsPolygon[bodies][9][1] += 5
				if coordinatesKeyPointsPolygon[bodies][10].any() != 0:
					coordinatesKeyPointsPolygon[bodies][10][0] -= 5
					coordinatesKeyPointsPolygon[bodies][10][1] -= 5
				if coordinatesKeyPointsPolygon[bodies][11].any() != 0:
					coordinatesKeyPointsPolygon[bodies][11][0] += 5
					coordinatesKeyPointsPolygon[bodies][11][1] -= 5
				if coordinatesKeyPointsPolygon[bodies][12].any() != 0:
					coordinatesKeyPointsPolygon[bodies][12][1] += 5
				if coordinatesKeyPointsPolygon[bodies][13].any() != 0:
					coordinatesKeyPointsPolygon[bodies][13][0] -= 5
					coordinatesKeyPointsPolygon[bodies][13][1] += 5

				coordinatesKeyPointsPolygon[bodies][14][0] -= 5
				coordinatesKeyPointsPolygon[bodies][14][1] += 5
			else:
				coordinatesKeyPointsPolygon[bodies][7][0] -= 5
				coordinatesKeyPointsPolygon[bodies][7][1] -= 5

				if coordinatesKeyPointsPolygon[bodies][8].any() != 0:
					coordinatesKeyPointsPolygon[bodies][8][0] -= 5
					coordinatesKeyPointsPolygon[bodies][8][1] -= 5
				if coordinatesKeyPointsPolygon[bodies][9].any() != 0:
					coordinatesKeyPointsPolygon[bodies][9][1] += 5
				if coordinatesKeyPointsPolygon[bodies][10].any() != 0:
					coordinatesKeyPointsPolygon[bodies][10][0] += 5
					coordinatesKeyPointsPolygon[bodies][10][1] += 5
				if coordinatesKeyPointsPolygon[bodies][11].any() != 0:
					coordinatesKeyPointsPolygon[bodies][11][0] -= 5
					coordinatesKeyPointsPolygon[bodies][11][1] += 5
				if coordinatesKeyPointsPolygon[bodies][12].any() != 0:
					coordinatesKeyPointsPolygon[bodies][12][1] += 5
				if coordinatesKeyPointsPolygon[bodies][13].any() != 0:
					coordinatesKeyPointsPolygon[bodies][13][0] += 5
					coordinatesKeyPointsPolygon[bodies][13][1] -= 5

				coordinatesKeyPointsPolygon[bodies][14][0] += 5
				coordinatesKeyPointsPolygon[bodies][14][1] -= 5
		"""
		backWards = False
		nonZeroTruck = False
		# Resize the middle
		for leftPoints in range(2, 7):  # Search for a non zero value on the left of the body
			if coordinatesKeyPointsPolygon[bodies][leftPoints].any() != 0:
				for rightPoints in range(15, 20):  # Search for a non zero value on the right of the body
					if coordinatesKeyPointsPolygon[bodies][rightPoints].any() != 0:
						if coordinatesKeyPointsPolygon[bodies][rightPoints][0] > \
								coordinatesKeyPointsPolygon[bodies][leftPoints][0]:
							nonZeroTruck = True
						else:
							nonZeroTruck = True
							backWards = True

					if nonZeroTruck:  # Quit the inner loop if already found the orientation
						break

			if nonZeroTruck:  # Quit the outer loop if already found the orientation
				break

		# Make the resize of the middle
		if backWards:  # If the body is backwards
			for leftPoints in range(2, 7):
				if coordinatesKeyPointsPolygon[bodies][leftPoints].any() != 0:
					coordinatesKeyPointsPolygon[bodies][leftPoints][0] += 5
					coordinatesKeyPointsPolygon[bodies][leftPoints][1] -= 5
				if leftPoints == 5:
					coordinatesKeyPointsPolygon[bodies][leftPoints][1] += 10

			for leftPoints in range(15, 20):
				if coordinatesKeyPointsPolygon[bodies][leftPoints].any() != 0:
					coordinatesKeyPointsPolygon[bodies][leftPoints][0] -= 5
					coordinatesKeyPointsPolygon[bodies][leftPoints][1] -= 5
				if leftPoints == 16:
					coordinatesKeyPointsPolygon[bodies][leftPoints][1] += 10
		else:  # If the body is on front
			for leftPoints in range(2, 7):
				if coordinatesKeyPointsPolygon[bodies][leftPoints].any() != 0:
					coordinatesKeyPointsPolygon[bodies][leftPoints][0] -= 5
					coordinatesKeyPointsPolygon[bodies][leftPoints][1] -= 5
				if leftPoints == 5:
					coordinatesKeyPointsPolygon[bodies][leftPoints][1] += 10

			for leftPoints in range(15, 20):
				if coordinatesKeyPointsPolygon[bodies][leftPoints].any() != 0:
					coordinatesKeyPointsPolygon[bodies][leftPoints][0] += 5
					coordinatesKeyPointsPolygon[bodies][leftPoints][1] -= 5
				if leftPoints == 16:
					coordinatesKeyPointsPolygon[bodies][leftPoints][1] += 10

	for bodies in range(bodiesNumber):
		for keyPoints in range(25):
			print(keyPoints, end=' ')
			for coordinates in range(2):
				print(coordinatesKeyPointsPolygon[bodies][keyPoints][coordinates], end=' ')
			print()
		print()

	# Go through bodies
	for bodies in range(bodiesNumber):
		nonZeroValues = 0

		# Find the non zero values
		for keyPoints in range(25):
			if coordinatesKeyPointsPolygon[bodies][keyPoints].any() != 0:
				nonZeroValues += 1

		# Define the polygon to crop the image
		polygon = [[[0 for cartesianCoordinates in range(2)] for keyPoints in range(nonZeroValues)] for
		           polygonArray in range(1)]

		keyPointsIndex = 0
		insertion = False
		# Fill the polygon with all points of the all bodies
		for keyPoints in range(25):
			for coordinates in range(2):
				if coordinatesKeyPointsPolygon[bodies][keyPoints].any() != 0:
					polygon[0][keyPointsIndex][coordinates] = int(
						coordinatesKeyPointsPolygon[bodies][keyPoints][coordinates])
					insertion = True
			if insertion:
				keyPointsIndex += 1
				insertion = False
			if keyPointsIndex == nonZeroValues:
				break

		# The polygon only exist if has more than 2 points. But i will limit it to 10 points
		if keyPointsIndex > 10:
			# First find the minX minY maxX and maxY of the polygon
			minX = imageToProcess.shape[1]
			maxX = -1
			minY = imageToProcess.shape[0]
			maxY = -1
			for point in polygon[0]:
				x = point[0]
				y = point[1]

				if x < minX:
					minX = x
				if x > maxX:
					maxX = x
				if y < minY:
					minY = y
				if y > maxY:
					maxY = y

			# Go over the points in the image if they are out side of the enclosing rectangle put zero
			# if not check if they are inside the polygon or not
			croppedImage = np.zeros_like(imageToProcess)
			for y in range(0, imageToProcess.shape[0]):
				for x in range(0, imageToProcess.shape[1]):

					if x < minX or x > maxX or y < minY or y > maxY:
						continue

					if cv2.pointPolygonTest(np.asarray(polygon), (x, y), False) >= 0:
						croppedImage[y, x, 0] = imageToProcess[y, x, 0]
						croppedImage[y, x, 1] = imageToProcess[y, x, 1]
						croppedImage[y, x, 2] = imageToProcess[y, x, 2]

			# Now we can crop again just the enveloping rectangle
			finalImage = croppedImage[minY:maxY, minX:maxX]

			# Display image
			nameFile = "resources/finalImage" + str(bodies) + ".png"
			print(nameFile)
			cv2.imwrite(nameFile, finalImage)
			cv2.imshow(nameFile, finalImage)

	# Display Image
	# print("Body key points: \n" + str(datum.poseKeypoints))
	cv2.imshow("OpenPose 1.6.0 - Tutorial Python API", datum.cvOutputData)
	cv2.waitKey(0)

	# Open rote file
	pointsFile = open("resources/bodies.txt", "w")
	# Writing on rote file
	pointsFile.write(str(datum.poseKeypoints))
	# Closing rote file
	pointsFile.close()

except Exception as e:
	print(e)
	sys.exit(-1)
