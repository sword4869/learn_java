请求：

​	*Host* 服务器的域名

​	*Accept* 客户端可接受的数据格式

​	*Accept-Encoding*  客户端可接受的压缩格式

响应

​	*Content-Length*  本次回应的数据长度，后面的字节就属于下一个回应了。

​	*Content-Type* 数据格式

​	*Content-Encoding* 压缩格式



都有

​	*Connection: Keep-Alive*  使用 TCP 长连接

