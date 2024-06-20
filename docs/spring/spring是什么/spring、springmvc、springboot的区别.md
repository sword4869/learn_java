# spring、springmvc、springboot的区别

spring 是一个容器开发框架，主要特性有 控制反转 IoC 和面向切面 AOP。

springmvc是 spring框架中一个mvc框架，用于web开发，主要包括后端逻辑开发和前端视图开发。

springboot是基于spring扩展的框架，更加专注于后端开发，简化了spring的繁琐流程。

​	约定大于配置。

​	即开即用的start集成。

# 约定大于配置

简化spring 繁琐的开发配置。

​	约定好默认的配置来使用。

​	只需要配置特殊的部分。

# 有哪些约定

maven的目录格式：

​	src-main-java 存放代码

​	src-main-resources 存放资源文件

​	target 存放编辑生成的字节码文件

配置文件及其格式：application.yml或application.properties

