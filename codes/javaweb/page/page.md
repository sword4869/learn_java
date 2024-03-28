---

sql脚本放在resouces下。

---

## mp

定义[config类](./src/main/java/com/sword/page/config/MybatisPlusConfig.java)，导入mp插件。

1. 前端的请求参数：[分页参数](./src/main/java/com/sword/page/domain/query/PageQuery.java)和[业务参数](./src/main/java/com/sword/page/domain/query/UserConditionQuery.java)。分页参数用一个类定义，业务参数类继承分页参数，实现参数复用。
2. mp核心: 
   - Page对象，传入第几页和页大小，添加结果排序规则；
   - 如果无筛选条件，调用IService的`page(page)`方法。方法内部就是selectPage。
   - 如果有筛选条件，设置wrapper对象，然后调用BaseMapper的`selectPage(page, wrapper)`方法。
   - 查询结果在page对象里。
3. 将查询结果返回给前端：
   - 将page对象里的`PO`类集合结果转化为[`VO`](./src/main/java/com/sword/page/domain/vo/UserVO.java)集合。
   - 再扔给[`PageDTO<VO>`](./src/main/java/com/sword/page/domain/dto/PageDTO.java)统一格式，并由page对象的total属性设置总条数、pages属性设置当前页码。
## pagehelper依赖

在Mapper中我们只需要进行正常的列表查询即可。

在Service层中，调用Mapper的方法之前设置分页参数，在调用Mapper方法执行查询之后，解析分页结果，并将结果封装到对象中返回。

1. 在pom.xml引入依赖

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
    ```
   
    不管mybatis-config.xml之类的东西，直接能用

2. service
    
    遗留问题：随便翻翻，没看到有排序的的，以后再说。
    ```java
    @Override
    public PageDTO<UserVO> queryUsersByPH(UserConditionQuery query) {
        // 当前页，页大小
        PageHelper.startPage(query.getPageNo().intValue(), query.getPageSize().intValue());
        List<User> users = lambdaQuery()
                .like(query.getKeyword() != null, User::getUsername, query.getKeyword())
                .eq(query.getStatus() != null, User::getStatus, query.getStatus())
                .ge(query.getMinBalance() != null, User::getBalance, query.getMinBalance())
                .le(query.getMaxBalance() != null, User::getBalance, query.getMaxBalance())
                .list();
        // 转化正常查询集合→Page<T>：p,getResult()获取List<T>，p.getTotal()总条数, p.getPages()总页
        com.github.pagehelper.Page<User> p = (com.github.pagehelper.Page<User>) users;
        List<UserVO> vos = BeanUtil.copyToList(p.getResult(), UserVO.class);
        PageDTO<UserVO> pageDTO = new PageDTO<UserVO>(p.getTotal(), (long) p.getPages(), vos);
        return pageDTO;
    }
    ```