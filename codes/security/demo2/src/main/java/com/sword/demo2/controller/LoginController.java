package com.sword.demo2.controller;

import com.sword.demo2.domain.ResponseResult;
import com.sword.demo2.domain.User;
import com.sword.demo2.service.LoginServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginServcie loginServcie;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        System.out.println("开始登录");
        return loginServcie.login(user);
    }
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        System.out.println("开始登出");
        return loginServcie.logout();
    }
}