package com.sword.logininterceptor.service;

import com.sword.logininterceptor.po.User;

public interface UserService {
    User matchUsernameAndPassword(User user);
}
