### HTTP 缓存有哪些实现方式

1、强制缓存

​	资源响应头 Cache-Control 或 Expires

​	由**浏览器**判断缓存是否过期

​		未则就使用缓存，不请求；

​		否则请求更新。

2、协商缓存

​	配合强制缓存，缓存过期时的请求处理。

​		最后修改时间：响应头 Etag，请求头 If-None-Match

​		唯一标识响应资源：响应头 Last-Modified，请求头 If-Modified-Since

​	服务器判断未过期，则304；过期则更新。

![img](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406131818240.png)