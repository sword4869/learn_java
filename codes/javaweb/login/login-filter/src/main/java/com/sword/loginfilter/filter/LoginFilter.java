package com.sword.loginfilter.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sword.loginfilter.dto.Result;
import com.sword.loginfilter.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //1.获取请求url。
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String url = req.getRequestURI().toString();

        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if(url.contains("login")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //3.获取请求头中的令牌（token）。
        String jwt = req.getHeader("token");

        ObjectMapper objectMapper = new ObjectMapper();
        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if(!StringUtils.hasLength(jwt)) {
            Result result = Result.error("NOT_LOGIN");
            String json = objectMapper.writeValueAsString(result);
            res.getWriter().write(json);
            return;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）。
        try{
            Claims claims = JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            Result result = Result.error("NOT_LOGIN");
            String json = objectMapper.writeValueAsString(result);
            res.getWriter().write(json);
            return;
        }

        //6.放行。
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
