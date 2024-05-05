package com.sword.order.controller;

import com.sword.feign.client.UserClient;
import com.sword.feign.po.User;
import com.sword.order.po.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    UserClient userClient;

    // 打印 header中的token
    @GetMapping
    public Order test(@RequestHeader(value = "token", required = false) String token){
        log.info(token);
        // 使用feignclient
        User user = userClient.test();

        Order order = new Order();
        order.setUser(user);
        return order;
    }
}
