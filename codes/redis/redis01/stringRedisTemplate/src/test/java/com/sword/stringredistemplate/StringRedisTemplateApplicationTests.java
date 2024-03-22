package com.sword.stringredistemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class StringRedisTemplateApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    // JSON序列化工具
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Description("手动转化Object，set/get都是json字符串")
    void setUser() throws JsonProcessingException {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        User user = new User("Tom", 18);
        // 手动序列化
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);   // {"name":"Tom","age":18}
        ops.set("person", json);

        String getJson = ops.get("person");
        // 手动反序列化
        User getUser = objectMapper.readValue(getJson, User.class);
        System.out.println(getUser);    // User(name=Tom, age=18)
    }

    @Test
    void other() throws JsonProcessingException {
        // StringRedisTemplate的方法同RedisTemplate一样使用
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("name", "Jackson");
        String name = (String) ops.get("name");
        System.out.println(name);   // Jackson

        // hset的方法就偏向java的hashset风格
        HashOperations<String, Object, Object> ops2 = stringRedisTemplate.opsForHash();

        ops2.put("person2", "name", "Alice");
        ops2.putAll("person2", new HashMap<String, String>(){{put("addr", "Xian");}});

        String name = (String) ops2.get("person2", "name");
        Map<Object, Object> person2 = ops2.entries("person2");
        System.out.println(person2);    // {name=Alice, addr=Xian}
    }

}
