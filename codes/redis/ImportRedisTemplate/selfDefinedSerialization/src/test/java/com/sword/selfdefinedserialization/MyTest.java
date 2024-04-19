package com.sword.selfdefinedserialization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class MyTest {
    User user = new User("Tom", 18);

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
    void setUserByString() {
        ValueOperations ops = redisTemplate.opsForValue();
        // 可以直接传入 Object
        ops.set("person", user);

        // 取出得到的直接强转
        User getUser = (User) ops.get("person");
        System.out.println(getUser);
        // User(name=Tom, age=18)
    }
}
