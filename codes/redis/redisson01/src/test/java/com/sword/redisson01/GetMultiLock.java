package com.sword.redisson01;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
public class GetMultiLock {
    private RedissonClient redissonClient1;
    private RedissonClient redissonClient2;
    private RedissonClient redissonClient3;

    private RLock lock;

    {
        Config config1 = new Config();
        config1.useSingleServer()
                .setAddress("redis://" + "192.168.137.3:6379")
                .setPassword("123456");
        redissonClient1 = Redisson.create(config1);

        Config config2 = new Config();
        config2.useSingleServer()
                .setAddress("redis://" + "192.168.137.3:6380")
                .setPassword("123456");
        redissonClient2 = Redisson.create(config2);

        Config config3 = new Config();
        config3.useSingleServer()
                .setAddress("redis://" + "192.168.137.3:6381")
                .setPassword("123456");
        redissonClient3 = Redisson.create(config3);


        RLock lock1 = redissonClient1.getLock("lock:order:1");
        RLock lock2 = redissonClient2.getLock("lock:order:1");
        RLock lock3 = redissonClient3.getLock("lock:order:1");

        // 任意一个客户端就行
        lock = redissonClient1.getMultiLock(lock1, lock2, lock3);
    }

    @Test
    void method1() throws InterruptedException {
        // 尝试获取锁：获取锁的最大等待时间（期间会重试），锁超时释放时间，时间单位
        // lock.tryLock(1, TimeUnit.SECONDS);
        // lock.tryLock(1, 10, TimeUnit.SECONDS)
        boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);

        if (!isLock) {
            log.error("获取锁失败 .... {}", 1);
            return;
        }
        try {
            log.info("获取锁成功 .... 1");
            method2();
            log.info("开始执行业务 ... 1");
        } finally {
            log.warn("准备释放锁 .... 1");
            lock.unlock();
        }
    }
    void method2() {
        boolean isLock = lock.tryLock();
        if (!isLock) {
            log.error("获取锁失败 .... 2");
            return;
        }
        try {
            log.info("获取锁成功 .... 2");
            log.info("开始执行业务 ... 2");
        } finally {
            log.warn("准备释放锁 .... 2");
            lock.unlock();
        }
    }
}
