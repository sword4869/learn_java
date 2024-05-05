package com.sword.order.controller;

import com.sword.order.client.UserClient;
import com.sword.order.po.Order;
import com.sword.order.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    UserClient userClient;

    @GetMapping
    public Order test(){
        // 使用feignclient
        User user = userClient.test();

        Order order = new Order();
        order.setUser(user);
        return order;
    }
}
