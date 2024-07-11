[code: nacos_demo2](../../codes/cloud/nacos_demo2/user-service/src/main/resources/bootstrap.yml)

## 如何使用nacos配置中心

1. 导入依赖: 父工程除了springcloud还要添加 alibaba 依赖管理，子工程是服务发现和配置中心。

    父工程
    ```xml
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>${spring-cloud-alibaba.version}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
    ```
    子模块
    ```xml
    <!-- 配置中心 -→
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
    <!-- spring-cloud-dependencies 2020.0.0 版本开始不在默认加载bootstrap 文件，如果需要加载bootstrap 文件需要手动添加依赖 -→
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
    ```

2. 在nacos中添加配置文件
3. 在user-service中添加一个bootstrap.yml（yaml也行）文件。

bootstrap：只需配置如何找到nacos就行。
```yaml
spring:
  application:
    name: userservice           # dataid的部分1
  # profiles: 
  #   active: dev                 # dataid的部分2。默认没有。
  cloud:
    nacos:
      server-addr: 192.168.150.3:8848 
      config:
        # namespace: dev                  # 定位1。默认public
        # group: xuecheng-plus-project    # 定位2。默认DEFAULT_GROUP
        file-extension: yml    # dataid的部分3。必须改，因为默认是properties
```
nacos: port, mysql, logging, swagger
```yml
server:
  port: 63040

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.101.65:3306/xcplus_content?serverTimezone=UTC&userUnicode=true&useSSL=false&
    username: root
    password: mysql

# 日志文件配置路径
logging:
  config: classpath:log4j2-dev.xml

swagger:
  title: "启明内容管理系统"
  description: "内容系统管理系统对课程相关信息进行管理"
  base-package: com.qiming.content
  enabled: true
  version: 1.0.0
```

## 如何在nacos中添加配置文件

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210500.png)

然后在弹出的表单中，填写配置信息：

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210501.png)

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210502.png)

## bootstrap.yml 和 application.yml 的关系？

因为nacos，而application.yml在nacos后读取，所以需要bootstrap配置nacos地址来去nacos读取配置。

微服务将三者依次合并，才能完成项目启动。

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210503.png)

## 配置优先级

> 读取顺序：

→bootstrap.yml → bootstrap-dev.yml → idea configuration的VM参数 

→ nacos公共配置 → nacos扩展配置 → nacos上的本应用配置（userservice.yml → userservice-dev.yml）

→ application.yml

> 优先级：

本地配置（和启动顺序不同！！！）：bootstrap < application < vm。

nacos配置：nacos公共配置 < nacos扩展配置 < nacos上的本应用配置（userservice.yml < userservice-dev.yml）

不强制本地配置优先时，本地配置被nacos覆盖。即 本地配置 < nacos。

强制本地配置优先时，本地配置不被nacos覆盖。即 nacos < 本地配置。

profiles不写是默认时，bootstrap或nacos的如果有，那么必被读取。

vm的profiles会覆盖bootstrap的profiles。bootstrap和vm后最终的profiles是test，那么结果是**默认和test**起了。则bootstrap和nacos会起默认和test，谁有起谁。
  - 比如，都有，都起。

    ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210504.png)

  - 比如，bootstrap没test，nacos没默认。

    ![bootstrap没test，nacos没默认](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210501.png)


> 强制**本地配置**(bootstrap, application, vm)优先级最高（不被nacos覆盖）：

- 需要在**nacos上的本应用配置**写，只用在 userservice.yml 和 userservice-dev.yml 中的一个写就行。一般写在 userservice.yml ，因为它必被读取。
- nacos发布完还需要重启spring应用才生效。

```yml
spring:
  cloud:
    config:
      override-none: true
```


## nacos如何去定位一个具体的配置文件

namespace、group、dataid. 
1. 通过config中的namespace、group定位配置所在环境。
2. 再通过dataid找到具体的配置文件。
   
    dataid有三部分组成 `spring.application.name`-`spring.profiles.active`-`spring.cloud.nacos.config.file-extension`


```yaml
spring:
  application:
    name: userservice           # dataid的部分1
  # profiles: 
  #   active: dev                 # dataid的部分2。默认没有。
  cloud:
    nacos:
      server-addr: 192.168.150.3:8848 
      config:
        # namespace: dev                  # 定位1。默认public
        # group: xuecheng-plus-project    # 定位2。默认DEFAULT_GROUP
        file-extension: yml    # dataid的部分3。必须改，因为默认是properties
```

PS：`spring.profiles.active`是由yml和vm参数共同决定的，而且vm会覆盖yml中的profiles。
## nacos公共配置

若干项目中配置内容相同的配置。比如：redis的配置，很多项目用的同一套redis服务所以配置也一样。

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210505.png)

```yml
# swagger 文档配置
swagger:
  title: "学成在线内容管理系统"
  description: "内容系统管理系统对课程相关信息进行业务管理数据"
  base-package: com.qiming      # 这里从 com.qiming.content 换成了大包
  enabled: true
  version: 1.0.0
```

```yml
# 省略了注册和配置的东西
spring:
  cloud:
    nacos:
      config:
        shared-configs: # 公共配置
          - data-id: swagger-${spring.profiles.active}.yaml
            group: xuecheng-plus-common
            refresh: true
          - data-id: logging-${spring.profiles.active}.yaml
            group: xuecheng-plus-common
            refresh: true
```

## nacos扩展配置

如api和service因为数据库而分开，api引用servcie的配置。

和公共配置的不同是，属于极个别配置。

```yml
# 省略了注册和配置的东西
spring:
  cloud:
    nacos:
      config:
        extension-configs: 
          - data-id: content-service-${spring.profiles.active}.yaml
            group: xuecheng-plus-project
            refresh: true
```

## 日常使用
### 是否需要重新maven编译

删除了nacos配置，需要重新编译。

新增、修改了nacos配置，开启刷新就行，不用重新编译。

新增nacos配置中有强制本地配置优先、修改了原本nacos配置去添加或是删除强制本地配置优先，都需要重新编译。
### 注入的两种方式

[同application配置文件的注入](../spring/基础.md#application配置文件的注入)

涉及到nacos配置热更新，有一点区别：@Value的用法，要在有@Value注入属性的类上加`@RefreshScope`，[UserController2](../../codes/cloud/nacos_demo2/user-service/src/main/java/com/sword/user/controller/UserController2.java)

@ConfigurationProperties则无须多做，它本身就是自动读取配置的。

### 远程配置的默认值

因为先加载 bootstrap，再加载nacos，所以我们一般在nacos的远程配置中，注入本地配置的属性。

本地的 bootstrap.yml
```yml
tj:
  mq:
    host: 192.168.150.102
```

shared-mq.yaml
```yml
spring:
  rabbitmq:
    host: ${tj.mq.host:192.168.150.101} # mq的IP
```

如果本地写了 `tj.mq.host`，那么远程配置就采用；本地没写，远程就使用默认值 `192.168.150.101`


### 如何修改spring.profiles.active


方式1：修改 bootstrap.yml

```yml
spring:
  profiles: 
    active: dev
```

方式2：修改idea的配置（vm参数或Active profiles），会覆盖bootstrap.yml的修改。

Active profiles会覆盖vm参数，大概因为其会被转化为vm参数拼接在原本vm参数后面。

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210506.png)

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210507.png)

PS：注意不能嵌套自身。

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112210508.png)