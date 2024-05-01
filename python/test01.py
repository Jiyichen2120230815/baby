from io import BytesIO
import cv2
import numpy as np
import requests
from requests.auth import HTTPBasicAuth
from PIL import Image
import base64

with open("asd.jpg",'rb') as fp:
    tu = base64.b64encode(fp.read())
    print(tu)

tu = base64.b64decode(tu)  # 字节流
print(tu)
print(type(tu))
bytes_stream = BytesIO(tu)
capture_img = Image.open(bytes_stream)
print(capture_img)
capture_img = cv2.cvtColor(np.asarray(capture_img), cv2.COLOR_RGB2BGR)
cv2.imshow("capture_img", capture_img)
cv2.waitKey(0)


# auth = HTTPBasicAuth("admin".encode('utf-8'), "12345")
# response = requests.post("http://192.168.1.102/action/snap?cam=0", auth=auth)
#
# if response.status_code == 200:
#     response_byte = response.content
#     bytes_stream = BytesIO(response_byte)
#     capture_img = Image.open(bytes_stream)
#     capture_img = cv2.cvtColor(np.asarray(capture_img), cv2.COLOR_RGB2BGR)
#     cv2.imshow("capture_img", capture_img)
#     cv2.waitKey(0)