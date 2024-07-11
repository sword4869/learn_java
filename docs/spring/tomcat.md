![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112201170.png)

spring服务部署到tomcat中。

每个用户其实对应都是去找tomcat线程池中的一个线程来完成工作的：

1. 当用户向tomcat注册的端口发起连接时，tomcat的监听线程来创建socket连接，用户和tomcat各一个socket。
2. 当tomcat端的socket接受到数据后，监听线程会从tomcat的线程池中取出一个线程执行用户请求，转发到工程中的controller，service，dao中，并且访问对应的DB
3. 执行完请求后，再统一返回，再找到tomcat端的socket，再将数据写回到用户端的socket，
