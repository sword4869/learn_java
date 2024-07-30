## Mybatis导入

```xml
<!-- mybatis起步依赖 -->
<mybatis.starter.version>3.0.1</mybatis.starter.version>
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>${mybatis.starter.version}</version>
</dependency>

<!-- mysql驱动包依赖 -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

```xml
<mybatis.version>3.5.7</mybatis.version>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>${mybatis.version}</version>
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

`@Mapper注解`: 表示是mybatis中的Mapper接口。程序运行时：框架会自动生成接口的实现类对象(代理对象)，并给交Spring的IOC容器管理.

`@MapperScan("com.sword.crud.mapper")`: 在启动上加，所以在mapper接口上就不用标注`@Mapper`

### Plan b

```xml
mybatis:
  mapper-locations: classpath:mybatis/**/*.xml
  config-location: classpath:mybatis.xml
```

mybatis.xml

![image-20240725114051510](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407251140668.png)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <setting name="jdbcTypeForNull" value="NULL"/>
    </settings>
</configuration>
```

## 编写mapper查询之XML

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407111409647.png)

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407111409648.png

### xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.hello.domain.eval.market.repository.MarketRepository">
</mapper>
```

idea创建xml模板

![image-20240722130302356](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407221303526.png)

![image-20240722130329779](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407221303917.png)

### 效果

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

增删改都行

```java
Integer update(Enterprise enterprise);		// 可以直接识别属性名的参数占位符


<update id="update">
    update eval_enterprise_info
    set
    enterprise_name = #{enterpriseName},
    enterprise_address = #{enterpriseAddress},
    status = #{status}
    where id = #{id}
</update>
```

### insert

#### 返回主键

默认情况下，执行插入操作时，是不会主键值返回的。

如果需要获取sql插入自动生成的主键id，需要设置`useGeneratedKeys="true"`（默认false）和`keyProperty="实体类属性名"` 这两个属性: 

​	keyProperty中对应的值是实体类的属性，而不是数据库的字段。

​	无关insert方法的返回值，而是使用传入的实体对象的主键对应属性的值

```xml
<!-- 写法1 -->
<insert id="addRuleHistory" keyProperty="id" useGeneratedKeys="true">
    INSERT INTO eval_rule_history(id,eval_rule_no) values
    (nextval('eval_id_seq'),#{evalRuleNo})
</insert>

<!-- 写法2：数据库设置了 "id" int8 NOT NULL DEFAULT nextval('eval_id_seq'::regclass), mybatis直接都不写 -->
<insert id="insertRoom" keyProperty="id" useGeneratedKeys="true">
    INSERT INTO eval_rule_history(eval_rule_no) values
    (#{evalRuleNo})
</insert>
```

```java
final int saveRes = roomInfoRepository.insertRoom(roomInfoEntity);
if (saveRes == 0) {
    return ReturnInfo.failure("保存房间信息失败");
}
return roomInfoEntity.getId();		// 使用传入的实体对象的主键对应属性的值
```

PS：老写法

```xml
<insert id="addRuleHistory">
    <selectKey keyProperty="id" resultType="long" order="BEFORE">
        select nextval('eval_id_seq')
    </selectKey>
    insert into eval_rule_history(id,eval_rule_no) values
    (#{id},#{evalRuleNo})
</insert>
```

#### 不返

```xml
<insert id="submit">
    insert into eval_area_govern (enterprise_id, is_target, is_noticed, is_sign, is_remove, area, score)
    values ( #{enterpriseId}, #{isTarget}, #{isNoticed}, #{isSign}, #{isRemove}, #{area}, #{score})
</insert>
```

#### 批量插入

```java
int insert(List<InstitutionMaterial> institutionMaterials);
```

```xml
<insert id="insertBatch">    // 不用写parameterType。
    INSERT INTO tb_student (name, age, phone, address, class_id) VALUES
    <foreach collection="list" separator="," item="item">			// collection="list"固定写法
        (#{item.name},#{item.age},#{item.phone},#{item.address},#{item.classId})
    </foreach>
</insert>
```

PS: 自动生成的代码是错误的，`parameterType="java.util.List&lt;com.safesoft.domain.institution.entity.InstitutionMaterial&gt;"`, 会报错`Cannot find class: java.util.List<com.safesoft.domain.institution.entity.InstitutionMaterial>`

```java
public interface StudentMapper {
    int insertBatch(List<Student> studentList);
}
```

[Mybatis 三种批量插入数据 方式-CSDN博客](https://blog.csdn.net/u010253246/article/details/115752049)

### update

都要这样写，因为前端传来的可能是全部的字段，也可能是只传更新的字段（其他未变动的字段就是null）。

```xml
<update id="update">
    update emp
    <set>
        update_time = now(), 
        updated_user_id = #{updatedUserId}		# 固定传、固定设置的东西就写前面
        <if test="username != null and username != ''">			# 不仅要 username != null, 还要 username != ''
            username=#{username},
        </if>
        <if test="gender != null and gender != ''">
            gender=#{gender}
    </set>
    where id=#{id}
</update>
```

### delete

```xml
<!-- delete from emp where id in (1,2,3); -->
<delete id="deleteByIds">
    delete from emp where id in
    <foreach collection="ids" item="id" separator="," open="(" close=")">
        #{id}
    </foreach>
</delete>
```

### select

#### 多条件

```xml
<!--分页查询的总条数-->
<select id="insCount" resultType="java.lang.Long">
    select count(distinct ins.*) from t_institution_info ins join t_label_institution l on ins.id =
    l.institution_id
    <where>
        <if test="institutionName != null and institutionName != ''">		# # 不仅要 username != null, 还要 username != ''
            and institution_name like CONCAT('%',#{institutionName},'%')
        </if>
        <if test="creditCode != null and creditCode != ''">
            and credit_code = #{creditCode}
        </if>
        <if test="township != null and township != ''">
            and township = #{township}
        </if>
        <if test="institutionType != null and institutionType != ''">
            and type_code = #{institutionType}
        </if>
        <if test="institutionStatus != null and institutionStatus !=''">
            and ins_status = #{institutionStatus}
        </if>
        <if test="label != null and label != ''">
            and l.label_code = #{label}
        </if>
    </where>
</select>
```

#### 直接VO还是PO再转VO

如果VO是PO中的挑几个字段，那么PO再BeanUtil转VO。

如果VO是PO的联表查询，那么直接resultMap对应VO。

### other

![image-20240718104409909](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407181044555.png)

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


### 参数占位符的值是什么

> mybatis的参数占位符是getter获取的值，而不是直接变量的值。
>
> 这就是为什么要在entity上设置 getter。

```java
@Data
public class TeacherInfoCriteria {
    private Integer start = 0;			// 直接变量的值 0
    private Integer limit = 10;
    private Integer page = 1;			
    private Long institutionId;

    @Override
    public Integer getStart() {			// getter返回 1
        if(getLimit() == 10){
            return 1;
        }
    }
}
```

```xml
<select id="queryTeacherInfoPage" resultMap="VOmap">
    select
        ti.id, ti.institution_id, ti.teacher_name, ti.id_num, sp2.name gender, ti.phone, sp.name label_code
    from t_teacher_info ti
             join t_label_teacher lt on ti.id = lt.teacher_id
             join sys_parameter sp on lt.label_code = sp.code and sp.type = 'custom_tab'
             join sys_parameter sp2 on ti.gender = sp2.code and sp2.type = 'gender'
    where ti.institution_id = #{institutionId}
    order by ti.id desc
    limit #{limit} offset #{start}				
</select>
```



```cmd
: ==>  Preparing: select ... where ti.institution_id = ? order by ti.id desc limit ? offset ?
: ==> Parameters: 1(Long), 10(Integer), 1(Integer)  
```



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

列名必须对应，可以是驼峰对应 `dept_id`对`deptId`，也可以是完全对应`deptId`对`deptId`

### resultMap分开

`column`是sql中select 查询出来的名字，

`property` 是javabean类的字段名，`type`/`javaType`/`ofType`是javabean的全类名。

```xml
<resultMap id="唯一的标识" type="pojo对象A">
    <id column="select出来的主键列名" property="pojo对象A的属性名" />			// 好像主键写成 result 效果一样，只是为了展示吗？
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

【例子2】

简单的 `List<String> labelCodes` 到 `label_code` 列

```xml
<collection property="labelCodes" ofType="java.lang.String">
    <result column="label_code"/>		// 就不用写property了
</collection>
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