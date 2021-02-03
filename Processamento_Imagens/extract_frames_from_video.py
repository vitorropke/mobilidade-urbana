import cv2

videoCapture = cv2.VideoCapture('../../Documentos/videos_cameras/camera3/wetransfer-33c3c3/ch03_20210122153516.mp4')
# success, image = videoCapture.read()
count = 0

"""""""""
while success and count <= 30:
	cv2.imwrite("resources/frames/frame%d.jpg" % count, image)  # save frame as JPEG file
	success, image = videoCapture.read()
	print('Read a new frame: ', success)
	count += 1
"""

frame_no = 56690
# Choose a specific frame
videoCapture.set(1, frame_no)  # Where frame_no is the frame you want
ret, frame = videoCapture.read()  # Read the frame
cv2.imshow('window_name', frame)  # show frame on window

while True:
	ch = 0xFF & cv2.waitKey(1)  # Wait for a second

	if ch == 27:
		break
