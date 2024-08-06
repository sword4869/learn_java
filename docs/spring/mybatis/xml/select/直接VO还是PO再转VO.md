## 直接VO还是PO再转VO

（1）看有没有枚举字段（这里Entity是String Integer，而不是 enum）。

如果没有枚举类型。

​	那么随便。既可以mapper直接查到VO里，也可以PO再BeanUtil.copyProperty转VO。

如果有枚举类型，

​	那么建议mapper中需要联表查询到VO里。mapper将原生结果放入PO里，再用Java手动替换为对应的展示字符串，太费劲。

（2）看是否VO中有单个PO没有的字段

如果只是VO在单个PO中的挑几个字段，那么都可以。

如果VO是PO的联表查询，那么要用mapper直接resultMap对应VO。



PS：枚举字段在数据库中要设计为 `varchar(32)`，而不是 `int(4)`。

虽然可以分别设计，Entity用Integer，VO用String，但是这样写VO麻烦，而且BeanUtil.copyProperty存在风险。

```java
Dog dog = new Dog();		// String name
dog.setName("1234567890123");
System.out.println(dog);

DogVO dogVO1 = BeanUtil.copyProperties(dog, DogVO.class);		// Integer name
System.out.println(dogVO1);
// TestBean.Dog(name=1234567890123)
// TestBean.DogVO(name=1912276171)
```

