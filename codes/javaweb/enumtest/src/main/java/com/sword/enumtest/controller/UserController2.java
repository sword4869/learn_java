package com.sword.enumtest.controller;


import cn.hutool.core.bean.BeanUtil;
import com.sword.enumtest.*;
import com.sword.enumtest.service.IUserService1;
import com.sword.enumtest.service.IUserService2;
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
@RequestMapping("/user2")
@RequiredArgsConstructor
public class UserController2 {
    private final IUserService2 userService;

    @ApiOperation("方案 2")
    @GetMapping("{id}")
    public User2 queryUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        User2 user = userService.getById(id);
        user.setStatus(UserStatus2.NORMAL);
        return user;
    }

    @ApiOperation("方案 2 - VO")
    @GetMapping("vo/{id}")
    public UserVO2 queryUserById2(@ApiParam("用户id") @PathVariable("id") Long id){
        User2 user = userService.getById(id);
        user.setStatus(UserStatus2.NORMAL);
        return BeanUtil.copyProperties(user, UserVO2.class);
    }
}
