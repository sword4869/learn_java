package com.sword.feign.client;

import com.sword.feign.config.FeignConfig;
import com.sword.feign.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "userservice", configuration = FeignConfig.class)     // 指定要调用的服务名
public interface UserClient {
    @GetMapping("/user")        // 路径
    public User test();         // 参数和返回值要和对应的controller中的方法一样。但方法名随便，毕竟服务名和路径已经确定。
}