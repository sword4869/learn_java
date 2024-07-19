## JSR303 validation

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112159244.png)

### 使用

第一步：接收参数类型的dto类的属性上，定义校验项。

```java
public class AddCourseDto{
    @NotEmpty(message = "课程名称不能为空")
    private String name;

    @Size(message = "内存太少", min = 10)
    private String description;

    @Min(message = "页码不能小于1", value = 1)
    private Integer pageNo;
}
```
第二步：controller中 `@Validated` 来开启校验。

```java
@RestController
public class CourseBaseInfoController{
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated AddCourseDto addCourseDto){

    }
}
```

### 分组校验

新增课程和修改课程，对同一个字段要求不一样。

```java
public class ValidationGroups{
    public interface Insert{};
    public interface Update{};
    public interface Delete{};
}
```
dto中添加组属性 `groups`
```java
public class AddCourseDto{
    @NotEmpty(message = "新增课程名称不能为空", groups = {ValidationGroups.Insert.class})
    @NotEmpty(message = "修改课程名称不能为空", groups = {ValidationGroups.Update.class})
    private String name;
}
```
controller中指定使用某个组 `@Validated(组)`
```java
@RestController
public class CourseBaseInfoController{
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Insert.class) AddCourseDto addCourseDto){

    }

    public CourseBaseInfoDto updateCourseBase(@RequestBody @Validated(ValidationGroups.Update.class) AddCourseDto addCourseDto){

    }
}
```
### 配合全局异常处理器

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112159245.png)

### 校验规则不满足?  

1. 手写校验代码。
2. 自定义校验注解。

