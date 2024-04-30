- [导读](#导读)
- [conf编写](#conf编写)
  - [最常用：公共 listen 和server\_name](#最常用公共-listen-和server_name)
  - [域名解析：多个server](#域名解析多个server)
  - [ngix反向代理、负载均衡](#ngix反向代理负载均衡)

---
## 导读
- conf/nginx.conf：配置文件
- html：静态资源

`nginx -s reload | stop`。可执行文件，windows执行在nginx目录下，linux在nginx的sbin下。



nginx网关，负载均衡：`http://192.168.101.65:9000`→`http://localhost/video`。

nginx 有2个线程，work和master。

## conf编写

配置文件中语句以`;`结尾！
- 一个http，http内部可以有多个server
- 每个server主要有三项，listen端口，server_name域名，location路径。location可以有多个。
- 当多个server的listen和server_name都相同时，可以写在http中作为公共的。

### 最常用：公共 listen 和server_name
```bash
http {
    # 公共
    listen       80;
    server_name  localhost;

    server {
        location / {
            root   html;
            index  index.html index.htm;
        }

        location /api {
            proxy_pass http://192.168.101.65:9000;
        }
    }
}
```
### 域名解析：多个server

访问时，`http://localhost`, `http://git.tianji.com`, `http://jenkins.tianji.com` 会根据 `server_name`分别对应到不同的server。
```bash
http {
    # 多个server
    ### http://localhost
    server {
        listen       80;
        server_name  localhost;
        location / {
            root   html;
            index  index.html index.htm;
        }
    }
    ### http://git.tianji.com
    server {
       listen       80;
       server_name  git.tianji.com;
       location / {
            proxy_pass http://localhost:10880;
       }
    }
    ### http://jenkins.tianji.com
    server {
       listen       80;
       server_name  jenkins.tianji.com;
       location / {
            proxy_pass http://localhost:18080;
       }
    }
}
```
### ngix反向代理、负载均衡

`http://192.168.101.64:9000/content/coursepreview/74`→`http://localhost/api/content/coursepreview/74`
```bash
http {
    # 默认
    listen       80;
    server_name  localhost;

    # upstream 放在http里面
    upstream videoerver{
        server 192.168.101.65:9000 weight=10;
        server 192.168.101.64:9000 weight=10;   # 负载均衡的权重
    }
    server{
        # 在server中配置映射路径
        # 只有 http://localhost/video 这样时
        location /api/{
            proxy_pass http://videoerver;
        }
        # 有后缀时 http://localhost/video/xxx 必须以/结尾
        location /api/{
            proxy_pass http://videoerver/;
        }
    }
}
```