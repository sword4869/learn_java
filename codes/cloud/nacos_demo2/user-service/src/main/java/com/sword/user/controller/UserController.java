package com.sword.user.controller;

import com.sword.user.config.TestValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    TestValue testValue;

    @GetMapping
    public TestValue test() {
        System.out.println(System.currentTimeMillis());
        return testValue;
    }
}
