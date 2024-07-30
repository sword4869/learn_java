## 检测 安装是否成功

[Node.js — 在任何地方运行 JavaScript (nodejs.org)](https://nodejs.org/zh-cn)

```cmd
node -v             // 显示安装的 node.js 的版本
npm -v              // 显示安装的 npm 版本
```

## 更换淘宝镜像

```cmd
npm config set registry https://registry.npmmirror.com
```

## 下载路径配置

创建文件夹

- `D:\nodejs\node_global\node_modules`
- `D:\nodejs\node_cache`

```cmd
npm config set prefix "D:\nodejs\node_global"
npm config set cache "D:\nodejs\node_cache"
```

修改环境变量：

- 用户PATH: 将原本npm路径，替换为新 global 的路径 `D:\nodejs\node_global`
- 系统，新建`NODE_PATH`, 值为 `D:\nodejs\node_global\node_modules`
- 系统PATH: 添加 `%NODE_PATH%`.

```cmd
// -g是全局安装的意思，不加 -g 就是默认下载到当前目录
npm install express -g
```

可以看到，下载的 express 模块成功下载到全局的指定目录

## 安装模块

vue

```cmd
npm install -g vue-cli@2.9.6

vue -V      // 注意后边是 大写 V
```

webpack

```cmd
npm install webpack -g
```

## 其他

[避坑了避坑了！！！全网最详细Nodejs安装配置_node安装配置-CSDN博客](https://blog.csdn.net/weixin_45754463/article/details/135279187)

## 代替默认的命令行工具 npm

### pnpm

```cmd
npm install -g pnpm
```

### cnpm

我们不选，npm的使用还没遇到过问题。

```cmd
// 更换镜像为淘宝镜像
npm install -g cnpm --registry=https://registry.npmmirror.com
```

例如

```cmd
cnpm install -g vue-cli@2.9.6
```