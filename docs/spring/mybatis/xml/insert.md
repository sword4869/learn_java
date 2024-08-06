
## insert


### 不返主键

```xml
<insert id="submit">
    insert into eval_area_govern (enterprise_id, is_target, is_noticed, is_sign, is_remove, area, score)
    values ( #{enterpriseId}, #{isTarget}, #{isNoticed}, #{isSign}, #{isRemove}, #{area}, #{score})
</insert>
```


### 返回主键

默认情况下，执行插入操作时，是不会主键值返回的。

如果需要获取sql插入自动生成的主键id，需要设置`useGeneratedKeys="true"`（默认false）和`keyProperty="实体类属性名"` 这两个属性: 

​	keyProperty中对应的值是实体类的属性，而不是数据库的字段。

​	无关insert方法的返回值，而是使用传入的实体对象的主键对应属性的值

```xml
<!-- 写法1: 指定序列生成主键 -->
<insert id="addRuleHistory" keyProperty="id" useGeneratedKeys="true">
    INSERT INTO eval_rule_history(id,eval_rule_no) values
    (nextval('eval_id_seq'), #{evalRuleNo})
</insert>

<!-- 写法2：insert主键返回可不写主键。
	数据库设置了 "id" int8 NOT NULL DEFAULT nextval('eval_id_seq'::regclass), mybatis直接都不写 -->
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

### 批量插入

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