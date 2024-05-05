package com.sword.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user2")
@RefreshScope
public class UserController2 {
    @Value("${test.name:hh404}")
    String name;

    @GetMapping
    public String test() {
        System.out.println(System.currentTimeMillis());
        return name;
    }
}
