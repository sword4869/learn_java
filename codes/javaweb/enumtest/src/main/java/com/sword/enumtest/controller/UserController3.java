package com.sword.enumtest.controller;


import cn.hutool.core.bean.BeanUtil;
import com.sword.enumtest.*;
import com.sword.enumtest.service.IUserService1;
import com.sword.enumtest.service.IUserService3;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-03-27
 */
@RestController
@RequestMapping("/user3")
@RequiredArgsConstructor
public class UserController3 {
    private final IUserService3 userService;

    @ApiOperation("方案 3")
    @GetMapping("{id}")
    public User3 queryUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        User3 user = userService.getById(id);
        user.setStatus(UserStatus3.NORMAL);
        return user;
    }


    @ApiOperation("方案 3 - VO")
    @GetMapping("vo/{id}")
    public UserVO3 queryUserById2(@ApiParam("用户id") @PathVariable("id") Long id){
        User3 user = userService.getById(id);
        user.setStatus(UserStatus3.NORMAL);
        return BeanUtil.copyProperties(user, UserVO3.class);
    }
}
