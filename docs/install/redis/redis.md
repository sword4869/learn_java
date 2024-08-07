## 服务端：安装Redis docker

默认配置启动
```bash
docker run -d --name redis -p 6379:6379 redis redis-server --appendonly yes
```

带配置启动：

https://www.cnblogs.com/junyi-bk/p/15056343.html

## 服务端：安装Redis压缩包

### 依赖库
此处选择的Linux版本为CentOS 7.

Redis是基于C语言编写的，因此首先需要安装Redis所需要的gcc依赖：

```sh
yum install -y gcc tcl
```



### 上传安装包并解压

```sh
tar -xzf redis-6.2.6.tar.gz
cd redis-6.2.6
make && make install
```

默认的安装路径是在 `/usr/local/bin`目录下：


该目录已经默认配置到环境变量，因此可以在任意目录下运行这些命令。其中：

- `redis-cli`：是redis提供的命令行客户端
- `redis-server`：`是redis的服务端启动脚本
- `redis-sentinel`：是redis的哨兵启动脚本

### 启动

redis的启动方式有很多种，例如：

- 默认启动
- 指定配置启动
- 开机自启

### 默认启动

安装完成后，在任意目录输入redis-server命令即可启动Redis：

```
redis-server
```

这种启动属于`前台启动`，会阻塞整个会话窗口，窗口关闭或者按下`CTRL + C`则Redis停止。不推荐使用。

### 指定配置启动

如果要让Redis以`后台`方式启动，则必须修改Redis配置文件，就在我们之前解压的redis安装包下（`/usr/local/src/redis-6.2.6`），名字叫`redis.conf`：

我们先将这个配置文件备份一份：

```
cp redis.conf redis.conf.bck
```
然后修改redis.conf文件中的一些配置：

```properties
# 允许访问的地址，默认是127.0.0.1，会导致只能在本地访问。修改为0.0.0.0则可以在任意IP访问，生产环境不要设置为0.0.0.0
bind 0.0.0.0
# 守护进程，修改为yes后即可后台运行
daemonize yes 
# 密码，设置后访问Redis必须输入密码
requirepass 123321
```
Redis的其它常见配置：

```properties
# 监听的端口
port 6379
# 工作目录，默认是当前目录，也就是运行redis-server时的命令，日志、持久化等文件会保存在这个目录
dir .
# 数据库数量，设置为1，代表只使用1个库，默认有16个库，编号0~15
databases 1
# 设置redis能够使用的最大内存
maxmemory 512mb
# 日志文件，默认为空，不记录日志，可以指定日志文件名
logfile "redis.log"
```
启动Redis：

```sh
# 启动
redis-server /usr/local/src/redis-6.2.6/redis.conf
```


### 开机自启

我们也可以通过配置来实现开机自启。

首先，新建一个系统服务文件：

```sh
vi /etc/systemd/system/redis.service
```

内容如下：

```conf
[Unit]
Description=redis-server
After=network.target

[Service]
Type=forking
ExecStart=/usr/local/bin/redis-server /usr/local/src/redis-6.2.6/redis.conf
PrivateTmp=true

[Install]
WantedBy=multi-user.target
```


然后重载系统服务：

```sh
systemctl daemon-reload
```

现在，我们可以用下面这组命令来操作redis了：

```sh
# 启动
systemctl start redis
# 停止
systemctl stop redis
# 重启
systemctl restart redis
# 查看状态
systemctl status redis
```



执行下面的命令，可以让redis开机自启：

```sh
systemctl enable redis
```
## Redis桌面客户端

安装完成Redis，我们就可以操作Redis，实现数据的CRUD了。这需要用到Redis客户端，包括：

- 命令行客户端
- 图形化桌面客户端
- 编程客户端

### Redis命令行客户端

Redis安装完成后就自带了命令行客户端：redis-cli，使用方式如下：

```sh
redis-cli [options] [commonds]
```

```sh
# 停止服务
# 利用redis-cli来执行 shutdown 命令，即可停止 Redis 服务，
# 因为之前配置了密码，因此需要通过 -u 来指定密码
redis-cli -u 123321 shutdown
```

其中常见的options有：

- `-h 127.0.0.1`：指定要连接的redis节点的IP地址，默认是127.0.0.1
- `-p 6379`：指定要连接的redis节点的端口，默认是6379
- `-a 123321`：指定redis的访问密码 

不指定commond时，会进入`redis-cli`的交互控制台：

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407092312131.png)

- `auth [username] [password]`: 登录。
- `ping`：与redis服务端做心跳测试，服务端正常会返回`pong`


### 图形化桌面客户端

GitHub上的大神编写了Redis的图形化桌面客户端，地址：https://github.com/uglide/RedisDesktopManager

不过该仓库提供的是RedisDesktopManager的源码，并未提供windows安装包。

在下面这个仓库可以找到安装包：https://github.com/lework/RedisDesktopManager-Windows/releases


### 建立连接

点击左上角的`连接到Redis服务器`按钮：

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407092312132.png)

在弹出的窗口中填写Redis服务信息：

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407092312133.png)

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407092312134.png)


Redis默认有16个仓库，编号从0至15.  通过配置文件可以设置仓库数量，但是不超过16，并且不能自定义仓库名称。

如果是基于redis-cli连接Redis服务，可以通过select命令来选择数据库：

```sh
# 选择 0号库
select 0
```