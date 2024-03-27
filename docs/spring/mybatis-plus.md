## 1. Mybatis导入

- 引入起步依赖
- 在application.yml中根据需要添加配置
- 在实体类上添加注解声明 表信息
- 自定义Mapper，继承基础BaseMapper
- 自定义Service接口，继承IService接口；自定义Service实现类，继承ServiceImpl类并实现自定义接口。


```xml
<!-- mybatis起步依赖 -->
<!-- <dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.3.0</version>
</dependency> -->

<!-- mybatis-plus包含对mybatis的自动装配，因此完全可以替换掉Mybatis -->
<dependency>
    <groupId>com.baomidou</groupId>
    <!--下面坐标根据自己使用的SpringBoot版本二选一-->
    <!--SpringBoot2使用此版本-->
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <!--3.5.4开始,支持SpringBoot3使用此版本-->
    <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
    <version>3.5.3.1</version>
</dependency>

<!-- mysql驱动包依赖 -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

```java
public interface UserMapper extends BaseMapper<User> {}
```
```java
public interface IUserService extends IService<User>{}
```
```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {}
```
在启动上加`@MapperScan("com.sword.crud.mapper")`。所以在mapper接口上就不用标注`@Mapper`

## PO

- MybatisPlus会把PO实体的类名驼峰转下划线作为表名
- MybatisPlus会把PO实体的所有变量名驼峰转下划线作为表的字段名，并根据变量类型推断字段类型
- MybatisPlus会把名为id的字段作为主键(变量名和数据库字段都得是`id`)

```java
public class User {
    private Long id;  // 数据库不自增时
    private String name;
    private Integer age;
    @TableField("isMarried")
    private Boolean isMarried;
    @TableField("concat")
    private String concat;
}
```
- 表名不一致 `@TableName`
- 主键名不一致:`@TableId`
  - 可以set指定id，不set则自己生成。生成方案，如果是数据库设置auto必须写，否则默认是雪花算法。
- 字段名：@TableField
  - 不一致；is被过滤；关键字冲突要转义``` `xxx` ```；
  - 非数据库字段；自动填充


## 配置

常用
```yml
spring:
  datasource:
    # 驱动类名称
    driver-class-name: com.mysql.cj.jdbc.Driver
    # 数据库连接的url
    url: jdbc:mysql://localhost:3306/tlias
    # 连接数据库的用户名
    username: root
    # 连接数据库的密码
    password: 1234

# 其实可以啥都不写
mybatis-plus:
  type-aliases-package: com.itheima.mp.domain.po  # 用于mapper.xml中resultType直接写类名，也可以不配，毕竟namespace要写全包名，resultType也不差这几个字
  global-config:
    db-config:
      id-type: auto # 全局id类型为自增长，也可以不写，MybatisPlus插件生成的就已经在每个字段上写了。
```
大多数的配置都有默认值
```yml
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml # 默认值，放在resouces/mappers/xxx.xml位置
  configuration:
    map-underscore-to-camel-case: true  #  MyBatis 中原默认值为 false，  MyBatis-Plus 中原默认值为 true
  global-config:
    db-config:
      id-type: assign_id  # 全局默认主键类型：雪花算法生成id
      update-strategy: not_null # update传入po实体，只更新其非null的字段
```


## lambda

lambdaQuery 查，lambdaUpdate 更新

condition可选条件。

one/list/page/count/exists



---

## hutool

```java
// 方式1
CourseBase courseBaseNew = new CourseBase();
BeanUtils.copyProperties(dto, courseBaseNew);


// 方式2
CourseBase courseBaseNew2 = BeanUtils.copyProperties(dto, CourseBase.class);
```
`BeanUtils.copyProperties()`，是根据属性名字来匹配的，如果名字不一样的话，那么就得手动get/set。所以设计dto和po之间时，要注意名字。

```java
List<User> users = userService.listByIds(ids);
List<UserVO> userVOs = BeanUtil.copyToList(users, UserVO.class);
```

