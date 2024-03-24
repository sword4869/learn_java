package com.sword.logininterceptor.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // 查数据库需要id
    private Integer id;
    // 登录需要用户名和密码
    private String username;
    private String password;
}
