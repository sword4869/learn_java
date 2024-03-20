- conf/nginx.conf：配置文件
- html：静态资源

`nginx -s reload` 重启

配置文件中语句以`;`结尾！

ngigx网关，负载均衡：`http://192.168.101.65:9000`→`http://localhost/video`
```bash
worker_processes  1;
events {
    worker_connections  1024;
}
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
        location /video{
            proxy_pass http://videoerver;
        }
        # 有后缀时 http://localhost/video/xxx 必须以/结尾
        location /video/{
            proxy_pass http://videoerver/;
        }
    }
}
```
ngix网关+spring网关： `http://127.0.0.1:63010/content/coursepreview/74`→`http://localhost/api/content/coursepreview/74`
```
#后台网关
upstream gatewayserver{
    server 127.0.0.1:63010 weight=10;
} 
server {
    location /api/ {
        proxy_pass http://gatewayserver/;
    } 
```