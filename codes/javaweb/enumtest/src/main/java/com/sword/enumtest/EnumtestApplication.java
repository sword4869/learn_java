package com.sword.enumtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@MapperScan("com.sword.enumtest.mapper")
@SpringBootApplication
public class EnumtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnumtestApplication.class, args);
    }
}
