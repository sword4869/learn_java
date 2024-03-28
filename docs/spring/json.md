## fastjson
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.76</version>
</dependency>
```
```java
// 手动序列化
User user = new User("Tom", 18);
String json = JSONObject.toJSONString(user);
```

## jackson
```xml
<!--Jackson依赖: ObjectMapper要用-->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```
```java
// JSON序列化工具
ObjectMapper objectMapper = new ObjectMapper();

User user = new User("Tom", 18);
// 手动序列化
String json = objectMapper.writeValueAsString(user);
System.out.println(json);   // {"name":"Tom","age":18}

// 手动反序列化
User getUser = objectMapper.readValue(json, User.class);
```