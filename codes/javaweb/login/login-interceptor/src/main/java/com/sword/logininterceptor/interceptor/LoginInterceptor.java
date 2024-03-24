package com.sword.logininterceptor.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sword.logininterceptor.dto.Result;
import com.sword.logininterceptor.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求url。
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURI().toString();

        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if(url.contains("login")){
            return true;
        }

        //3.获取请求头中的令牌（token）。
        String jwt = req.getHeader("token");

        ObjectMapper objectMapper = new ObjectMapper();
        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if(!StringUtils.hasLength(jwt)) {
            Result result = Result.error("NOT_LOGIN");
            String json = objectMapper.writeValueAsString(result);
            res.getWriter().write(json);
            return false;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）。
        try{
            Claims claims = JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            Result result = Result.error("NOT_LOGIN");
            String json = objectMapper.writeValueAsString(result);
            res.getWriter().write(json);
            return false;
        }

        //6.放行。
        return true;
    }
}
