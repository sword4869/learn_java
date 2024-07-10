[2024 windows环境下安装RabbitMQ（亲测超详细）_rabbitmq windows-CSDN博客](https://blog.csdn.net/qq_25861247/article/details/136272612)

## Erlang和RabbitMQ都是最新版

[Erlang Version Requirements | RabbitMQ](https://www.rabbitmq.com/docs/which-erlang)

[Installing on Windows | RabbitMQ](https://www.rabbitmq.com/docs/install-windows#dependencies)

[Installing on Windows | RabbitMQ](https://www.rabbitmq.com/docs/install-windows#downloads)

## Erlang的环境变量

`xxx/bin`

## RabbitMQ

（1）环境变量`xxx/sbin`: bat脚本

​	启动服务、关闭服务。

​	![image-20240709223102803](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407092231866.png)

（2）启动服务，这样才会产生`C:\Windows\System32\config\systemprofile\.erlang.cookie`。

```
$ rabbitmq-plugins enable rabbitmq_management
$ rabbitmqctl status
```

（3）`rabbitmqctl status`报错

​	`C:\Users\xxx\.erlang.cookie`和`C:\Windows\System32\config\systemprofile\.erlang.cookie`不一致，让前者和后者一致即可。

​	然后不用重启服务，直接`rabbitmqctl status`就能显示正确。

## 测试

[http://127.0.0.1:15672](http://127.0.0.1:15672/)

账号: guest

密码: guest
