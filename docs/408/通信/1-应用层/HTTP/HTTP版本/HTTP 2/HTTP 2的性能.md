# HTTP 2的性能

头部压缩

​	HPACK

二进制格式

​	帧

并发传输 

​	多个 Stream 复用在一条 TCP 连接，帧头中的「标志位」可以设置Stream优先级

服务器主动推送资源

​	减少了客户端请求的次数。

# HTTP 2的性能缺陷

**TCP的队头阻塞**

​	packet1、2接收，应用读取；packet 3 网络阻塞了，packet 456 收到只能放在缓冲区里，应用读取不了。

​	要么等到 packet 3 后，应用才可以读。

​	要么 packet 3丢包，TCP重传 packet 3 4 5 6，浪费资源。