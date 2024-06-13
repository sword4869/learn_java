TLS 收发数据的基本单位是记录（*record*）。

​	多个记录可以组合成一个 TCP 包发送。



TLS 握手

​	TLS和TLS 1.2：4次 TLS 握手，2个 RTT

​	TLS 1.3：3次 TLS 握手，1个 RTT

![image-20240611164931558](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406111649593.png)

# SSL/TLS 协议基本流程

先TCP握手，再TLS握手，再TCP请求应答。



**客户端和服务端双方都共享了三个随机数，分别是 Client Random、Server Random、pre-master**



根据三个随机数，生成**会话密钥（Master Secret）**，它是对称密钥



![image-20240613210100951](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406132101072.png)

![image-20240613202911819](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406132029914.png)

![image-20240613202941980](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406132029109.png)