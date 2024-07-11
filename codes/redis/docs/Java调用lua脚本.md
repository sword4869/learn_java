## redis命令：EVAL

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112219844.png)

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112219845.png)

## EVAL对应的Java的 RedisTemplate的API

1. lua脚本位置放在classpath下

    ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112219846.png)

2. `stringRedisTemplate.execute()`

```java
class Test{
    // RedisScript是抽象类，它的实现类是 DefaultRedisScript，泛型参数是返回值类型
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        // Resource类型
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        // 返回值类型
        UNLOCK_SCRIPT.setResultType(Long.class);
    }

    public void unlock() {
        // 调用lua脚本
        Long executed = stringRedisTemplate.execute(
            UNLOCK_SCRIPT,
            Collections.singletonList(KEY_PREFIX + name),       // List类型
            ID_PREFIX + Thread.currentThread().getId()          // 可变长度参数
        );
    }
}
```