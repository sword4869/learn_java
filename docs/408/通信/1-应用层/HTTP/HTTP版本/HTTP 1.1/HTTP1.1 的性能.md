#  HTTP/1.1 的性能优化

长连接

管道网络传输（默认不开启，大家基本没有使用）

# HTTP 1.1的性能瓶颈

Header 不压缩	→   HTTP 2的二进制格式

Header 不重用	→  HTTP 2的HPACK

请求-应答模型，请求的队头阻塞	→ HTTP 2的并发传输

请求只能从客户端开始，服务器只能被动响应	→ HTTP 2的服务器主动推送资源

