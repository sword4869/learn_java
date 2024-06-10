package com.sword.demo2.service;

import com.sword.demo2.domain.ResponseResult;
import com.sword.demo2.domain.User;

public interface LoginServcie {
    ResponseResult login(User user);

    ResponseResult logout();
}
