package com.sword.stringredistemplate;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class StringRedisTemplateApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    // JSON序列化工具
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void stringTest() {
        // string
        stringRedisTemplate.opsForValue().set("exercise:name", "Man");

        String s = stringRedisTemplate.opsForValue().get("exercise:name");

        System.out.println(s);
    }

    @Test
    @Description("")
    void setUserByString() throws JsonProcessingException {
        // 手动json化 Bean
        User user = new User("Tom", 18);

        // 手动序列化
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);   // {"name":"Tom","age":18}
        stringRedisTemplate.opsForValue().set("exercise:User:string:person", json);

        String getJson = stringRedisTemplate.opsForValue().get("exercise:User:string:person");
        // 手动反序列化
        User getUser = objectMapper.readValue(getJson, User.class);
        System.out.println(getUser);    // User(name=Tom, age=18)
    }

    @Test
    @Description("")
    void setUserByHash() throws JsonProcessingException {
        // bean to map
        User user = new User("Tom", 18);
        Map<String, Object> map = BeanUtil.beanToMap(user, new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true).setFieldValueEditor((k, v) -> v.toString()));
        stringRedisTemplate.opsForHash().putAll("exercise:User:hash:person", map);

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("exercise:User:hash:person");
        User reuslt = BeanUtil.fillBeanWithMap(entries, new User(), false);
        System.out.println(reuslt);    // User(name=Tom, age=18)
    }

}
