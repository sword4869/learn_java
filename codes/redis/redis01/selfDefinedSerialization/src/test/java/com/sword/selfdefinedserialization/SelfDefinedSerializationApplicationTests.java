package com.sword.selfdefinedserialization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SelfDefinedSerializationApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void setName() {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("name", "Jackson");
        String name = (String) ops.get("name");
        System.out.println(name);
        // Jackson
    }

    @Test
    void setUser() {
        ValueOperations ops = redisTemplate.opsForValue();
        User user = new User("Tom", 18);
        // 可以直接传入 Object
        ops.set("person", user);
        // 取出得到的直接强转
        User getUser = (User) ops.get("person");
        System.out.println(getUser);
        // User(name=Tom, age=18)
    }

    @Test
    void hsetUser() {
        // hset的方法就偏向java的hashset风格
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();

        ops.put("person2", "name", "Alice");
        ops.putAll("person2", new HashMap<String, String>(){{put("addr", "Xian");}});

        String name = (String) ops.get("person2", "name");
        Map<Object, Object> person2 = ops.entries("person2");
        System.out.println(person2);    // {name=Alice, addr=Xian}
    }
}
