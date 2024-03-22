- [1. Jedis客户端](#1-jedis客户端)
  - [1.1. 直接创建Jedis](#11-直接创建jedis)
  - [1.2. 连接池](#12-连接池)
- [2. SpringDataRedis客户端](#2-springdataredis客户端)
  - [2.1. 默认序列化](#21-默认序列化)
  - [2.2. 自定义序列化](#22-自定义序列化)
  - [2.3. StringRedisTemplate](#23-stringredistemplate)

---

## 1. Jedis客户端

Jedis的官网地址： https://github.com/redis/jedis

在Redis官网中提供了各种语言的客户端，地址：https://redis.io/docs/clients/

其中Java客户端也包含很多：

![alt text](../../images/image-80.png)

- Jedis和Lettuce：这两个主要是提供了Redis命令对应的API，方便我们操作Redis，而SpringDataRedis又对这两种做了抽象和封装，因此我们后期会直接以SpringDataRedis来学习。
- Redisson：是在Redis基础上实现了分布式的可伸缩的java数据结构，例如Map、Queue等，而且支持跨进程的同步机制：Lock、Semaphore等待，比较适合用来实现特殊的功能需求。

### 1.1. 直接创建Jedis

Jedis本身是线程不安全的，并且频繁的创建和销毁连接会有性能损耗


1）引入依赖：

```xml
<!--jedis-->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.7.0</version>
</dependency>
```

[JedisDirectConnection](/codes/redis/jedis/src/main/java/com/sword/jedis/JedisDirectConnection.java)
```java
// 1.建立连接 host port timeout
Jedis jedis = new Jedis("192.168.101.65", 6379, 3000);
// 2.设置密码
jedis.auth("redis");
// 3.选择库
jedis.select(13);


String result = jedis.set("name", "Jack");
System.out.println(result); // OK
String name = jedis.get("name");
System.out.println(name);   // Jack

Long result2 = jedis.rpush("characters", "A", "B");
System.out.println(result2); // 2
List<String> characters = jedis.lrange("characters", 0, -1);
System.out.println(characters); // [A, B]


if (jedis != null) {
    jedis.close();
}
```

### 1.2. 连接池

我们推荐大家使用Jedis连接池代替Jedis的直连方式。

[JedisPoolConnection](/codes/redis/jedis/src/main/java/com/sword/jedis/JedisPoolConnection.java)
```java
JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
jedisPoolConfig.setMaxTotal(8);     // 连接池最大连接数，设为-1表示不限制
jedisPoolConfig.setMaxIdle(8);      // 最大空闲连接数，一开始初始化连接池时会创建这么多连接，让服务启动时就有连接可用。空闲连接超时后会被回收
jedisPoolConfig.setMinIdle(0);      // 最小空闲连接数
jedisPoolConfig.setMaxWaitMillis(1000); // 获取连接的最大等待时间，超过这个时间会抛出异常

// 创建连接池
// config, host, port, timeout, password
JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.101.65", 6379, 1000, "redis");
Jedis jedis = jedisPool.getResource();

String result = jedis.set("name", "Jack");
System.out.println(result); // OK
String name = jedis.get("name");
System.out.println(name);   // Jack

jedis.close();
jedisPool.close();
```

## 2. SpringDataRedis客户端

SpringData是Spring中数据操作的模块，包含对各种数据库的集成，其中对Redis的集成模块就叫做SpringDataRedis，官网地址：https://spring.io/projects/spring-data-redis

- 提供了对不同Redis客户端的整合（Lettuce和Jedis）
- 提供了RedisTemplate统一API来操作Redis
- 支持Redis的发布订阅模型
- 支持Redis哨兵和Redis集群
- 支持基于Lettuce的响应式编程
- 支持基于JDK、JSON、字符串、Spring对象的数据序列化及反序列化
- 支持基于Redis的JDKCollection实现



SpringDataRedis中提供了RedisTemplate工具类，其中封装了各种对Redis的操作。并且将不同数据类型的操作API封装到了不同的类型中：

![](assets/UFlNIV0.png)



### 2.1. 默认序列化

[defalutSerialization](/codes/redis/redis01/defalutSerialization/src/test/java/com/sword/defalutserialization/DefalutSerializationApplicationTests.java)

存在的问题：`ops.set("name", "Jackson")`中将`"name"`当成Object序列化（不止key，还有hashKey, value, hashValue），都使用`JdkSerializationRedisSerializer`，底层是jdk默认序列化`ObjectOutputStream`。

这样得到的序列化结果不好。本来是修改redis中的`name`key，结果java修改的是`\xAC\xED\x00\x05t\x00\x04name`key。
```
centos7:13>keys *
1) "name"
2) "\xAC\xED\x00\x05t\x00\x04name"

centos7:13>get \xAC\xED\x00\x05t\x00\x04name
"\xAC\xED\x00\x05t\x00\x07Jackson"
```


缺点：

- 可读性差
- 内存占用较大

### 2.2. 自定义序列化

[defalutSerialization](/codes/redis/redis01/defalutSerialization/src/test/java/com/sword/defalutserialization/DefalutSerializationApplicationTests.java)

整体可读性有了很大提升，并且能将Java对象自动的序列化为JSON字符串，并且查询时能自动把JSON反序列化为Java对象。

```
centos7:13>get name
""Jackson""
centos7:13>get person
"{"@class":"com.sword.selfdefinedserialization.User","name":"Tom","age":18}"
```
不过，其中记录了序列化时对应的class名称，目的是为了查询时实现自动反序列化。这会带来额外的内存开销。

### 2.3. StringRedisTemplate

[StringRedisTemplateApplicationTests](/codes/redis/redis01/stringRedisTemplate/src/test/java/com/sword/stringredistemplate/StringRedisTemplateApplicationTests.java)

为了节省内存空间，我们不使用JSON序列化器来处理value，而是统一使用String序列化器。当需要存储Java对象时，手动完成对象的序列化和反序列化，将其转化为Json字符串。

这种用法比较普遍，因此SpringDataRedis就提供了RedisTemplate的子类：StringRedisTemplate，它的key和value的序列化方式默认就是String方式。

```
centos7:13>get person
"{"name":"Tom","age":18}"
centos7:13>get name
"Jackson"
```