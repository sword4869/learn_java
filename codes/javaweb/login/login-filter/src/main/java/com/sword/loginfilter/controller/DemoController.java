package com.sword.loginfilter.controller;

import com.sword.loginfilter.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/login")
    public Result login(){
        return Result.success();
    }

    @GetMapping("/")
    public Result other(){
        return Result.success();
    }
}
