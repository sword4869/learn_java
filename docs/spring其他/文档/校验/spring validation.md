## pom

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

不要引用 hibernate 的。

'org.hibernate.validator.constraints.NotEmpty' is deprecated

## 注解



```java
空检查
@Null			验证对象是否为null
@NotNull		验证对象是否不为null, 无法查检长度为0的字符串
@NotEmpty		集合和字符串，检查非null非空. 
@NotBlank		只对字符串，检查非null或Trim后非空


Booelan检查
@AssertTrue		验证 Boolean 对象是否为 true  
@AssertFalse	验证 Boolean 对象是否为 false  

长度检查
@Size(min=, max=)		验证对象（Array,Collection,Map,String）长度是否在给定的范围之内  
@Length(min=, max=)		只对字符串，验证字符串长度在min和max区间内。@Length(min=1)只是检查非空，但可为null，并不等同于NotEm

日期检查
@Past					   验证 Date 和 Calendar 对象是否在当前时间之前  
@PastOrPresent（时间）      过去或者现在  
@Future					   验证 Date 和 Calendar 对象是否在当前时间之后  
@FutureOrPresent（时间） 	将来或者现在 

    
数值检查
建议使用在Stirng,Integer类型，不建议使用在int类型上，因为表单值为“”时无法转换为int，但可以转换为Stirng为"",Integer为null
Double也行。
只有`@Min(1)`，是可以为空。
@Min(10)			验证 Number 和 String 对象是否大等于指定的值  
@Max(100)			验证 Number 和 String 对象是否小等于指定的值  
@Positive               数字，正数    
@PositiveOrZero         数字，正数或0 
@Negative               数字，负数    
@NegativeOrZero         数字，负数或0  
@DecimalMax		被标注的值必须不大于约束中指定的最大值. 这个约束的参数是一个通过BigDecimal定义的最大值的字符串表示.小数存在精度
@DecimalMin		被标注的值必须不小于约束中指定的最小值. 这个约束的参数是一个通过BigDecimal定义的最小值的字符串表示.小数存在精度
@Digits			验证 Number 和 String 的构成是否合法  
@Digits(integer=,fraction=)		验证字符串是否是符合指定格式的数字，interger指定整数精度，fraction指定小数精度。
@Range(min=, max=)	验证注解的元素值在最小值和最大值之间


@CreditCardNumber 信用卡验证
@Email  验证是否是邮件地址，如果为null,不进行验证，算通过验证。
@ScriptAssert(lang= ,script=, alias=)
@URL(protocol=,host=, port=,regexp=, flags=)
@Pattern(regexp=)	验证 String 对象是否符合正则表达式的规则
```

​     |

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112159244.png)

## 基本使用

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

## 配合全局异常处理器

配合全局捕获，对应抛出的异常类型是 `MethodArgumentNotValidException`或者`ConstraintViolationException`异常

validationer校验。继承抽象service

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112159245.png)

### 校验规则不满足?  

1. 手写校验代码。
2. 自定义校验注解。

### `@valid`和`@Validated`

|     区别     |                            @Valid                            |                 @Validated                 |
| :----------: | :----------------------------------------------------------: | :----------------------------------------: |
|    提供者    |                         JSR-303规范                          |             Spring JSR-303规范             |
| 是否支持分组 |                            不支持                            |                  **支持**                  |
|   嵌套校验   |                           **支持**                           |                   不支持                   |
|   标注位置   | METHOD 方法, PARAMETER 方法参数, **FIELD** 成员属性, CONSTRUCTOR, TYPE_USE | METHOD 方法, PARAMETER 方法参数, TYPE 类型 |

两者都可以加在 方法和方法参数上。

两者是否能用于成员属性（字段）上直接影响能否提供嵌套验证的功能。

## 字符串

```java
@NotBlank  			// 推荐字符串用NotBlank，而不是NotEmpty
@Length(max = 50) 	// 限定字符串长度。比实际短些，防止特殊字符，varchar(60)
private String name;


/**
 * 指定位数的数字
 * integer为小数点前的位数
 * fraction为小数点后的位数
 */
@Digits(integer = 3, fraction = 2)
private String eee;


/**
 * 一些特殊的字符串
 */
@Email //邮件地址格式
@Pattern(regexp = "^1[3-9][0-9]{9}$") //正则表达式,手机号
@Pattern(regexp = "^[1-9][0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|(1|2)[0-9]|30|31)$") //正则表达式,标准日期
@Pattern(regexp = "^[1-9][0-9]{5}(19|20)[0-9]{2}(0[1-9]|1[0-2])-(0[1-9]|(1|2)[0-9]|30|31)[0-9]{3}[0-9Xx]$") //正则表达式,身份证号
private String fff;
```

一定要加上最长长度限制。不然数据库疯狂报错。



## 分组校验 @Validated

### 分组

新增课程和修改课程，对同一个字段要求不一样。

```java
public class ValidationGroups{
    public interface Insert{};
    public interface Update{};
    public interface Delete{};
}
```
注解中添加组属性 `groups`
```java
public class AddCourseDto{
    @NotEmpty(message = "新增课程名称不能为空", groups = {ValidationGroups.Insert.class})
    @NotEmpty(message = "修改课程名称不能为空", groups = {ValidationGroups.Update.class})
    private String name;
    
    @NotEmpty(groups = { ValidationGroups.Insert.class })  
    @Size(min = 3, max = 8, groups = { ValidationGroups.Update.class })  
    private List<Emp> emps; 
}
```
controller中指定使用某个组 `@Validated(组)`，只能使用`@Validated`。
```java
@RestController
public class CourseBaseInfoController{
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Insert.class) AddCourseDto addCourseDto){
    }

    public CourseBaseInfoDto updateCourseBase(@RequestBody @Validated(ValidationGroups.Update.class) AddCourseDto addCourseDto){
    }
}
```

### 默认分组

没有指定分组，归属默认分组 `Default.class`（`import jakarta.validation.groups.Default;`）

默认分组和指定分组，井水不犯河水。

```
// VO: 两种方式等同
@Size(message = "课程名称不能为空", min=1)
@Size(message = "课程名称不能为空", min=1, groups = {Default.class})
    
// 默认分组校验
public CourseDto defaultCourseBase(@RequestBody @Validated CourseDto courseDto){
```



### 分组序列 @GroupSequence

用途：

（1）依赖：第二个组中的约束验证依赖于一个稳定状态来运行，而这个稳定状态是由第一个组来进行验证的。

（2）耗时：某个组的验证比较耗时，CPU 和内存的使用率相对比较大，最优的选择是将其放在最后进行验证。

```java
public class ValidationGroups{
    public interface GroupA{};
    public interface GroupB{};
    
    @GroupSequence( { GroupA.class, GroupB.class })
    public interface Group{};
}
```

```java
@RestController
public class CourseBaseInfoController{
    public CourseBaseInfoDto updateCourseBase(@RequestBody @Validated(ValidationGroups.Group.class) AddCourseDto addCourseDto){
    }
}
```



## 嵌套验证 @Valid

### 基本

`@Valid` 递归的对关联对象进行校验

​	如果关联对象是个集合或者数组,那么对其中的元素进行递归校验

​	如果是一个map,则对其中的值部分进行校验

想要嵌套校验 `props` 中的每个元素 `Prop`，在 `props` 属性上只能加 `@Valid`.



```java
public class Item {
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id必须为正整数")
    private Long id;

    @Valid 			// 嵌套验证必须用@Valid
    @NotNull(message = "props不能为空")
    @Size(min = 1, message = "至少要有一个属性")
    private List<Prop> props;
}
```

```java
public class Prop {
    @NotNull(message = "pid不能为空")
    @Min(value = 1, message = "pid必须为正整数")
    private Long pid;

    @NotNull(message = "vid不能为空")
    @Min(value = 1, message = "vid必须为正整数")
    private Long vid;

    @NotBlank(message = "pidName不能为空")
    private String pidName;

    @NotBlank(message = "vidName不能为空")
    private String vidName;
}
```

然后我们在ItemController的addItem函数上再使用`@Validated`或者`@Valid`，就能对Item的入参进行嵌套验证。

### 怎样校验 List

`@Valid`是JSR-303注解，JSR-303适用于JavaBeans的验证。但是`java.util.List`不是JavaBean，因此不能使用兼容JSR-303的验证器直接对其进行验证。

目标情况，无法嵌套校验

```java
@PostMapping
public String doIt(@RequestBody @Valid List<User> users) {
    return "SUCCESS";
}
```

[解决@Valid List 无法校验的问题-CSDN博客](https://blog.csdn.net/wangjiangongchn/article/details/86481729)



## 自定义注解

## 使用 `Validator` 校验：
