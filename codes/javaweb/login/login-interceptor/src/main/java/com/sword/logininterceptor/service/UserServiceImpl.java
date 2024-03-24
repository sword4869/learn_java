package com.sword.logininterceptor.service;

import com.sword.logininterceptor.po.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User matchUsernameAndPassword(User user) {
        if("admin".equals(user.getUsername()) && "admin".equals(user.getPassword())){
            return user;
        }
        return null;
    }
}
