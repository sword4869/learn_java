## docker
### nacos + 自带数据库

```bash
docker run -d \
  --name nacos \
  -p 8848:8848 \
  -e MODE=standalone \
  nacos/nacos-server:v2.1.0-slim
```

成功！

`http://192.168.150.3:8848` 是404，但起码证明启动成功了，否则连打开都没有。

`http://192.168.150.3:8848/nacos` 是登录界面。
### nacos + mysql

参见 [docker](docker/docker-compose.yml)

## Windows安装

Nacos依赖于JDK运行，需要安装JDK才行。


### 下载安装包

GitHub的Release下载页：[Release 2.1.0 (Apr 29, 2022) · alibaba/nacos (github.com)](https://github.com/alibaba/nacos/releases/tag/2.1.0)

如图：

![image-20240710163020644](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407101630707.png)



windows版本使用`nacos-server-1.4.1.zip`包即可。



### 解压

将这个包解压到任意非中文目录下，如图：

![image-20240710163055777](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407101630877.png)

目录说明：

- bin：启动脚本
- conf：配置文件

### 配置数据库

在`conf`目录中会发现存在一个`nacos-mysql.sql`文件

(1) 创建nacos数据库

(2) 导入该脚本

### 配置文件

![image-20240710163118875](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407101631939.png)

开启mysql：原本这些都是注释掉的。

修改`db.url.0`, `db.user.0`, `db.password.0`。

注意 `serverTimezone`参数。

```properties
#*************** Config Module Related Configurations ***************#
### If use MySQL as datasource:
spring.datasource.platform=mysql

### Count of DB:
db.num=1

### Connect URL of DB:
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user.0=root
db.password.0=mysql
```

### 启动

进入bin目录，然后执行命令即可：

```cmd
.\startup.cmd -m standalone
```



 其中`-m standalone`指定为单机模式，否则以cluster集群模式启动。

### 访问

在浏览器输入地址：http://127.0.0.1:8848/nacos 即可：默认的账号和密码都是nacos

## Linux安装

### 安装JDK

Nacos依赖于JDK运行，需要安装JDK才行。



然后解压缩到`/usr/local/`：

```sh
tar -xvf jdk-8u144-linux-x64.tar.gz
```

然后重命名为java



配置环境变量：

```sh
export JAVA_HOME=/usr/local/java
export PATH=$PATH:$JAVA_HOME/bin
```

设置环境变量：

```sh
source /etc/profile
```





### 上传安装包

上传到Linux服务器的某个目录，例如`/usr/local/src`目录下：

### 解压

命令解压缩安装包：

```sh
tar -xvf nacos-server-1.4.1.tar.gz
```

然后删除安装包：

```sh
rm -rf nacos-server-1.4.1.tar.gz
```

目录中最终样式：

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407101647570.png)

### 端口配置

与windows中类似



### 启动

在nacos/bin目录中，输入命令启动Nacos：

```sh
sh startup.sh -m standalone
```