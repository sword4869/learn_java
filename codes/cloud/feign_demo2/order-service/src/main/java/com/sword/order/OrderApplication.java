package com.sword.order;

import com.sword.feign.client.UserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// 解决不同模块下的包扫描问题
// 全部：@EnableFeignClients(basePackages = "cn.sword.feign.client")
// 指定：@EnableFeignClients(clients = {UserClient.class})
@EnableFeignClients(clients = {UserClient.class})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}