package com.sword.resultmap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sword.resultmap.mapper")
public class ResultmapApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResultmapApplication.class, args);
    }

}
