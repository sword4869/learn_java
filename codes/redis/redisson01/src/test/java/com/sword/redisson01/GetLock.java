package com.sword.redisson01;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
public class GetLock {
    @Resource
    private RedissonClient redissonClient;

    @Test
    void method1() throws InterruptedException {
        RLock lock = redissonClient.getLock("lock:order:1");
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
        RLock lock = redissonClient.getLock("lock:order:1");
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