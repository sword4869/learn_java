
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
    <!--Jackson依赖: GenericJackson2JsonRedisSerializer内部要引入com.fasterxml.jackson-->
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
3. 写config，自定义RedisTemplate Bean。
    
    ```java
    @Configuration
    public class RedisConfig {
        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
            // 创建RedisTemplate对象
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            // 关联到连接工厂
            redisTemplate.setConnectionFactory(redisConnectionFactory);

            RedisSerializer<String> stringRedisSerializer = RedisSerializer.string();
            GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
            // 设置 Key 和 HashKey 的序列化为字符串序列化
            redisTemplate.setKeySerializer(stringRedisSerializer);
            redisTemplate.setHashKeySerializer(stringRedisSerializer);
            // 设置 Value 和 HashValue 的序列化为json序列化
            redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
            redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
            return redisTemplate;
        }
    }
    ```
4. 注入RedisTemplate。

    ```java
    @Autowired
    private RedisTemplate redisTemplate;
    ```
    ```java
    ValueOperations ops = redisTemplate.opsForValue();
    ops.set("name", "Jackson");
    String name = (String)ops.get("name");
    System.out.println(name);
    // Jackson

    User user = new User("Tom", 18);
    // 可以直接传入 Object
    ops.set("person", user);
    // 取出得到的直接强转
    User getUser = (User) ops.get("person");
    System.out.println(getUser);
    // User(name=Tom, age=18)
    ```