参考：[Window下Redis的安装和部署详细图文教程（Redis的安装和可视化工具的使用）_redis安装-CSDN博客](https://blog.csdn.net/weixin_44893902/article/details/123087435)



[Releases · tporadowski/redis (github.com)](https://github.com/tporadowski/redis/releases)下载zip

解压到`D:\Redis`，并设置环境变量。

```
# 启动Redis服务
redis-server.exe redis.windows.conf

## 服务退出方式1
ctrl-C
## 服务退出方式2
redis-cli.exe shutdown
```

```
redis-cli.exe -h 127.0.0.1 -p 6379
```

