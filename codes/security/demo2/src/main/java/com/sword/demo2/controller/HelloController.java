package com.sword.demo2.controller;


import com.sword.demo2.domain.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

    @RequestMapping("hello")
    @PreAuthorize("hasAuthority('system:menu:list')")
    public String hello(){
        return "hello world";
    }

    @PostMapping("testCors")
    public ResponseResult test(){
        System.out.println("testCors");
        return new ResponseResult(200,"testCors");
    }
}
