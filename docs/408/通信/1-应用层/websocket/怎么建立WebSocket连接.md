# 怎么建立WebSocket连接

在 **TCP 三次握手**建立连接之后，

**统一使用 HTTP 协议**，请求头和响应头设置升级协议，

```
Connection: Upgrade
Upgrade: WebSocket
Sec-WebSocket-Key: T2a6wZlAwhgQNqruZ2YUyg==\r\n
```

```
HTTP/1.1 101 Switching Protocols\r\n
Sec-WebSocket-Accept: iBJKv/ALIW2DobfoA4dmr3JHBCY=\r\n
Upgrade: WebSocket\r\n
Connection: Upgrade\r\n
```

两次 HTTP 握手，WebSocket就建立完成了，后续双方就可以使用 webscoket 的数据格式进行通信了。

![image-20240616205929876](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images202406162059926.png)