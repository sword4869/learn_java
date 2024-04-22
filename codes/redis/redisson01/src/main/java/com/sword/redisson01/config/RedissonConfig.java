package com.sword.redisson01.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class  RedissonConfig {

    @Value("${spring.redis.host}")
    String redistHost;

    @Value("${spring.redis.port}")
    String redisPort;

    @Value("${spring.redis.password}")
    String redisPassword;

    @Bean
    public RedissonClient redissonClient(){
        // 1. 配置类
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redistHost + ":" + redisPort)
                .setPassword(redisPassword);

        // 2. 创建客户端
        return Redisson.create(config);
    }
}
