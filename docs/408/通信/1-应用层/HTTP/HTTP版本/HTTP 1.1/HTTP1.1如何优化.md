# HTTP/1.1 如何优化

尽量避免发送 HTTP 请求；

​	缓存

减少请求次数

​	减少重定向请求次数；

​		代理服务器不是直接返回301、302，而是返回更新后的url。	

​	合并请求；

​		多个小图合并大图

​		webpack打包资源

​		图片base64嵌入html

​	延迟发送请求

​		lazy load

减少 HTTP 响应的数据大小

​	无损压缩

​		gzip、br，文本、代码

​	有损压缩

​		图片，webp格式

​		音视频，关键帧 + 增量数据。





## 减少重定向请求次数

![image-20240613204409590](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406132044632.png)