# HTTP 3的性能优化

基于 UDP 的 **QUIC 协议**

​	无TCP队头阻塞

​		QUIC 连接上的多个 Stream 独立，当某个流发生丢包时，只会阻塞这个流，其他流不会受到影响

​	更快的连接建立

​		QUIC 内部包含了 TLS 1.3，TCP握手（慢启动）和TLS握手合并为QUIC三次握手 （1-RTT）

​		当会话恢复时，QUIC握手信息与数据包一起发送（0-RTT）

​	连接迁移

​		网络变化（流量到wifi的ip变化）时，QUIC 使用**连接 ID**绑定连接，实现无卡顿。

头部压缩算法，HPACK→QPACK

​	静态表扩大，静态表扩大，61→91项。

​	动态表，解决第一次请求丢包，对方就无法更新动态表的问题。使用QUIC的单向流，来同步双方的动态表。



