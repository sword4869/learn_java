# UDP头部格式

- 目标和源端口：主要是告诉 UDP 协议应该把报文发给哪个进程。
- 包长度：该字段保存了 UDP 首部的长度跟数据的长度之和。
- 校验和：校验和是为了提供可靠的 UDP 首部和数据而设计，防止收到在网络传输中受损的 UDP 包。

![UDP 头部格式](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images202406162136330.png)