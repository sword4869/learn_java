## pagehelper

技术架构: Mybait + pagehelper。

在Mapper中我们只需要进行正常的列表查询即可。

在Service层中，调用Mapper的方法之前设置分页参数，在调用Mapper方法执行查询之后，解析分页结果，并将结果封装到对象中返回。

### 在pom.xml引入依赖

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.4.2</version>
</dependency>
```

```yml
pagehelper:
  helper-dialect: mysql
  reasonable: true  # 在启用合理化时，如果 pageNum<1，则会查询第一页；如果 pageNum>pages，则会查询最后一页。
  support-methods-arguments: true # 支持通过Mapper接口参数来传递分页参数
  # close-conn: true	# 默认true关闭。当使用运行时动态数据源或没有设置 helperDialect 属性自动获取数据库类型时，会自动获取一个数据库连接， 通过该属性来设置是否关闭获取的这个连接，设置为 false 后，不会关闭获取的连接，这个参数的设置要根据自己选择的数据源来决定。
```

### 不管mybatis-config.xml之类的东西，直接能用

```java
// (1) 设置分页参数
int pageNum = 2;
int pageSize = 20;
PageHelper.startPage(pageNum, pageSize);		// 都要求是int值，平时是从Long转


// (2) 正常的mybatis查询：得到List<User> users;
// 转化正常查询集合List<T>→Page<T>
Page<User> p = (Page)userMapper.selectAll();

// (3) 返回结果
// p,getResult()获取List<T>，p.getTotal()总条数, p.getPages()总页
List<UserVO> vos = BeanUtil.copyToList(p.getResult(), UserVO.class);
PageDTO<UserVO> pageDTO = new PageDTO<UserVO>(p.getTotal(), (long) p.getPages(), vos);
```



遗留问题：随便翻翻，没看到有排序的的，以后再说。