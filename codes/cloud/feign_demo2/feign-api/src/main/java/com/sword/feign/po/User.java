package com.sword.feign.po;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String address;
}