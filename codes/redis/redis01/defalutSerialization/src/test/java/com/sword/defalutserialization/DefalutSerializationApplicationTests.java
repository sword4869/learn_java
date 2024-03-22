package com.sword.defalutserialization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class DefalutSerializationApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("name", "Jackson");
        String name = (String)ops.get("name");
        System.out.println(name);
    }
}
