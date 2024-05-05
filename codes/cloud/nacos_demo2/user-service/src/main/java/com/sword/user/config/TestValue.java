package com.sword.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "test")
@Component
public class TestValue {
    // 整个流程的
    String name;

    // 阶段：bootstrap, nacos, local(bootstrap+vm+application)
    String nameBootstrap;
    String nameNacos;
    String nameLocal;

    // 单个配置是否被激活
    Boolean bootstrap;
    Boolean bootstrapDev;
    Boolean bootstrapTest;
    Boolean nacos;
    Boolean nacosDev;
    Boolean nacosTest;
    Boolean nacosShared;
    Boolean nacosExtension;
    Boolean application;
    Boolean vm;
}
