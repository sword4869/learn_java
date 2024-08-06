## 介绍

Lombok会在**编译**时，会自动生成对应的java代码

| **注解**            | **作用**                                                     |
| ------------------- | ------------------------------------------------------------ |
| `@Getter/@Setter`     | 为所有的属性提供get/set方法                                  |
| `@ToString`           | 会给类自动生成易阅读的  toString 方法                        |
| `@EqualsAndHashCode`  | 根据类所拥有的非静态字段自动重写 equals 方法和  hashCode 方法 |
| `@Data`               | 提供了更综合的生成代码功能（@Getter  + @Setter + @ToString + @EqualsAndHashCode） |
| `@NoArgsConstructor`  | 为实体类生成无参的构造器方法                                 |
| `@AllArgsConstructor` | 为实体类生成除了static修饰的字段之外带有各参数的构造器方法。 |
|`@RequiredArgsConstructor`| 注入方式|
|`@Builder`|基于变种的建造者模式的注解|
|`@Log4j` |注解在类上；为类提供一个 属性名为log 的 log4j 日志对像|
|`@Slf4j`| 同上|

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

~~~java
import lombok.Data;

@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor//全参构造
public class User {
    private String name;
    private Short age;
}
~~~

## @Getter @Setter

自己写了，lombok就不会帮我们生成了（不会冲突我们的自定义规则）

```java
public void test(){
    Dog dog = new Dog();
    dog.setName("dog");
    System.out.println(dog.getName());		// fixed
}

@Getter
@Setter
static class Dog{
    private String name;
    public String getName(){
        return "fixed";
    }
}
```

## @EqualsAndHashCode
`@EqualsAndHashCode` 默认选项 `(callSuper=false)`：

比较两个对象时：
1.  只是想在当前类比较字段，使用`@EqualsAndHashCode(callSuper=false)`。
2.  需要考虑**父类**和本类中的成员，使用`@EqualsAndHashCode(callSuper=true)`
3.  如果全部要比较 或 全部不需要比较 父类成员，使用全局配置 lombok.config

## @ToString

  ```java
  @ToString						// 默认false, 只打印子类自身的属性。
  @ToString(callSuper = true)		// 默认false，callSuper = true表示在toString方法中包含父类的toString方法，不然只会打印子类的属性
  ```

```java
public class TestCase {
    @Test
    public void test(){
        Dog1 dog1 = new Dog1();
        System.out.println(dog1);

        Dog2 dog2 = new Dog2();
        System.out.println(dog2);
    }
    @Data
    static class Animal{
        private String name = "animal";
    }
    @Data
    static class Dog1{
        private Integer age = 1;
    }

    @Data
    static class Dog2{
        private String name = "dog";		// 重新声名，这样就是子类自身的了 
        private Integer age = 2;
    }
    
    // TestCase.Dog1(age=1)
    // TestCase.Dog2(name=dog, age=2)
}
```



## @AllArgsConstructor

```java
@AllArgsConstructor(staticName = "of")      // return new User(...)

User user = User.of("Tom", 123);    
```
## @RequiredArgsConstructor

自动注入的原理是基于Spring构造函数注入，像这样：

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112201611.png)

但是，如果需要注入的属性较多，构造函数就会非常臃肿，代码写起来也比较麻烦。

```java
@RequiredArgsConstructor
// 将类的每一个final字段或者non-null字段生成一个构造方法
public class UserController {
    // 需要变成final字段
    private final IUserService userService;
}
```

简化了对多个注入的@Autowired书写。我们在写Controller层或者Service层的时候，总是需要注入很多mapper接口或者service接口，如果每个接口都写上@Autowired，这样看起来就会很繁琐，@RequiredArgsConstructor注解可以代替@Autowired注解

## @Accessors

用来控制getter/setter访问行为的，Accessors有三个属性：默认`Accessors(fluent = false, chain = false, prefix = {})`
- `fluent`: 去除 get/set。为true，直接`user.name()`
- `chain`: 链式编程 set。为true，`new User().setName("Tom").setAge(12);`
- `prefix`：过滤字段前缀。`xxName`, `prefix = {"xx"}` → `getName(), setName()`

一般就常用 `@Accessors(chain = true)`

https://blog.csdn.net/sunnyzyq/article/details/119992746

## @Builder

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112201612.png)

## @Slf4j

【方式1】

```java
// 在类上加注释
@Slf4j
@SpringBootTest
public class RedissonTest {
    @Test
    void method1() throws InterruptedException {
        log.error("获取锁成功 .... ");
        log.error("获取锁失败 .... {}", 1);
    }
// 2024-04-22 15:57:43.305  INFO 18768 --- [           main] com.sword.redisson01.RedissonTest        : 获取锁成功 .... 
// 2024-04-22 15:57:43.307  INFO 18768 --- [           main] com.sword.redisson01.RedissonTest        : 获取锁失败 .... 1
```
`log.error/warn/debug/info()`

【方式2】

```java
public class InstitutionBasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstitutionBasicController.class);

	@GetMapping(WebURIMappingConstant.REQUEST_MAPPING_INSTITUTION_BASIC_TEACHER)
    public Rest<JsonPagedVO<TeacherInfoVO>> getTeacher(@Validated TeacherInfoCriteria teacherInfoCriteria) {
        LOGGER.info("获取教师信息 {}", teacherInfoCriteria);
        JsonPagedVO<TeacherInfoVO> teacherList = teacherService.getTeacherList(teacherInfoCriteria);
        return RestBody.okData(teacherList);
    }
}
```



