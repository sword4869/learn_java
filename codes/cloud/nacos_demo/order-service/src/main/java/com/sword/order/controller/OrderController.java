package com.sword.order.controller;

import com.sword.order.po.Order;
import com.sword.order.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public Order test(){
        String url = "http://userservice/user";
        User user = restTemplate.getForObject(url, User.class);
        Order order = new Order();
        order.setUser(user);
        return order;
    }
}
