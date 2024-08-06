```xml
<!-- 排除 tomcat -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>					
        </exclusion>
    </exclusions>
</dependency>
<!-- undertow -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-undertow</artifactId>
</dependency>
```

[在 Spring Boot 中使用 Undertow 作为嵌入式服务器 - spring 中文网 (springdoc.cn)](https://springdoc.cn/spring-boot-undertow/)