## 依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>

<!-- 另一个排除的?? -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
        </exclusion>
        <exclusion>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

一旦依赖了spring-boot-starter-test，下面这些类库将被一同依赖进去：

- JUnit：java测试事实上的标准，默认依赖版本是4.12
- Spring Test & Spring Boot Test：Spring的测试支持。
- AssertJ：提供了流式的断言方式。
- Hamcrest：提供了丰富的matcher。
- Mockito：mock框架，可以按类型创建mock对象，可以根据方法参数指定特定的响应，也支持对于mock调用过程的断言。
- JSONassert：为JSON提供了断言功能。
- JsonPath：为JSON提供了XPATH功能。

## 使用

```java
@SpringBootTest
public class EmailTest {
    @Autowired
    UserMapper usermapper;

    @Test
    void runner() Exception {
        Thread.sleep(60000);
    }

    @Test
    void runne2(){
		usermapper.findById(1L);
    }
}
```

`@SpringBootTest` + `@Test`

## @SpringBootTest

一、加载ApplicationContext，扫描`@SpringBootApplication`和`@SpringBootConfiguration`的类。

​	（1）加载bean。意思是开启**自动注入**，只要`@Autowired`就需要。

​		主要服务注入`@Autowired UserMapper`、`@Autowired UserService`、`@Autowired UserController`；

​		只是在Test中注入依赖的官方包也需要 `@Autowired JavaMailSender javaMailSender`。

```java
import com.hello.domain.eval.enterprise.entity.Enterprise;
import com.hello.domain.eval.enterprise.repository.EnterpriseMapper;

@SpringBootTest
public class CrudTest {
    @Autowired
    EnterpriseMapper enterpriseMapper;		// 自定义bean
```



```java
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailTest {
    @Autowired
    JavaMailSender javaMailSender;		// 第三方的bean
```



​	（2）配置文件，test中会使用main中的**resources下的配置文件**。

​		如果一样，那么test中就无需创建。

​		如果需要自定义不同的配置，那么test中才创建，而且要写完整的。不能只定义一部属性而想着另一部分不写用main的，这样不行。

​	![image-20240712100603979](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407121006526.png)

## @Test

作用就是让方法上多一个执行按键。

![image-20240712141914894](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407121419576.png)