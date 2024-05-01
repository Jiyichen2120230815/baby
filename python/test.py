import json
import traceback
import urllib
import urllib.request
import os
import poster3
import http.cookiejar
import requests
import base64

with open("asd.jpg",'rb') as fp:
    tu = base64.b64encode(fp.read())
    print(tu)

data = {'img':tu}

# files = {"file":open("now.mp3", "rb")}
# files = {"file":open("non_asd.jpg", "rb")}
url = "http://192.168.1.102:8080/detect"
try:
    response = requests.post(url,data=data)  # files = files
    # request = urllib.request.Request(url, data, header)
    # response = urllib.request.urlopen(request)
    response_str = response.text  # 状态值 200，表明该请求被成功地完成，所请求的资源发送到客户端。
    print(response_str)
    # response_str = str(response_str, encoding="utf-8")
    # response_str = eval(response_str)
    # print(response_str)
    # response.close()
    # print(response_str['result']['y'])
    # print(type(response_str))
    response.close()
except urllib.request.HTTPError as e:
    print("The server couldn't fulfill the request")
    print(e.code)
    print(e.read())
except urllib.request.URLError as e:
    print("Failed to reach the server")
    print(e.reason)
except:
    traceback.print_exc()