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
    void test() {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("name", "Jackson");
        String name = (String)ops.get("name");
        System.out.println(name);   // Jackson
    }

    @Test
    void test2(){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("person0", new User("Anki", 12));

        User o = (User) ops.get("person0");
        System.out.println(o);  // User(name=Anki, age=12)

    }
}
