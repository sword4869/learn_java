package com.sword.logininterceptor.controller;


import com.sword.logininterceptor.dto.Result;
import com.sword.logininterceptor.po.User;
import com.sword.logininterceptor.service.UserService;
import com.sword.logininterceptor.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User u = userService.matchUsernameAndPassword(user);

        // 匹配成功，生成令牌,下发令牌
        if (u != null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("username", u.getUsername());

            String jwt = JwtUtils.generateJwt(claims); //jwt包含了当前登录的员工信息
            return Result.success(jwt);
        }
        return Result.error("用户名或密码错误");
    }

    @GetMapping("/")
    public Result other(){
        return Result.success();
    }
}
