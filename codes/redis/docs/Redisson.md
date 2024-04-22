## 依赖导入
```xml
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson</artifactId>
    <version>3.12.3</version>
</dependency>
```

## 创建客户端 RedissonClient

```java
public class Test {
    public static void main(String[] args) {
        // 1. 配置类
        Config config = new Config();
        // 集群则是 config.useClusterServers()
        config.useSingleServer()        
                .setAddress("redis://127.0.0.1:6379")
                .setPassword("123456")
                .setDatabase(0);    // more是0号
        // config.setTransportMode(TransportMode.EPOLL); // 默认是NIO的方式

        // 2. 创建客户端
        RedissonClient redissonClient = Redisson.create(config);
        //获取所有的key
        redissonClient.getKeys().getKeys().forEach(key -> System.out.println(key));
        //关闭客户端
        redissonClient.shutdown();
    }
}
```

变成配置类
```java
@Configuration
public class  RedissonConfig {

    @Value("${spring.redis.host}")
    String redistHost;

    @Value("${spring.redis.port}")
    String redisPort;

    @Value("${spring.redis.password}")
    String redisPassword;

    @Bean
    public RedissonClient redissonClient(){
        // 1. 配置类
        Config config = new Config();
        config.useSingleServer()
            .setAddress("redis://" + redistHost + ":" + redisPort)
            .setPassword(redisPassword);
        
        // 2. 创建客户端
        return Redisson.create(config);
    }
}
```

## 可重入锁

可重入锁: `RLock lock = redissonClient.getLock("lock:order:1")`

```java
// lock.tryLock() 会抛出中断异常 InterruptedException
void method1() throws InterruptedException {
    // 可重入锁
    RLock lock = redissonClient.getLock("lock:order:1");

    // 尝试获取锁：获取锁的最大等待时间（期间会重试），锁超时释放时间，时间单位
    /* 
        lock.tryLock();    不重试直接返回
        lock.tryLock(1, TimeUnit.SECONDS);   
        lock.tryLock(1, 10, TimeUnit.SECONDS) 
    */
    boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);

    if (!isLock) {
        log.error("获取锁失败 .... 1");
        return;
    }
    try {
        log.info("获取锁成功 .... 1");
        log.info("开始执行业务 ... 1");
    } finally {
        log.warn("准备释放锁 .... 1");
        lock.unlock();
    }
}
```