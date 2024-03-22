
1. 引入依赖

    ```xml
    <!--redis依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <!--连接池依赖，jedis和lettuce的连接池都需要-->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>
    ```
   
2. 设置redis配置文件。spring-boot-starter-data-redis 默认使用lettuce，如果想要使用jedis，还要额外引入jedis的依赖。
    
    ```yaml
    spring:
      redis:
        host: 192.168.101.65
        port: 6379
        password: redis
        # 默认0号数据库
        database: 13
        # 想要使用连接池，就必须在配置中指定，否则无效
        lettuce:
          pool:
            max-active: 8
            max-idle: 8
            min-idle: 0
            max-wait: 1000ms
    ```

3. 注入RedisTemplate。因为有了SpringBoot的自动装配，我们可以拿来就用：

    ```java
    @Autowired
    private RedisTemplate redisTemplate;
    ```
