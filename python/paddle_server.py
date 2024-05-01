import os
import uuid
import numpy as np
from PIL import Image
from flask import Flask, request
from werkzeug.utils import secure_filename
import json
import paddle.fluid as fluid
import librosa
from pydub import AudioSegment
import warnings
warnings.filterwarnings("ignore")

# 新加的
import torch
from torchvision import transforms
from torchvision.models import resnet18

app = Flask(__name__)
# 根路径，返回一个字符串
@app.route('/', methods=['POST'])
def hello_world():
    data = request.get_data()
    json_data = json.loads(data.decode("utf-8"))
    temp = json_data.get("price")
    print("接收到的数据："+temp)
    temp = int(temp)+5
    print(temp)
    return str(temp)

@app.route('/index', methods=['GET'])
def hello():
    print("hello world!")
    return "hello world!"

@app.route('/upload', methods=['POST'])
def upload_file():
    f = request.files['audio']
    # 设置保存路径
    save_father_path = 'save_audios'
    audio_path = os.path.join(save_father_path, str(uuid.uuid1()) + secure_filename(f.filename).split('.')[-1])
    if not os.path.exists(save_father_path):
        os.makedirs(save_father_path)
    f.save(audio_path)
    return 'success, save path: ' + audio_path

# 创建执行器
place = fluid.CPUPlace()
exe = fluid.Executor(place)
exe.run(fluid.default_startup_program())
# 保存预测模型路径
save_path = 'hand.inference.model'
# 从模型中获取预测程序、输入数据名称列表、分类器
[infer_program, feeded_var_names, target_var] = fluid.io.load_inference_model(dirname=save_path, executor=exe)
# 类别
LABELS = ['awake', 'diaper', 'hug', 'hungry', 'sleepy', 'uncomfortable']
N_CLASS = len(LABELS)

# 定义转化格式函数
def trans_mp3_to_wav(filepath):
    # print(filepath)
    song = AudioSegment.from_mp3(filepath)
    # filepath = filepath.split('.')[-2]
    # filepath = filepath.split('\\')[-1]
    filename = "./transformed_audios/"+str(uuid.uuid1())+".wav"
    file = song.export(filename, format="wav")
    return file,filename


def extract_logmel(y, sr):  # , size=3
    """
    extract log mel spectrogram feature
    :param y: the input signal (audio time series) 音频时间序列
    :param sr: sample rate of 'y'
    :param size: the length (seconds) of random crop from original audio, default as 3 seconds
    :return: log-mel spectrogram feature
    """
    # # 归一化
    # y = y.astype(np.float32)
    # normalization_factor = 1 / np.max(np.abs(y))
    # y = y * normalization_factor

    # random crop
    # if len(y) <= size * sr:
    #     new_y = np.zeros((size * sr+1, ))
    #     new_y[:len(y)] = y
    #     y = new_y

    # start = np.random.randint(0, len(y) - size * sr)
    # y = y[start: start + size * sr]

    # Log-Mel Spectrogram特征是目前在语音识别和环境声音识别中很常用的一个特征，由于CNN在处理图像上展现了强大的能力，
    # 使得音频信号的频谱图特征的使用愈加广泛，甚至比MFCC使用的更多。在librosa中，Log-Mel Spectrogram特征的提取只需几行代码：
    # extract log mel spectrogram #####
    melspectrogram = librosa.feature.melspectrogram(
        y=y, sr=sr, n_fft=1024, hop_length=512, n_mels=128)
    logmelspec = librosa.power_to_db(melspectrogram)

    # 归一化
    logmelspec = logmelspec.astype(np.float32)
    normalization_factor = 1 / np.max(np.abs(logmelspec))
    logmelspec = logmelspec * normalization_factor
    return logmelspec


def get_wave_norm(file):
    data, framerate = librosa.load(file, sr=None)  # 以22050hz采样
    return data, framerate  # data为numpy.ndarray类型, framerate为采样率22050

# def extract_mfcc(file):
#     mfcc = np.zeros((40, 400))
#     mfcc_feat = wav2mfcc(file)[:, :400]
#     mfcc[:, :mfcc_feat.shape[1]] = mfcc_feat
#     return mfcc

# def wav2mfcc(file):
#     y, sr = librosa.load(file, mono=True, sr=None)
#     # 提取mfcc特征
#     mfcc = librosa.feature.mfcc(y, sr=8000, n_mfcc=40)
#     # 归一化
#     mfcc = mfcc.astype(np.float32)
#     normalization_factor = 1 / np.max(np.abs(mfcc))
#     mfcc = mfcc * normalization_factor
#     return mfcc


# 预处理音频
def load_audio(file):
    file,filename = trans_mp3_to_wav(file)
    # test_mfcc = extract_mfcc(file)
    # test_mfcc = np.expand_dims(test_mfcc.T, axis=0)  # 对每个数据先转置 x.shape = (1, 400, 40)
    # test_mfcc = test_mfcc.transpose().reshape(1, 1, 400, 40).astype('float32')
    raw, sr = get_wave_norm(file)  # raw.shape = (399857,) sr为采样率
    feature = np.zeros((128, 728))
    feature_feat = extract_logmel(y=raw, sr=sr)[:, :728]
    feature[:, :feature_feat.shape[1]] = feature_feat
    feature = np.expand_dims(feature.T, axis=0)  # 对每个数据先转置 x.shape = (1, 400, 40)
    feature = feature.transpose().reshape(1, 1, 728, 128).astype('float32')
    return feature,filename

@app.route('/infer', methods=['POST'])
def infer():
    # with open("save_audios/"+str(uuid.uuid1())+".mp3", 'wb+') as f:
    #     for chunk in request.files.get('audio').chunks():
    #         f.write(chunk)
    f = request.files['file']
    # 保存音频
    # save_father_path = 'save_audios'
    # audio_path = os.path.join(save_father_path, str(uuid.uuid1()) + '.' + secure_filename(f.filename).split('.')[-1])
    # if not os.path.exists(save_father_path):
    #     os.makedirs(save_father_path)
    # if secure_filename(f.filename).split('.')[-1] == 'wav':
    #     audio_path = os.path.join("transformed_audios", str(uuid.uuid1()) + '.' + secure_filename(f.filename).split('.')[-1])
    #     f.save(os.path.join("transformed_audios", str(uuid.uuid1()) + '.' + secure_filename(f.filename).split('.')[-1]))
    # else:
    #     f.save(audio_path)
    # print(audio_path)
    # 开始预测音频
    audio,filename = load_audio(f)
    results = exe.run(program=infer_program,
                     feed={feeded_var_names[0]: audio},
                     fetch_list=target_var)
    # print(results.shape)
    # print(np.argmax(results[0][0]))
    results = np.argmax(results[0][0])
    results = results.item()
    results = LABELS[results]
    # print(results)
    os.remove(filename)
    return results


# 检测准备
transform = transforms.Compose([
        transforms.Resize((224, 224)),
        transforms.ToTensor(),
        # transforms.Normalize((0.485, 0.456, 0.406), (0.229, 0.224, 0.225)),
    ])
model = resnet18(pretrained=False)
model.fc = torch.nn.Linear(512, 2, bias=True)
model.load_state_dict(torch.load("./asdDetect_models/model.pth", map_location=torch.device('cpu')))  # !!!此处需要修改
model.eval()  # 开启验证模式，关闭dropout层

@app.route('/detect', methods=['POST'])
def detect():
    f = request.files['file']
    # f = request.data
    # f = f['img']
    # tu = base64.b64decode(f)  # 字节流
    item = Image.open(f)
    # 图片进行归一化处理时一定要先读取图片
    item = transform(item)
    item = item.unsqueeze(0)
    # 模型预测
    y_pred = model(item)
    _, predicted = torch.max(y_pred, dim=1)
    if predicted[0].item() == 1:
        return "yes"
    else:
        return "no"


@app.route('/save', methods=['POST'])
def save():
    f = request.files['file']
    data = request.form['category']
    # print(data)
    # 保存音频
    save_father_path = 'save_audios'
    save_father_path = os.path.join(save_father_path,data)
    audio_path = os.path.join(save_father_path, str(uuid.uuid1()) + '.' + 'wav')
    f.save(audio_path)
    print(audio_path)
    return 'ok'

if __name__ == '__main__':
    # 启动服务，并指定端口号
    app.run(host='192.168.1.102',port=8080,debug=True)