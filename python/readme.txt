small Api项目文件说明：
1. hand.inference.model：存放识别婴儿哭声的Resnet50模型中的节点的权重等数据
2. asdDetect_models:存放识别婴儿抑郁症的Resnet18模型中的节点的权重等数据
3. 备份：存放的上一个Resnet50模型权重（新的模型准确率为40%，比上一个更高）
4. demo00.py：python使用request中的post请求传递MP3文件，用于测试
5. now.mp3：测试的MP3音频
6. paddle_server.py：接口的运行代码
7. 其余文件不用管
使用说明：
1. 运行paddle_server.py即可，在代码中最后一行需要修改当前的IP地址及端口号