[SpringBoot中 @Bean 和 @Component的联系区别_springboot bean 名称 componet-CSDN博客](https://blog.csdn.net/weixin_42366358/article/details/105133128)



`@Componet`在类上

​	它的子类型（@Controller, @Service and @Repository）

`@Bean + @Configuration` 在配置类的方法上



## Component

```java
@Component	// 在类上
public class MessageBuilder {
     public String getMsg(){
         return "msgBuilder";
     }
}
```

```java
@Controller
@RequestMapping("/web")
public class WebController {
    @Autowired		// 自动注入
    private MessageBuilder messageBuilder;

    @ResponseBody
    @RequestMapping("/msg")
    public String message(){
        return messageBuilder.getMsg();
    }
}
```



## Bean+Configuration

```java
public class MessageBuilder {
     public String getMsg(){
         return "msgBuilder";
     }
}
```

```java
// Bean + Configuration
@Configuration
public class AppConfig {
    @Bean
    public MessageBuilder messageBuilder(){		// 方法名就是bean name
        return new MessageBuilder();		// 方法返回一个对象
    }
}

```

```java
@Controller
@RequestMapping("/web")
public class WebController {
    @Autowired		// 自动注入
    private MessageBuilder messageBuilder;

    @ResponseBody
    @RequestMapping("/msg")
    public String message(){
        return messageBuilder.getMsg();
    }
}
```

```java
```

