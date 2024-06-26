HTTPS 就是在 HTTP 与 TCP 层之间增加了 **SSL/TLS 安全传输层**

​	TLS握手阶段（TLS握手协议）：通过非对称加密握手来得到对称加密密钥。

​	HTTP通信阶段（TLS记录协议）：使用握手得到的对称加密密钥，加密HTTP数据再传输。



# HTTP 与 HTTPS 有哪些区别？

- HTTP 是信息是明文传输，不安全。HTTPS 就是在 HTTP 与 TCP 层之间增加了 SSL/TLS 安全传输层，使得报文能够加密传输。
- HTTP 连接建立相对简单， TCP 三次握手之后便可进行 HTTP 的报文传输。而 HTTPS 在 TCP 三次握手之后，还需进行 SSL/TLS 的握手过程，才可进入加密报文传输。
- 两者的默认端口不一样，HTTP 默认端口号是 80，HTTPS 默认端口号是 443。
- HTTPS 协议需要向 CA（证书权威机构）申请数字证书，来保证服务器的身份是可信的。

# HTTPS 解决了 HTTP 的哪些问题？

不安全，明文传输。

​	窃听→TLS混合加密，变成非明文		

​		通信建立前，采用 **非对称加密** 的方式交换「会话秘钥」

​		后续通信过程中，使用「会话秘钥」**对称加密** 明文数据。

​	篡改（消息的完整性）→**摘要算法**，校验完整性

​		发送**「报文 + 数字签名」**，报文的哈希值再被私钥加密就是数字签名，收到后公钥解密数字签名得到哈希值，并计算报文的哈希值，比较两个哈希值是否一样。

​	冒充（消息可靠性）→**数字证书**

​		数字证书，保证服务器公钥的身份。



PS：

​	内容 + 哈希值：可以保证内容完整性，但可靠性不行，因为中间人可以整体替换为篡改的内容及其哈希值。

​	不直接发服务器公钥，是害怕被中间人替换掉。