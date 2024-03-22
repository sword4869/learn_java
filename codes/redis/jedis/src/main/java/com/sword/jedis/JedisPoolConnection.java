package com.sword.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolConnection {

    private static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(8);     // 连接池最大连接数，设为-1表示不限制
        jedisPoolConfig.setMaxIdle(8);      // 最大空闲连接数，一开始初始化连接池时会创建这么多连接，让服务启动时就有连接可用。空闲连接超时后会被回收
        jedisPoolConfig.setMinIdle(0);      // 最小空闲连接数
        jedisPoolConfig.setMaxWaitMillis(1000); // 获取连接的最大等待时间，超过这个时间会抛出异常

        // 创建连接池
        // config, host, port, timeout, password
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.101.65", 6379, 1000, "redis");
    }

    public static Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    public static void main(String[] args) {
        Jedis jedis = getJedis();

        String result = jedis.set("name", "Jack");
        System.out.println(result); // OK
        String name = jedis.get("name");
        System.out.println(name);   // Jack

        jedis.close();
        jedisPool.close();
    }
}
