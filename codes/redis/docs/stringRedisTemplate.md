- [1. string](#1-string)
  - [1.1. 将User类存储为string类型](#11-将user类存储为string类型)
- [2. hash](#2-hash)
  - [2.1. 将User类存储为hash类型](#21-将user类存储为hash类型)

---

[完整的用法可以看这里，偷懒没有自己写](https://blog.csdn.net/weixin_43835717/article/details/92802040)

通用：想要删除整个key 
```java
// 不存在也不会报错
stringRedisTemplate.delete(key);
```

## 1. string

```java
stringRedisTemplate.opsForValue().set("exercise:name", "Man");

String s = stringRedisTemplate.opsForValue().get("exercise:name");
```

[stringTest()](../stringRedisTemplateExercise/src/test/java/com/sword/stringredistemplate/StringRedisTemplateApplicationTests.java)

```java
// 没有直接自动创建。
long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);
```
```java
// 可能是null
Boolean success = stringRedisTemplate.opsForValue()
        .setIfAbsent(KEY_PREFIX + name, threadId, timeoutSec, TimeUnit.SECONDS);
boolean success_flag = Boolean.TRUE.equals(success);
```
### 1.1. 将User类存储为string类型


[setUserByString()](../stringRedisTemplateExercise/src/test/java/com/sword/stringredistemplate/StringRedisTemplateApplicationTests.java)

## 2. hash
删除：想要删除整个key。
```java
String key  = LOGIN_USER_KEY + token;
Object[] hashKeys = stringRedisTemplate.opsForHash().keys(key).toArray();
stringRedisTemplate.opsForHash().delete(key, hashKeys);


String key  = LOGIN_USER_KEY + token;
stringRedisTemplate.delete(key);
```
### 2.1. 将User类存储为hash类型

[setUserByHash()](../stringRedisTemplateExercise/src/test/java/com/sword/stringredistemplate/StringRedisTemplateApplicationTests.java)
```java
/*
public class UserDTO {
    // 注意是Long类型
    private Long id;
    private String nickName;
    private String icon;
}
*/

UserDTO userDTO = new UserDTO();

// BeanUtil.beanToMap(userDTO); 不行
Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
        CopyOptions.create()
            .setIgnoreNullValue(true)      // 忽略 null 值的字段
            .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));    // 将 value 转化为 String 类型

stringRedisTemplate.opsForHash().putAll("tokenKey", userMap);
```

`BeanUtil.beanToMap(userDTO);` 得到的 map 是 `Map<String, Object>`，字段`id` 对应的 value 类型是 `Long`。而 `stringRedisTemplate` 要求 value类型是 `String`。


```java
// 要求 key 和 value类型都是 `String`
public class StringRedisTemplate extends RedisTemplate<String, String> {
    public StringRedisTemplate() {
        // 都采用 string 序列化方式
        this.setKeySerializer(RedisSerializer.string());
        this.setValueSerializer(RedisSerializer.string());
        this.setHashKeySerializer(RedisSerializer.string());
        this.setHashValueSerializer(RedisSerializer.string());
    }
    ...
}
```