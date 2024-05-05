- [安装](#安装)
  - [docker](#docker)
    - [nacos + 自带数据库](#nacos--自带数据库)
    - [nacos + mysql](#nacos--mysql)
  - [Windows安装](#windows安装)
    - [下载安装包](#下载安装包)
    - [解压](#解压)
    - [端口配置](#端口配置)
    - [启动](#启动)
    - [访问](#访问)
  - [Linux安装](#linux安装)
    - [安装JDK](#安装jdk)
    - [上传安装包](#上传安装包)
    - [解压](#解压-1)
    - [端口配置](#端口配置-1)
    - [启动](#启动-1)

---

# 安装

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


### 下载安装包

在Nacos的GitHub页面，提供有下载链接，可以下载编译好的Nacos服务端或者源代码：

GitHub主页：https://github.com/alibaba/nacos

GitHub的Release下载页：https://github.com/alibaba/nacos/releases

如图：

![](../../images/image-20210402161102887.png)



本课程采用1.4.1.版本的Nacos，课前资料已经准备了安装包：

![](../../images/image-20210402161130261.png)

windows版本使用`nacos-server-1.4.1.zip`包即可。



### 解压

将这个包解压到任意非中文目录下，如图：

![](../../images/image-20210402161843337.png)

目录说明：

- bin：启动脚本
- conf：配置文件



### 端口配置

Nacos的默认端口是8848，如果你电脑上的其它进程占用了8848端口，请先尝试关闭该进程。

**如果无法关闭占用8848端口的进程**，也可以进入nacos的conf目录，修改配置文件中的端口：

![](../../images/image-20210402162008280.png)

修改其中的内容：

![](../../images/image-20210402162251093.png)



### 启动

启动非常简单，进入bin目录，结构如下：

![](../../images/image-20210402162350977.png)

然后执行命令即可：

- windows命令：

  ```
  startup.cmd -m standalone
  ```


执行后的效果如图：

![](../../images/image-20210402162526774.png)



### 访问

在浏览器输入地址：http://127.0.0.1:8848/nacos即可：

![](../../images/image-20210402162630427.png)

默认的账号和密码都是nacos，进入后：

![](../../images/image-20210402162709515.png)





## Linux安装

Linux或者Mac安装方式与Windows类似。

### 安装JDK

Nacos依赖于JDK运行，索引Linux上也需要安装JDK才行。

上传jdk安装包：

![](../../images/image-20210402172334810.png)

上传到某个目录，例如：`/usr/local/`



然后解压缩：

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

如图：

![](../../images/image-20210402161102887.png)

也可以直接使用课前资料中的tar.gz：

![](../../images/image-20210402161130261.png)

上传到Linux服务器的某个目录，例如`/usr/local/src`目录下：

![](../../images/image-20210402163715580.png)



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

![](../../images/image-20210402163858429.png)

目录内部：

![](../../images/image-20210402164414827.png)



### 端口配置

与windows中类似



### 启动

在nacos/bin目录中，输入命令启动Nacos：

```sh
sh startup.sh -m standalone
```