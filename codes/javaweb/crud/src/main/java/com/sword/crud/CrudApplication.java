package com.sword.crud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sword.crud.mapper")
@SpringBootApplication
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

}
