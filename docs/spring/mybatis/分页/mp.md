定义 [config类](../../codes/javaweb/crud/src/main/java/com/sword/crud/config/MybatisPlusConfig.java) 去导入mp分页插件。

1. 前端的请求参数：[分页参数的父类](../../codes/javaweb/crud/src/main/java/com/sword/crud/domain/query/PageQuery.java)和[继承它的业务参数](../../codes/javaweb/crud/src/main/java/com/sword/crud/domain/query/UserConditionQuery.java)。
   
    分页参数用一个类定义，业务参数类继承分页参数，实现参数复用。
2. mp核心: 
   - Page对象，传入第几页和页大小，添加结果排序规则。
   - 查询结果是原地修改的，还在Page对象里，不用返回值。当然也可以用返回值。
3. 将查询结果返回给前端：
   - 将page对象里的`PO`类集合结果转化为[`VO`](../../codes/javaweb/crud/src/main/java/com/sword/crud/domain/vo/UserVO.java)
   - 再扔给[`PageDTO<VO>`](../../codes/javaweb/crud/src/main/java/com/sword/crud/domain/dto/PageDTO.java)统一格式，并由page对象的total属性设置总条数、pages属性设置当前页码。

## page创建方式

```java
// 从第一页开始
Page<User> page = new Page<>(1, 10);
Page<User> page = Page.of(1, 10);

// 排序（可选）
page.addOrder(new OrderItem("balance", false));
```

## 最简单的写法（不排序）

```java
// 如果无筛选条件，调用IService的`page(page)`方法。方法内部就是selectPage
Page<Blog> page = blogService.page(new Page<>(current, PAGE_SIZE));

// 如果有筛选条件，设置wrapper对象，调用IService的`page(page, wrapper)`方法。或者4钟wrapper方式。
Page<Blog> page = blogService.query()
        .eq("user_id", user.getId())
        .page(new Page<>(current, PAGE_SIZE));

long total = page.getTotal();  // 总条数
long pages = page.getPages();  // 总条数可以划分为几页
List<Blog> blogs = page.getRecords();  // 查询结果
```

## 两种排序方式

```java
// 在page这里排序
Page<User> page = new Page<>(1, 10);
page.addOrder(new OrderItem("balance", false));


// 在wrapper排序
Page<Blog> page = blogService.query()
    .orderBy(query.getSortBy() == null, false, "balance")   // 默认排序
    .orderBy(query.getSortBy() != null, false, query.getSortBy()) // 自定义排序
    .page(new Page<>(1, 10));
```