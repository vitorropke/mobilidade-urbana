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

	saveLocation = "/mnt/993a36c3-7196-41ee-9cf8-2ee60c841bc1/resources/frames1"

	# Take the video
	videoCapture = cv2.VideoCapture('MHDX_ch3_main_20200220100000_20200220103000.avi')
	success, image = videoCapture.read()
	count = 0
	framesLimit = 1800
	"""
	# Save the frames on separate images
	while success and count < framesLimit:
		videoCapture.set(cv2.CAP_PROP_POS_MSEC, (count * 1000))  # Take 1 frame per second
		success, image = videoCapture.read()
		cv2.imwrite(saveLocation + "/frame%d.jpg" % count, image)  # save
		# frame as JPEG file
		print('Read a new frame: ', success)
		count += 1
	"""
	# Take all the bodies in all frames
	for frame in range(framesLimit):
		actualFrame = saveLocation + "/frame" + str(frame) + ".jpg"

		# Flags
		parser = argparse.ArgumentParser()
		parser.add_argument("--image_path", default=actualFrame,
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

		# Check if there are bodies on image
		if len(coordinatesKeyPoints.shape) == 0:
			print("Null")
		else:
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

				# Resize the parts of the body

				# Resize the head
				if coordinatesKeyPointsPolygon[bodies][20][1] - 20 > 0:  # Point 20 Coordinate Y
					coordinatesKeyPointsPolygon[bodies][20][1] -= 20
				else:
					coordinatesKeyPointsPolygon[bodies][20][1] = 0

				if coordinatesKeyPointsPolygon[bodies][21][1] - 30 > 0:  # Point 21 Coordinate Y
					coordinatesKeyPointsPolygon[bodies][21][1] -= 30
				else:
					coordinatesKeyPointsPolygon[bodies][21][1] = 0

				if coordinatesKeyPointsPolygon[bodies][22][1] - 30 > 0:  # Point 22 Coordinate Y
					coordinatesKeyPointsPolygon[bodies][22][1] -= 30
				else:
					coordinatesKeyPointsPolygon[bodies][22][1] = 0

				if coordinatesKeyPointsPolygon[bodies][23][1] - 20 > 0:  # Point 23 Coordinate Y
					coordinatesKeyPointsPolygon[bodies][23][1] -= 20
				else:
					coordinatesKeyPointsPolygon[bodies][23][1] = 0

				# Verify if the body is on front or is on back
				backWards = False
				foundOrientation = False
				for leftPoints in range(10):  # Search for a non zero value on the left of the body
					if coordinatesKeyPointsPolygon[bodies][leftPoints].any() != 0:
						for rightPoints in range(11, 21):  # Search for a non zero value on the right of the body
							if coordinatesKeyPointsPolygon[bodies][rightPoints].any() != 0:
								# If a right point is higher than a left point in X, it means that the body is on front
								if coordinatesKeyPointsPolygon[bodies][rightPoints][0] > \
										coordinatesKeyPointsPolygon[bodies][leftPoints][0]:
									foundOrientation = True
								# If a right point is lower than a left point in X, it means that the body is on back
								else:
									foundOrientation = True
									backWards = True

							if foundOrientation:  # Quit the inner loop if already found the orientation
								break

					if foundOrientation:  # Quit the outer loop if already found the orientation
						break

				# Make the resize of the middle
				if backWards:  # If the body is backwards
					# Left arm
					for leftArm in range(2, 5):  # Points from 2 to 4
						if coordinatesKeyPointsPolygon[bodies][leftArm].any() != 0:
							if coordinatesKeyPointsPolygon[bodies][leftArm][0] + 5 < imageToProcess.shape[1]:
								coordinatesKeyPointsPolygon[bodies][leftArm][0] += 5  # Coordinate X
							else:
								coordinatesKeyPointsPolygon[bodies][leftArm][0] = imageToProcess.shape[1] - 1  # Coordinate X

							if coordinatesKeyPointsPolygon[bodies][leftArm][1] - 5 > 0:
								coordinatesKeyPointsPolygon[bodies][leftArm][1] -= 5  # Coordinate Y
							else:
								coordinatesKeyPointsPolygon[bodies][leftArm][1] = 0  # Coordinate Y

					# Right arm
					for rightArm in range(17, 20):  # Points from 17 to 19
						if coordinatesKeyPointsPolygon[bodies][rightArm][0] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][rightArm][0] -= 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][rightArm][0] = 0  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][rightArm][1] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][rightArm][1] -= 5  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][rightArm][1] = 0  # Coordinate Y

					# Point 5
					if coordinatesKeyPointsPolygon[bodies][5].any() != 0:
						if coordinatesKeyPointsPolygon[bodies][5][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][5][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][5][0] = imageToProcess.shape[1] - 1  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][5][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][5][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][5][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					# Point 16
					if coordinatesKeyPointsPolygon[bodies][16].any() != 0:
						if coordinatesKeyPointsPolygon[bodies][16][0] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][16][0] -= 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][16][0] = 0  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][16][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][16][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][16][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					# Point 6
					if coordinatesKeyPointsPolygon[bodies][6].any() != 0:
						if coordinatesKeyPointsPolygon[bodies][6][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][6][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][6][0] = imageToProcess.shape[1] - 1  # Coordinate X

					# Point 15
					if coordinatesKeyPointsPolygon[bodies][15][0] - 5 > 0:
						coordinatesKeyPointsPolygon[bodies][15][0] -= 5  # Coordinate X
					else:
						coordinatesKeyPointsPolygon[bodies][15][0] = 0  # Coordinate X

					# Resize the foot
					if coordinatesKeyPointsPolygon[bodies][7].any() != 0:  # Point 7
						if coordinatesKeyPointsPolygon[bodies][7][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][7][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][7][0] = imageToProcess.shape[1] - 1  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][7][1] - 10 > 0:
							coordinatesKeyPointsPolygon[bodies][7][1] -= 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][7][1] = 0  # Coordinate Y

					if coordinatesKeyPointsPolygon[bodies][8].any() != 0:  # Point 8
						if coordinatesKeyPointsPolygon[bodies][8][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][8][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][8][0] = imageToProcess.shape[1] - 1  # Coordinate X

					if coordinatesKeyPointsPolygon[bodies][9].any() != 0:  # Point 9
						if coordinatesKeyPointsPolygon[bodies][9][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][9][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][9][0] = imageToProcess.shape[1] - 1  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][9][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][9][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][9][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					if coordinatesKeyPointsPolygon[bodies][10].any() != 0:  # Point 10
						if coordinatesKeyPointsPolygon[bodies][10][0] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][10][0] -= 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][10][0] = 0  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][10][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][10][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][10][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					if coordinatesKeyPointsPolygon[bodies][11].any() != 0:  # Point 11
						if coordinatesKeyPointsPolygon[bodies][11][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][11][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][11][0] = imageToProcess.shape[1] - 1  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][11][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][11][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][11][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					if coordinatesKeyPointsPolygon[bodies][12].any() != 0:  # Point 12
						if coordinatesKeyPointsPolygon[bodies][12][0] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][12][0] -= 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][12][0] = 0  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][12][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][12][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][12][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					# Point 13
					if coordinatesKeyPointsPolygon[bodies][13][0] - 5 > 0:
						coordinatesKeyPointsPolygon[bodies][13][0] -= 5  # Coordinate X
					else:
						coordinatesKeyPointsPolygon[bodies][13][0] = 0  # Coordinate X

					# Point 14
					if coordinatesKeyPointsPolygon[bodies][14][0] - 5 > 0:
						coordinatesKeyPointsPolygon[bodies][14][0] -= 5  # Coordinate X
					else:
						coordinatesKeyPointsPolygon[bodies][14][0] = 0  # Coordinate X

					if coordinatesKeyPointsPolygon[bodies][14][1] - 10 > 0:
						coordinatesKeyPointsPolygon[bodies][14][1] -= 10  # Coordinate Y
					else:
						coordinatesKeyPointsPolygon[bodies][14][1] = 0  # Coordinate Y

				else:  # If the body is on front
					# Left arm
					for leftArm in range(2, 5):  # Points from 2 to 4
						if coordinatesKeyPointsPolygon[bodies][leftArm][0] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][leftArm][0] -= 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][leftArm][0] = 0  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][leftArm][1] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][leftArm][1] -= 5  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][leftArm][1] = 0  # Coordinate Y

					# Right arm
					for rightArm in range(17, 20):  # Points from 17 to 19
						if coordinatesKeyPointsPolygon[bodies][rightArm].any() != 0:
							if coordinatesKeyPointsPolygon[bodies][rightArm][0] + 5 < imageToProcess.shape[1]:
								coordinatesKeyPointsPolygon[bodies][rightArm][0] += 5  # Coordinate X
							else:
								coordinatesKeyPointsPolygon[bodies][rightArm][0] = imageToProcess.shape[1] - 1  # Coordinate X

							if coordinatesKeyPointsPolygon[bodies][rightArm][1] - 5 > 0:
								coordinatesKeyPointsPolygon[bodies][rightArm][1] -= 5  # Coordinate Y
							else:
								coordinatesKeyPointsPolygon[bodies][rightArm][1] = 0  # Coordinate Y

					# Point 5
					if coordinatesKeyPointsPolygon[bodies][5].any() != 0:
						if coordinatesKeyPointsPolygon[bodies][5][0] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][5][0] -= 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][5][0] = 0  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][5][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][5][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][5][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					# Point 16
					if coordinatesKeyPointsPolygon[bodies][16].any() != 0:
						if coordinatesKeyPointsPolygon[bodies][16][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][16][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][16][0] = imageToProcess.shape[1] - 1  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][16][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][16][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][16][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					# Point 6
					if coordinatesKeyPointsPolygon[bodies][6][0] - 5 > 0:
						coordinatesKeyPointsPolygon[bodies][6][0] -= 5  # Coordinate X
					else:
						coordinatesKeyPointsPolygon[bodies][6][0] = 0  # Coordinate X

					# Point 15
					if coordinatesKeyPointsPolygon[bodies][15].any() != 0:
						if coordinatesKeyPointsPolygon[bodies][15][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][15][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][15][0] = imageToProcess.shape[1] - 1  # Coordinate X

					# Resize the foot
					# Point 7
					if coordinatesKeyPointsPolygon[bodies][7][0] - 5 > 0:
						coordinatesKeyPointsPolygon[bodies][7][0] -= 5  # Coordinate X
					else:
						coordinatesKeyPointsPolygon[bodies][7][0] = 0  # Coordinate X

					if coordinatesKeyPointsPolygon[bodies][7][1] - 10 > 0:
						coordinatesKeyPointsPolygon[bodies][7][1] -= 10  # Coordinate Y
					else:
						coordinatesKeyPointsPolygon[bodies][7][1] = 0  # Coordinate Y

					# Point 8
					if coordinatesKeyPointsPolygon[bodies][8][0] - 5 > 0:
						coordinatesKeyPointsPolygon[bodies][8][0] -= 5  # Coordinate X
					else:
						coordinatesKeyPointsPolygon[bodies][8][0] = 0  # Coordinate X

					if coordinatesKeyPointsPolygon[bodies][9].any() != 0:  # Point 9
						if coordinatesKeyPointsPolygon[bodies][9][0] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][9][0] -= 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][9][0] = 0  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][9][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][9][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][9][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					if coordinatesKeyPointsPolygon[bodies][10].any() != 0:  # Point 10
						if coordinatesKeyPointsPolygon[bodies][10][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][10][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][10][0] = imageToProcess.shape[1] - 1  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][10][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][10][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][10][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					if coordinatesKeyPointsPolygon[bodies][11].any() != 0:  # Point 11
						if coordinatesKeyPointsPolygon[bodies][11][0] - 5 > 0:
							coordinatesKeyPointsPolygon[bodies][11][0] -= 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][11][0] = 0  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][11][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][11][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][11][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					if coordinatesKeyPointsPolygon[bodies][12].any() != 0:  # Point 12
						if coordinatesKeyPointsPolygon[bodies][12][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][12][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][12][0] = imageToProcess.shape[1] - 1  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][12][1] + 10 < imageToProcess.shape[0]:
							coordinatesKeyPointsPolygon[bodies][12][1] += 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][12][1] = imageToProcess.shape[0] - 1  # Coordinate Y

					if coordinatesKeyPointsPolygon[bodies][13].any() != 0:  # Point 13
						if coordinatesKeyPointsPolygon[bodies][13][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][13][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][13][0] = imageToProcess.shape[1] - 1  # Coordinate X

					if coordinatesKeyPointsPolygon[bodies][14].any() != 0:  # Point 14
						if coordinatesKeyPointsPolygon[bodies][14][0] + 5 < imageToProcess.shape[1]:
							coordinatesKeyPointsPolygon[bodies][14][0] += 5  # Coordinate X
						else:
							coordinatesKeyPointsPolygon[bodies][14][0] = imageToProcess.shape[1] - 1  # Coordinate X

						if coordinatesKeyPointsPolygon[bodies][14][1] - 10 > 0:
							coordinatesKeyPointsPolygon[bodies][14][1] -= 10  # Coordinate Y
						else:
							coordinatesKeyPointsPolygon[bodies][14][1] = 0  # Coordinate Y

			# Print the points on terminal
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
					nameFile = saveLocation + "/bones/frame" + str(
						frame) + "body" + str(bodies) + ".png"
					print(nameFile)
					cv2.imwrite(nameFile, finalImage)
					# cv2.imshow(nameFile, finalImage)

	# Display Image
	# print("Body key points: \n" + str(datum.poseKeypoints))
	cv2.imshow("OpenPose 1.6.0 - Tutorial Python API", datum.cvOutputData)
	cv2.waitKey(0)

	# Open rote file
	pointsFile = open(saveLocation + "/bodies.txt", "w")
	# Writing on rote file
	pointsFile.write(str(datum.poseKeypoints))
	# Closing rote file
	pointsFile.close()

except Exception as e:
	print(sys.exc_info().tb_lineno)
	print(e)
	sys.exit(-1)
