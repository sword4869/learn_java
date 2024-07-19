## Mybatis导入

```xml
<!-- mybatis起步依赖 -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.3.0</version>
</dependency>

<!-- mysql驱动包依赖 -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

1. application.properties

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
    
    mybatis:
      configuration:
        # 指定mybatis输出日志的位置, 输出控制台
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
        # 开启数据库表字段 到 实体类属性的驼峰映射
        map-underscore-to-camel-case: true
    ```

2. Mapper接口（编写SQL语句）

    ~~~java
    @Mapper
    public interface UserMapper {
        
    }
    ~~~

@Mapper注解：表示是mybatis中的Mapper接口。程序运行时：框架会自动生成接口的实现类对象(代理对象)，并给交Spring的IOC容器管理

## 编写mapper查询

一种方式是注解，一种是XML。

方式一：注解
```java
// Service中
@Override
public List<UserVO> querySelfDefined() {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("a.city", "北京")
            .in("u.id", List.of(1L, 2L, 4L));
    // 传递 wrapper
    List<User> users = userMapper.querySelfDefined(wrapper);
    return BeanUtil.copyToList(users, UserVO.class);
}

// mapper中
@Select("SELECT u.* FROM user u INNER JOIN address a ON u.id = a.user_id ${ew.customSqlSegment}")
List<User> querySelfDefined(@Param("ew") QueryWrapper<User> wrapper); // @Param(Constants.WRAPPER)
```
方式二：xml

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407111409647.png)

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407111409648.png)
```java
// Service中
@Override
public List<UserVO> querySelfDefined() {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("a.city", "北京")
            .in("u.id", List.of(1L, 2L, 4L));
    // 传递 wrapper
    List<User> users = userMapper.querySelfDefined2(wrapper);
    return BeanUtil.copyToList(users, UserVO.class);
}

// mapper中
List<User> querySelfDefined2(@Param("ew") QueryWrapper<User> wrapper); // @Param(Constants.WRAPPER)

// xml中
<select id="querySelfDefined2" resultType="com.sword.crud.domain.po.User">
    SELECT u.* FROM user u INNER JOIN address a ON u.id = a.user_id  ${ew.customSqlSegment}
</select>
```

## 编写mapper查询之注解
@Select、@Delete、@Insert、@Update

```java
@Mapper
public interface UserMapper {
    @Select("select id, name, age, gender, phone from user")
    public List<User> list();

    @Delete("delete from emp where id = #{id}")//使用#{key}方式获取方法中的参数值
    public void delete(Integer id);

    //会自动将生成的主键值，赋值给emp对象的id属性
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time) values (#{username}, #{name}, #{gender}, #{image}, #{job}, #{entrydate}, #{deptId}, #{createTime}, #{updateTime})")
    public void insert(Emp emp);

    @Update("update emp set username=#{username}, name=#{name}, gender=#{gender}, image=#{image}, job=#{job}, entrydate=#{entrydate}, dept_id=#{deptId}, update_time=#{updateTime} where id=#{id}")
    public void update(Emp emp);
}
```

### @Param注解

当名字不一致时, ew是给sql语句的名字。

```java
List<User> querySelfDefined2(@Param("ew") QueryWrapper<User> wrapper); // @Param(Constants.WRAPPER)
```



### 参数占位符

在Mybatis中提供的参数占位符有两种：`${...}` 、`#{...}`。里面的属性名可以随便写，但是建议保持表字段名字一致。

- `${...}` 直接拼接
  - 拼接SQL。直接将参数拼接在SQL语句中，存在SQL注入问题
  - 使用时机：如果对表名、列表进行动态设置时使用

- `#{...}` 预编译SQL
  - 执行SQL时，会将`#{…}`替换为`?`，生成预编译SQL，会自动设置参数值
  - 使用时机：参数传递，都使用#{…}

比如：`like '%${name}%'`👉`like concat('%',#{name},'%')`

预编译SQL有两个优势：

1. 性能更高: 只编译一次，编译后的SQL语句缓存起来，后面再次执行这条语句时，不会再次编译。（只是输入的参数不同）
2. 更安全(防止SQL注入)：不采用字符串拼接，而是将敏感字进行转义

### 主键返回

默认情况下，执行插入操作时，是不会主键值返回的。

如果我们想要拿到主键值，需要在Mapper接口中的方法上添加一个Options注解，并在注解中指定属性`useGeneratedKeys=true`和`keyProperty="实体类属性名"`

### 数据封装
- 实体类属性名和数据库表查询返回的字段名一致，mybatis会自动封装。
- 如果实体类属性名和数据库表查询返回的字段名不一致，不能自动封装。

解决方案：
1. sql语句起别名
2. 结果映射
3. 开启驼峰命名：只限于表中字段名 abc_xyz  => 类中属性名 abcXyz


**起别名**：在SQL语句中，对不一样的列名起别名，**别名和实体类属性名一样**

```java
@Select("select id, username, password, name, gender, image, job, entrydate, " +
        "dept_id AS deptId, create_time AS createTime, update_time AS updateTime " +
        "from emp " +
        "where id=#{id}")
public Emp getById(Integer id);
```


**手动结果映射**：通过 @Results及@Result（column指定表中字段名，property指定类中属性名）进行手动结果映射。

```java
@Results({@Result(column = "dept_id", property = "deptId"),
          @Result(column = "create_time", property = "createTime"),
          @Result(column = "update_time", property = "updateTime")})
@Select("select id, username, password, name, gender, image, job, entrydate, dept_id, create_time, update_time from emp where id=#{id}")
public Emp getById(Integer id);
```
**开启驼峰命名**
```properties
mybatis:
  configuration:
    map-underscore-to-camel-case: true
```
## 编写mapper查询之XML

https://blog.csdn.net/li_w_ch/article/details/109802957

https://www.cnblogs.com/kenhome/p/7764398.html

> 作用

注解是写死的sql语句，而XML解决了动态sql的问题。比如，带条件查询。
```sql
-- 如果姓名输入了"张
select *  from emp where name like '%张%' order by update_time desc;

-- 如果姓名输入了"张",，性别选择了"男"
select *  from emp where name like '%张%' and gender = 1 order by update_time desc;
```

> 写法

`<select>`:

- `id`:同函数名，也是xml中的唯一标签id（别的sql片段引用它）。
- `parameterType`: 函数参数类型
- `resultType`/`resultMap`: 函数返回值类型

`<insert>`、`<update>`、`<delete>`：只需要`id`

| 标签名 | 说明 | 属性 |
| --- | --- | --- |
| **where** | 删除开头额外的AND或OR |  |
| **set** | 删掉额外的逗号 |  |
| **if** | 条件 | test |
| **foreach** | 循环 | collection, item, separator, open, close |
| sql | sql | id |
| include | 引入 | refid |
| bind | 绑定 | name, value |
| choose | 选择 | `<when test="">` + `<otherwise>`|
| trim | 去除 | prefix, prefixOverrides |

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.itheima.mapper.EmpMapper">
    <update id="update">
        update emp
        <set>
            <if test="username != null">
                username=#{username},
            </if>
            <if test="gender != null">
                gender=#{gender}
        </set>
        where id=#{id}
    </update>

    <!-- delete from emp where id in (1,2,3); -->
    <delete id="deleteByIds">
        delete from emp where id in
        <foreach collection="ids" item="id" separator="," open="(" close=")">
            #{id}
        </foreach>
    </delete>
</mapper>
```
增删改都行

```java
Integer update(Enterprise enterprise);		// 可以直接识别属性名的参数占位符


	<update id="update">
        update eval_enterprise_info
        set
        enterprise_name = #{enterpriseName},
        enterprise_address = #{enterpriseAddress},
        longitude = #{longitude},
        latitude = #{latitude},
        enterprise_list_name = #{enterpriseListName},
        enterprise_reg_address = #{enterpriseRegAddress},
        enterprise_legal_name = #{enterpriseLegalName},
        enterprise_legal_idcard = #{enterpriseLegalIdcard},
        enterprise_legal_household = #{enterpriseLegalHousehold},
        enterprise_legal_phone = #{enterpriseLegalPhone},
        enterprise_manager = #{enterpriseManager},
        enterprise_manager_idcard = #{enterpriseManagerIdcard},
        enterprise_manager_phone = #{enterpriseManagerPhone},
        enterprise_manager_scope = #{enterpriseManagerScope},
        land_usage_name  = #{landUsageName},
        landlord_name = #{landlordName},
        landlord_idcard = #{landlordIdcard},
        landlord_phone = #{landlordPhone},
        reg_capital = #{regCapital},
        floor_space = #{floorSpace},
        machine_number = #{machineNumber},
        employ_number = #{employNumber},
        license_status = #{licenseStatus},
        area_type = #{areaType},
        enterprise_room_status = #{enterpriseRoomStatus},
        status = #{status}
        where id = #{id}
    </update>
```

### other

![image-20240718104409909](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407181044555.png)

### resultType
```xml
    <select id="list" resultType="com.itheima.pojo.Emp">
        select * from emp       
        <where>
            <if test="name != null">
                name like concat('%',#{name},'%')
            </if>
            <if test="gender != null">
                and gender = #{gender}
            </if>
        </where>
        order by update_time desc
    </select>

	

	//////////////// 引用公共sql片段
    <sql id="commonSelect">
        select id, username, password, name, gender, image, job, entrydate, dept_id, create_time, update_time from emp
    </sql>
    <select id="list" resultType="com.itheima.pojo.Emp">
        <include refid="commonSelect"/>
        <where>
            <if test="name != null">
                name like concat('%',#{name},'%')
            </if>
            <if test="gender != null">
                and gender = #{gender}
            </if>
            <if test="begin != null and end != null">
                and entrydate between #{begin} and #{end}
            </if>
        </where>
        order by update_time desc
    </select>
```

### resultMap分开

`column`是sql中select 查询出来的名字，

`property` 是javabean类的字段名，`type`/`javaType`/`ofType`是javabean的全类名。

```xml
<resultMap id="唯一的标识" type="pojo对象A">
    <id column="select出来的主键列名" property="pojo对象A的属性名" />
    <result column="select出来的其他列名" property="pojo对象A的属性名"/>
    <result ..."/>

    <association property="pojo对象A的属性名" javaType="pojo对象A的属性名对应的类型 pojo对象B">
        <id column="select出来的主键列名"  property="pojo对象B的属性名"/>
        <result column="select出来的其他列名" property="pojo对象B的属性名"/>
    </association>

    <collection property="pojo对象A的属性名" ofType="pojo对象A的属性名对应的集合中的pojo对象C">
        <id column="select出来的主键列名" property="pojo对象C的属性名" />
        <result column="select出来的其他列名"  property="pojo对象C的属性名" />  
    </collection>

    <discriminator column="type" javaType="int">		/// 列名，pojo对象A的属性的类型
        <case value="0" resultMap="card1"></case>		/// 根据其值，对应不同的pojo类
        <case value="1" resultMap="card2"></case>
    </discriminator>
</resultMap>
```
#### 基本

[code: resultMap项目](../../../codes/javaweb/resultmap/src/main/java/com/sword/resultmap/mapper/UserMapper.java)

```xml
<select id="getUsers" resultType="com.sword.resultmap.domain.po.User">
    select * from user
</select>

// 返回的天然是一个集合，可用List, Set接收
List<User> getUsers();
```

#### 一对一 association

```java
<resultMap id="user_cardass" type="com.sword.resultmap.domain.dto.UserCardAssociation">
    <id column="id" property="id" />
    <result column="name" property="name" />
    <association property="card" javaType="com.sword.resultmap.domain.dto.Card">
        <id column="card_id" property="cardId"/>
        <result column="card_name" property="name"/>
    </association>
</resultMap>

<select id="getUsersCardAssociation" resultMap="user_cardass">
    select u.id, u.name, c.card_id, c.name card_name
    from user u, card_association c
    where u.id = c.user_id
</select>
```
```
+----+------+---------+-----------+
| id | name | card_id | card_name |
+----+------+---------+-----------+
|  1 | 张三 |      10 | 张三的卡  |
|  2 | 李四 |      11 | 李四的卡  |
|  3 | 王五 |      12 | 王五的卡  |
+----+------+---------+-----------+
```

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407111409649.png)

`c.name cardname` 冲突必须要起别名，不然就是
```json
  {
    "id": 1,
    "name": "张三",
    "cardAssociation": {
      "userId": 1,
      "name": "张三"
    }
  },
```

#### 一对多 collection

之所以叫collection，是因为java传入的不仅可以是List，还可以是Set

```xml
    <resultMap id="user_cardcol" type="com.sword.resultmap.domain.dto.UserCardCollection">
        <id column="id" property="id" />
        <result column="name" property="name" />
        <collection property="cards" ofType="com.sword.resultmap.domain.dto.Card">
            <id column="card_id" property="cardId"/>
            <result column="card_name" property="name"/>
        </collection>
    </resultMap>

    <select id="getUsersCardCollection" resultMap="user_cardcol">
        select u.id, u.name, c.card_id, c.name card_name
        from user u, card_collection c
        where u.id = c.user_id
    </select>
```
```sql
+----+------+---------+-----------+
| id | name | card_id | card_name |
+----+------+---------+-----------+
|  1 | 张三 |      20 | 张三的卡1 |
|  1 | 张三 |      21 | 张三的卡2 |
|  2 | 李四 |      22 | 李四的卡  |
|  3 | 王五 |      23 | 王五的卡  |
+----+------+---------+-----------+
```
```json
[
  {
    "id": 1,
    "name": "张三",
    "cards": [
      {
        "cardId": 20,
        "name": "张三的卡1"
      },
      {
        "cardId": 21,
        "name": "张三的卡2"
      }
    ]
  },
  {
    "id": 2,
    "name": "李四",
    "cards": [
      {
        "cardId": 22,
        "name": "李四的卡"
      }
    ]
  },
  {
    "id": 3,
    "name": "王五",
    "cards": [
      {
        "cardId": 23,
        "name": "王五的卡"
      }
    ]
  }
]
```

#### 多对多 discriminator

```xml
    <resultMap id="carddis" type="com.sword.resultmap.domain.dto.CardDis">
        <id column="card_id" property="cardId" />
        <result column="type" property="type" />
        <discriminator javaType="int" column="type">
            <case value="0" resultMap="card1"></case>
            <case value="1" resultMap="card2"></case>
        </discriminator>
    </resultMap>
    <resultMap id="card1" extends="carddis" type="com.sword.resultmap.domain.dto.CardDis1">
        <result column="name" property="dis1Name"></result>
    </resultMap>
    <resultMap id="card2" extends="carddis" type="com.sword.resultmap.domain.dto.CardDis2">
        <result column="name" property="dis2Name"></result>
    </resultMap>

    <select id="getCardDiscriminator" resultMap="carddis">
        select *
        from card_discriminator
    </select>
```
```
+---------+------+------+
| card_id | name | type |
+---------+------+------+
|      30 | 卡1  |    0 |
|      31 | 卡2  |    1 |
+---------+------+------+
```
```json
[
  {
    "cardId": 30,
    "type": 0,
    "dis1Name": "卡1"
  },
  {
    "cardId": 31,
    "type": 1,
    "dis2Name": "卡2"
  }
]
```

### resultMap子查询

#### 一对一 association

直接指定子查询

​	`select`子查询的标签id。

​	 `column`子查询传递参数。

然后将子查询的结果的赋值给javabean的属性`property`

```java
<resultMap id="EnterpriseMap" type="com.hello.domain.eval.enterprise.valueobject.EnterpriseValueObject">
    <result column="id" property="id"/>
    <result column="enterprise_name" property="enterpriseName"/>
    <result column="area_status" property="areaStatus"/>
    // 多个sql参数
    <association select="queryAreaGovernEntity" column="{enterpriseId=id,areaStatus=area_status}" property="areaGovernEntity"/>
    // 单个sql参数，就不用指定。findRoleByUserId中就是 value
    <collection select="findRoleByUserId" column="id" property="roles"/>	
</resultMap>

<select id="queryAreaGovernEntity" resultType="com.hello.domain.eval.areagovern.entity.AreaGovernEntity">
    select * from eval_area_govern
    where enterprise_id = #{enterpriseId}
    and delete_flag = '0'
    and #{areaStatus} = '1'
    order by id desc limit 1
</select>
        

<select id="findRoleByUserId" resultType="com.safesoft.domain.system.entity.Role">
    select id, name
    from sys_role sr
             inner join sys_user_role sur on sur.role_id = sr.id and sur.user_id = #{value}
</select>
```

