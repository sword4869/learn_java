- [DDL](#ddl)
  - [数据库](#数据库)
  - [表](#表)
- [索引](#索引)
- [约束](#约束)
  - [外键](#外键)
  - [唯一](#唯一)
- [DML](#dml)
  - [insert](#insert)
  - [delete](#delete)
    - [不能对同一个表来边查边删](#不能对同一个表来边查边删)
  - [update](#update)
    - [replace函数](#replace函数)
- [DCL](#dcl)

---

# DDL 

```sql
-- 查询所有数据库
show databases;

-- 进入数据库
use 数据库名;
-- 查询当前数据库所有表
show tables;
```
## 数据库
```sql
create database [ if not exists ] 数据库名 [ default charset 字符集 ] [ collate 排序
规则 ];

create database itcast;
create database if not extists itcast;
create database itheima default charset utf8mb4;
```
```sql
drop database [ if exists ] 数据库名;
```

## 表
```sql
-- 查看指定表结构
desc 表名;
-- 查询指定表的建表语句
show create table 表名 ;
```
```sql
-- 表名和字段名不需要用引号括起来，加了反而错！！！
create table tb_user
(
    -- 字段名 字段类型
    id      int(10)        not null   comment '编号' primary key,
    name    varchar(50)    not null   comment '姓名',
    age     int            not null   comment '年龄',
    gender  char(1)        not null   comment '性别'
) comment '用户表';


create table xttblog
(
    id int not null,
    k int,
    primary key (id),       -- 主键 primary key
    index (k),               -- 索引 index (column_name)
    foreign key (k) references employees_test(id)   -- 外键
) engine = InnoDB;
```
```sql
-- 修改表名 rename to
alter table 表名 rename to 新表名;

-- 添加字段 add
-- 默认最后一列增加列：alter table 表名 add column ……
-- 指定位置增加列：alter table 表名 add column …… after 列名
-- 在第一列增加列：alter table 表名 add column …… first
alter table actor add column create_date datetime not null default '2020-10-01 00:00:00';

-- 修改数据类型 modify
alter table 表名 modify 字段名 新数据类型 (长度);

-- 修改字段名和字段类型 change
alter table 表名 change 旧字段名 新字段名 类型 (长度) [ COMMENT 1 注释 ] [ 约束 ];
alter table emp change nickname username varchar(30) comment '昵称';

-- 删除字段 drop
alter table 表名 drop 字段名;
```
```sql
-- 删除
DROP TABLE [ IF EXISTS ] 表名;

-- 清空数据
TRUNCATE TABLE 表名;
```

# 索引
```sql
-- 查看
show index from employees;
```
```sql
-- 一次可以创建多个索引
alter table actor add primary key (id),
                  add index idx_lastname(last_name),
                  add unique index uniq_idx_firstname(first_name), 
                  add fulltext index fulltext_idx_text(text)
                  add spatial index spatial_idx_text(geo);

-- 但不能创建 primary key
create [unique | fulltext | spatial] index idx_name on table_name(col_list);
```

```sql
-- 删除
alter table table_name drop index index_name;
alter table table_name drop primary key;

drop index index_name on table_name;
```

```sql
-- 强制使用索引，from 表后面 force index(idx_name)。
select * from user force index(idx_name) where age = 18;

-- 例子：使用不同的索引而导致查询时间不同
-- 没有指定索引：Mysql选择使用userid这个索引，而userid这个查询只有下界，没有上界，从而需要查询的数据量就很大了，查询时间太长了
SELECT * FROM `pay` WHERE userid > 100 AND updatetime >= 'xxxx-xx-xx xx:xx:xx' AND updatetime <= 'xxxx-xx-xx xx:xx:xx' GROUP BY userid
-- 强制MySQL使用updatetime这个索引，范围小，查询快。
SELECT * FROM `pay` FORCE INDEX(updatetime) WHERE userid > 100 AND updatetime >= 'xxxx-xx-xx xx:xx:xx' AND updatetime <= 'xxxx-xx-xx xx:xx:xx' GROUP BY userid
```

# 约束

## 外键

```sql
-- 方式一：alter
alter table audit add constraint foreign key (emp_no) references employees_test(id)

-- 方式二：创建时
CREATE TABLE audit (
    EMP_no INT NOT NULL,
    create_date datetime NOT NULL,
    -- 一样的语法
    foreign key (emp_no) references employees_test(id)
);
```
## 唯一

```sql
-- 方式二：创建时
CREATE TABLE audit (
    file_id varchar(120),
    constraint unique_fileid unique(file_id)
);
```

# DML

## insert
```sql
-- 指定字段添加数据
INSERT INTO 表名 (字段名1, 字段名2, ...) VALUES (值1, 值2, ...);
INSERT INTO 表名 (字段名1, 字段名2, ...) VALUES (值1, 值2, ...), (值1, 值2, ...);

-- 全部字段添加数据
INSERT INTO 表名 VALUES (值1, 值2, ...);
INSERT INTO 表名 VALUES (值1, 值2, ...), (值1, 值2, ...);
```
mysql中常用的三种插入数据的语句:
- `insert into`表示插入数据. 
  
    数据库会检查主键，如果出现重复会报错；
- `insert ignore`表示只插入不存在的数据.
    
    表示如果中已经存在主键相同的记录，则忽略当前新数据，否则插入；
- `replace into`表示插入替换数据
  
    需求表中有PrimaryKey 或者unique索引.
    
    如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
```sql
insert ignore into actor values("3","ED","CHASE","2006-02-15 12:34:33");
```

## delete
```sql
DELETE FROM 表名 [ WHERE 条件 ] ;
delete from employee 1 where gender = '女';
delete from employee;
```
错，`DELETE * FROM 商品 WHERE 价格>3000`

对，`DELETE FROM 商品 WHERE 价格>3000`

DELETE不需要列名或通配符。DELETE 语句不能删除某一个字段的值(可以使用UPDATE，将该字段值置为NULL即可)，只能删除整行。

### 不能对同一个表来边查边删

```sql
-- SQL236 删除emp_no重复的记录，只保留最小的id对应的

-- 错误
delete from titles_test where id not in
(
   select min(id) from titles_test group by emp_no
)

-- 再套一层起别名，就正确了
delete from titles_test where id not in
(
   select * from
   (select min(id) from titles_test group by emp_no) a
)
```


## update

```sql
update 表名 set 字段名1 = 值1 , 字段名2 = 值2 , .... 1 [ WHERE 条件 ] ;
-- 指定行
update employee set name = 'itheima' where id = 1;
-- 所有行
update employee set name = 'itheima';
```

### replace函数

`字段 = replace(字段，目标老值，新值)`

```sql
-- sql238 将id=5以及emp_no=10001的行数据替换成id=5以及emp_no=10005,其他数据保持不变
update titles_test set emp_no = replace(emp_no, 10001, 10005) where id = 5

-- 等同
update titles_test set emp_no = 10005 where id = 5 and emp_no = 10001
```

PS：好像是只能指定字段？不能用列值

```sql
-- sql 242 自定义。
update salaries set salary = replace(salary, salary, slary * 1.1) -- error
```

# DCL

```sql
-- 查询用户
select * from mysql.user;

-- 创建用户
CREATE USER '用户名'@'主机名' IDENTIFIED BY '密码';

-- 修改用户密码
ALTER USER '用户名'@'主机名' IDENTIFIED WITH mysql_native_password BY '新密码' ;

-- 删除用户
DROP USER '用户名'@'主机名' ;

• 主机名可以使用 % 通配
```

![alt text](../../images/image-392.png)‘

```sql
-- 查询权限
SHOW GRANTS FOR '用户名'@'主机名' ;

-- 授予权限
GRANT 权限列表 ON 数据库名.表名 TO '用户名'@'主机名';
grant all on itcast.* to 'heima'@'%';   -- 授予 'heima'@'%' 用户itcast数据库所有表的所有操作权限

-- 撤销权限
REVOKE 权限列表 ON 数据库名.表名 FROM '用户名'@'主机名';


• 多个权限之间，使用逗号分隔
• 授权时， 数据库名和表名可以使用 * 进行通配，代表所有。
```