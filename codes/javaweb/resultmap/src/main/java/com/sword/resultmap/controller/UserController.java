package com.sword.resultmap.controller;


import com.sword.resultmap.domain.dto.*;
import com.sword.resultmap.domain.po.User;
import com.sword.resultmap.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-05-30
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("as")
    public List<UserCardAssociation> getUsersCardAssociation(){
        return userService.getUsersCardAssociation();
    }

    @GetMapping("co")
    public List<UserCardCollection> getUsersCardCollection(){
        return userService.getUsersCardCollection();
    }

    @GetMapping("cards")
    public List<CardDis> getCardDiscriminator(){
        return userService.getCardDiscriminator();
    }
}
