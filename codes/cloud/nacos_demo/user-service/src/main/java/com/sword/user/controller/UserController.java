package com.sword.user.controller;

import com.sword.user.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public User test() {
        System.out.println(System.currentTimeMillis());
        return new User(1L, "Jack", "SC");
    }
}
