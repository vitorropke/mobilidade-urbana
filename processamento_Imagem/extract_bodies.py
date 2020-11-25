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
			import pyopenpose as op
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
	parser.add_argument("--image_path", default="../../Programas/openpose/examples/media/COCO_val2014_000000000192.jpg",
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
	# Declare a 4 dimension polygon
	polygon = [[[[0 for cartesianCoordinates in range(2)] for keyPoints in range(25)] for polygonArray in range(1)] for
	           bodies in range(coordinatesKeyPoints.shape[0])]

	# Fill the polygon with all points of the all bodies
	for bodies in range(coordinatesKeyPoints.shape[0]):
		for keyPoints in range(25):
			for coordinates in range(2):
				polygon[bodies][0][keyPoints][coordinates] = int(coordinatesKeyPoints[bodies][keyPoints][coordinates])

	for bodies in range(coordinatesKeyPoints.shape[0]):
		for keyPoints in range(25):
			for coordinates in range(2):
				print(polygon[bodies][0][keyPoints][coordinates], end=' ')
			print('\n')
		print('\n')

	# Go through bodies
	for bodies in range(coordinatesKeyPoints.shape[0]):
		# Swap the points according to pose key points
		polygon[bodies][0][0] = polygon[bodies][0][17]
		polygon[bodies][0][1] = polygon[bodies][0][17]

		# 5 -> 9
		temp = polygon[bodies][0][5]
		polygon[bodies][0][5] = polygon[bodies][0][9]
		polygon[bodies][0][9] = temp

		# 6 -> 10
		temp = polygon[bodies][0][6]
		polygon[bodies][0][6] = polygon[bodies][0][10]
		polygon[bodies][0][10] = temp

		# 7 -> 11
		temp = polygon[bodies][0][7]
		polygon[bodies][0][7] = polygon[bodies][0][11]
		polygon[bodies][0][11] = temp

		# 8 -> 23
		temp = polygon[bodies][0][8]
		polygon[bodies][0][8] = polygon[bodies][0][23]
		polygon[bodies][0][23] = temp

		# 5 -> 9 -> 22. In other words (or numbers), 5 -> 22
		temp = polygon[bodies][0][9]
		polygon[bodies][0][9] = polygon[bodies][0][22]
		polygon[bodies][0][22] = temp

		# 6 -> 10 -> 24. In other words (or numbers), 6 -> 24
		temp = polygon[bodies][0][10]
		polygon[bodies][0][10] = polygon[bodies][0][24]
		polygon[bodies][0][24] = temp

		# 7 -> 11 -> 21. In other words (or numbers), 7 -> 21
		temp = polygon[bodies][0][11]
		polygon[bodies][0][11] = polygon[bodies][0][21]
		polygon[bodies][0][21] = temp

		# 12 -> 19
		temp = polygon[bodies][0][12]
		polygon[bodies][0][12] = polygon[bodies][0][19]
		polygon[bodies][0][19] = temp

		# 13 -> 20
		temp = polygon[bodies][0][13]
		polygon[bodies][0][13] = polygon[bodies][0][20]
		polygon[bodies][0][20] = temp

		# 15 -> 20
		temp = polygon[bodies][0][15]
		polygon[bodies][0][15] = polygon[bodies][0][20]  # 20 was 13
		polygon[bodies][0][20] = temp

		# 16 -> 19
		temp = polygon[bodies][0][16]
		polygon[bodies][0][16] = polygon[bodies][0][19]  # 19 was 12
		polygon[bodies][0][19] = temp

		# 17 -> 21
		temp = polygon[bodies][0][17]
		polygon[bodies][0][17] = polygon[bodies][0][21]  # 21 was 7
		polygon[bodies][0][21] = temp

		# 18 -> 24
		temp = polygon[bodies][0][18]
		polygon[bodies][0][18] = polygon[bodies][0][24]  # 24 was 6
		polygon[bodies][0][24] = temp

		# 16 -> 19 -> 22. In other words (or numbers), 16 -> 22
		temp = polygon[bodies][0][19]
		polygon[bodies][0][19] = polygon[bodies][0][22]  # 22 was 5
		polygon[bodies][0][22] = temp

		# 15 -> 20 -> 24. In other words (or numbers), 15 -> 24
		temp = polygon[bodies][0][20]
		polygon[bodies][0][20] = polygon[bodies][0][24]  # 24 was 18
		polygon[bodies][0][24] = temp

		# 17 -> 21 -> 22
		temp = polygon[bodies][0][21]
		polygon[bodies][0][21] = polygon[bodies][0][22]  # 22 was 16
		polygon[bodies][0][22] = temp

		# 17 -> 21 -> 22 -> 24
		temp = polygon[bodies][0][22]
		polygon[bodies][0][22] = polygon[bodies][0][24]  # 24 was 15
		polygon[bodies][0][24] = temp

		# 17 -> 21 -> 22
		temp = polygon[bodies][0][23]
		polygon[bodies][0][23] = polygon[bodies][0][24]  # 24 was 17

		# Increase a little the polygon
		for point in range(9):
			for coordinate in range(2):
				polygon[bodies][0][point][coordinate] -= 10

		# Resize the foot
		polygon[bodies][0][13][1] -= 10
		polygon[bodies][0][14][1] -= 10

		# Increase the polygon
		for point in range(10, 20):
			for coordinate in range(2):
				polygon[bodies][0][point][coordinate] += 10

		# Resize the head
		polygon[bodies][0][20][1] -= 10
		polygon[bodies][0][21][1] -= 20
		polygon[bodies][0][22][1] -= 20
		polygon[bodies][0][23][1] += 10

		# First find the minX minY maxX and maxY of the polygon
		minX = imageToProcess.shape[1]
		maxX = -1
		minY = imageToProcess.shape[0]
		maxY = -1
		for point in polygon[bodies][0]:
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
		cropedImage = np.zeros_like(imageToProcess)
		for y in range(0, imageToProcess.shape[0]):
			for x in range(0, imageToProcess.shape[1]):

				if x < minX or x > maxX or y < minY or y > maxY:
					continue

				if cv2.pointPolygonTest(np.asarray(polygon[bodies]), (x, y), False) >= 0:
					cropedImage[y, x, 0] = imageToProcess[y, x, 0]
					cropedImage[y, x, 1] = imageToProcess[y, x, 1]
					cropedImage[y, x, 2] = imageToProcess[y, x, 2]

		# Now we can crop again just the enveloping rectangle
		finalImage = cropedImage[minY:maxY, minX:maxX]

		# Display image
		nameFile = "finalImage" + str(bodies) + ".png"
		cv2.imwrite(nameFile, finalImage)
		cv2.imshow(nameFile, finalImage)

	# Display Image
	# print("Body key points: \n" + str(datum.poseKeypoints))
	cv2.imshow("OpenPose 1.6.0 - Tutorial Python API", datum.cvOutputData)
	cv2.waitKey(0)

	# Open rote file
	arquivoPontos = open("corpos.txt", "w")
	# Writing on rote file
	arquivoPontos.write(str(datum.poseKeypoints))
	# Closing rote file
	arquivoPontos.close()

except Exception as e:
	print(e)
	sys.exit(-1)
