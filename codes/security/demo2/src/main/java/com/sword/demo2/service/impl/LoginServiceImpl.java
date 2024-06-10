package com.sword.demo2.service.impl;

import com.sword.demo2.domain.LoginUser;
import com.sword.demo2.domain.ResponseResult;
import com.sword.demo2.domain.User;
import com.sword.demo2.service.LoginServcie;
import com.sword.demo2.utils.JwtUtil;
import com.sword.demo2.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class LoginServiceImpl implements LoginServcie {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        //1.封装Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        //2.通过AuthenticationManager的authenticate方法来进行用户认证
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);

        //3.在Authentication中获取用户信息
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //4.认证通过生成token
        String jwt = JwtUtil.createJWT(userId);
        //5.用户信息存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);
        //6.把token返回给前端
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("token", jwt);
        return new ResponseResult(200, "登录成功", hashMap);
    }

    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("login:" + userId);
        return new ResponseResult(200, "退出成功");
    }
}
