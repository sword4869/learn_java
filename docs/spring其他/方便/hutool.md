

```xml
<!--hutool-->
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.7.17</version>
</dependency>
```
## Bean

### bean和bean之间拷贝
#### copyProperties
```java
// 方式1
CourseBase courseBaseNew = new CourseBase();
BeanUtils.copyProperties(dto, courseBaseNew);


// 方式2
CourseBase courseBaseNew2 = BeanUtils.copyProperties(dto, CourseBase.class);
```
`BeanUtils.copyProperties()`，是根据属性名字来匹配的，如果名字不一样的话，那么就得手动get/set。所以设计dto和po之间时，要注意名字。

#### toBean

```java
LearningLessonVO vo = BeanUtils.toBean(lesson, LearningLessonVO.class)
```


### bean和map

bean→map
```java
UserDTO userDTO = new UserDTO();

// UserDTO的Long字段，在map中value还是Long类型
Map<String, Object> userMap = BeanUtil.beanToMap(userDTO);

// UserDTO的Long字段，在map中value是String类型
Map<String, Object> userMap2 = BeanUtil.beanToMap(userDTO, new HashMap<>(),
        CopyOptions.create()
            .setIgnoreNullValue(true)      // 忽略 null 值的字段
            .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));    // 将 value 转化为 String 类型
```

map→bean
```java
Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);  // fasle表示有错就抛出
```

### bean和list

```java
List<User> users = userService.listByIds(ids);
List<UserVO> userVOs = BeanUtil.copyToList(users, UserVO.class);
```

## Json

[json#hutool](json.md)

## 字符串

```java
StrUtil.isBlank(s)   
StrUtil.isEmpty(s)

StrUtil.isNotBlank(s)   // 非null、非空、非制表换行，确实有实在的东西
```
```java
String idStr = StrUtil.join(",", ids);
```
```java
String uuid = UUID.randomUUID().toString(true);   // true表示把`-`去掉
```

## Boolean

```java
// 解决flag是null时，返回false
Boolean flag = null;
boolean f = BooleanUtil.isTrue(flag);
```

## 集合

```java
CollUtil.isEmpty(records)     // null，size()==0
```
```java

```
## 文件

```java
FileUtil.del(file);   // File file
```