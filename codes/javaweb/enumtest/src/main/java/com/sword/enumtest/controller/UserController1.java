package com.sword.enumtest.controller;


import cn.hutool.core.bean.BeanUtil;
import com.sword.enumtest.User1;
import com.sword.enumtest.UserVO1;
import com.sword.enumtest.service.IUserService1;
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
@RequestMapping("/user1")
@RequiredArgsConstructor
public class UserController1 {
    private final IUserService1 userService;

    @ApiOperation("方案 1")
    @GetMapping("{id}")
    public User1 queryUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        User1 user = userService.getById(id);
        user.setStatus(2);
        return user;
    }

    @ApiOperation("方案 1 - VO")
    @GetMapping("vo/{id}")
    public UserVO1 queryUserById2(@ApiParam("用户id") @PathVariable("id") Long id){
        User1 user = userService.getById(id);
        user.setStatus(2);
        return BeanUtil.copyProperties(user, UserVO1.class);
    }
}
