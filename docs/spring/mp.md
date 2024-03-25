```java
// 方式1
CourseBase courseBaseNew = new CourseBase();
BeanUtils.copyProperties(dto, courseBaseNew);


// 方式2
CourseBase courseBaseNew2 = BeanUtils.copyProperties(dto, CourseBase.class);
```
`BeanUtils.copyProperties()`，是根据属性名字来匹配的，如果名字不一样的话，那么就得手动get/set。所以设计dto和po之间时，要注意名字。