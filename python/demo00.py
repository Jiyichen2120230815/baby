# import os
# import librosa
# from pydub import AudioSegment
#
# path = os.path.join("./small Api","save_audios")
# path = os.path.join(path,"now.mp3")
# path = path.split('.')[-2]
# print(path.split('\\')[-1])
# song = AudioSegment.from_mp3(path)
# song.export("./transformed_audios/"+path.split('.')[-2]+".wav", format="wav")
# path = "./transformed_audios/"+path.split('.')[-2]+".wav"
# print(path)
# #
# # # path = os.path.join("./small Api","transformed_audios")
# # # path = os.path.join(path,"awake_1.wav")
# # y,sr = librosa.load(path, sr=None)
# # print(y)

# f = open("now.mp3", "rb")
# sound_wav_rb = f.read()
# f.close()
# print(sound_wav_rb)

import requests
import json
leibie = {'category': 'uncomfortable'}
files = {'file': open("now.mp3", 'rb')}
# data = json.dumps(leibie).encode(encoding='UTF8')
# result = requests.post("http://10.5.85.175:8080/infer", files=files)
status = requests.post("http://10.5.85.175:8080/save", data=leibie, files=files)
print(status.text)
# print(result.text)
