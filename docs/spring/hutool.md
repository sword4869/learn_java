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
userDTO = BeanUtil.copyProperties(user, userDTO.class)

List<User> users = userService.listByIds(ids);
List<UserVO> userVOs = BeanUtil.copyToList(users, UserVO.class);

map = BeanUtil.beanToMap(userDTO)

userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false); // fasle表示有错就抛出
```

---

```
StrUtil.isBlank()
StrUtil.isEmpty()

UUID.randomUUID()
```