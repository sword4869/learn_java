package com.sword.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;

class JedisDirectConnection {
    public static void main(String[] args) {
        // 1.建立连接 host port timeout
        Jedis jedis = new Jedis("192.168.101.65", 6379, 3000);
        // 2.设置密码
        jedis.auth("redis");
        // 3.选择库
        jedis.select(13);


        String result = jedis.set("name", "Jack");
        System.out.println(result); // OK
        String name = jedis.get("name");
        System.out.println(name);   // Jack

        Long result2 = jedis.rpush("characters", "A", "B");
        System.out.println(result2); // 2
        List<String> characters = jedis.lrange("characters", 0, -1);
        System.out.println(characters); // [A, B]

        if (jedis != null) {
            jedis.close();
        }
    }
}
