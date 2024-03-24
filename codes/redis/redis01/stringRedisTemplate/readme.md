
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
    <!--Jackson依赖: ObjectMapper要用-->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
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
3. 注入 StringRedisTemplate。

    ```java
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    ```
    ```java
    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
    // JSON序列化工具
    ObjectMapper objectMapper = new ObjectMapper();

    User user = new User("Tom", 18);
    // 手动序列化
    String json = objectMapper.writeValueAsString(user);
    System.out.println(json);   // {"name":"Tom","age":18}
    ops.set("person", json);

    String getJson = ops.get("person");
    // 手动反序列化
    User getUser = objectMapper.readValue(getJson, User.class);
    System.out.println(getUser);    // User(name=Tom, age=18)
    ```