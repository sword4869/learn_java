# 如何避免 SYN 攻击？

在 TCP 三次握手的时候，Linux 内核会维护两个队列，分别是：

- 半连接队列，也称 SYN 队列；
- 全连接队列，也称 accept 队列；



伪造不同 IP 地址的 `SYN` 报文，**占满服务端的半连接队列**，**后续再在收到 SYN 报文就会丢弃**，使得服务端不能为正常用户服务

![正常流程](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images202406162302225.png)

避免 SYN 攻击方式，可以有以下四种方法：

- 调大 netdev_max_backlog 网卡接收数据包的队列
- 增大 TCP 半连接队列；
- 开启 tcp_syncookies；
- 减少 SYN+ACK 重传次数