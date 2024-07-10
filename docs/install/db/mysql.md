## mysql-docker

```bash
rm -rf /mydata/mysql

mkdir -p /mydata/mysql/log
mkdir -p /mydata/mysql/data
mkdir -p /mydata/mysql/conf/

cat << EOF > /mydata/mysql/conf/my.cnf
[mysqld]
skip-name-resolve
character_set_server=utf8
server-id=1010
innodb_fast_shutdown=1
EOF

docker run -d \
    --name mysql \
    --restart=always \
    -p 3306:3306 \
    -v /mydata/mysql/log:/var/log/mysql \
    -v /mydata/mysql/data:/var/lib/mysql \
    -v /mydata/mysql/conf:/etc/mysql/conf.d \
    -e MYSQL_ROOT_PASSWORD=123 \
    mysql:5.7

# -e MYSQL_DATABASE=nacos 初始化时创建库

```
验证了，好像不用就可以。
```bash
# 进入数据库，允许远程登录
[root@localhost mysql]# docker exec -it mysql mysql -uroot -p123

// mysql8之前
grant all privileges on *.* to 'root'@'%' identified by '123' with grant option;
flush privileges;

// mysql8
update user set host='%' where user='root';
flush privileges;


// 验证是否成功，去另一台机子，看是否可以登录上
mysql -h 192.168.150.3 -u root -p123
```

PS：记得重装容器的时候，清除 /mydata/mysql/data 和 log 的文件夹。

## mysql-windows

1. 下载链接：[mysql-installer-community-8.0.36.0.msi](https://dev.mysql.com/downloads/installer)

    ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091616938.png)

    ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091616210.png)

2. 没有Custom type，就选Full type吧。

    ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091616212.png)

3. Type and Networking：就用默认端口3306，直接next

4. Accounts and Roles：

    【有密码方式】

    设置密码 `mysql`

    ![image-20240709162853602](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091628701.png)

    【空密码方式】

    安装失败

    > Attempting to start service MySQL80...
    > Failed to start service MySQL80.
    > 只有在任务处于完成状态(RanToCompletion、Faulted 或 Canceled)时才能释放它。
    > Ended configuration step: Starting the server
    > ....
    > 
    搜索“服务”。

    “服务” → 在里面找到 “mysqlXX” → 右键 “属性” → 点击“登录”选项卡。选择 本地统账> 户后 点击确定 ，然后再 右键 启动 就可以了。

    ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091616214.png)

    ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091616215.png)

    接着安装 execute 就没问题了

5. MySQL Router Configuration：finish

6. Connect To Server：

    【有密码方式】

    ![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091633518.png)

    【空密码方式】

    不管直接next。

    因为“本地系统账户”这种方式登录应该是没有密码的，因为默认windows管理员账户是没密码的，因此也就不可能进行connect to server的测试，所以点“Cancel”取消这一步即可。

    ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091616216.png)

    虽然可以登录`mysql -u root -p`或`mysql-cli`直接回车登录MySQL，但是DataGrip配置空密码就链接不上。

    所以必须修改密码：`ALTER USER 'root'@'localhost' IDENTIFIED BY 'mysql';`

7. next，finish。
### 启动指令

- `net start mysql80`
- `net stop mysql80`

```
net stop mysql80
发生系统错误 5。

拒绝访问。
```
答案不是名字大小写，而是要用管理员权限的cmd。

### 客户端连接
1. `C:\Program Files\MySQL\MySQL Server 8.0\bin`有mysql.exe，可选配置环境变量。

    ```bash
    mysql [-h 127.0.0.1] [-P 3306] -u root -p
    ```

2. MySQL COmmand Line Client

    ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091616217.png)
