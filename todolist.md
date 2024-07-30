- [ ] ![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112217535.png) 这个final什么意思
- [ ] 有么有区别大吗？

    ```
    url: jdbc:mysql://127.0.0.1:3306/mp?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true
    ```

    涉及批量更新 `rewriteBatchedStatements=true`

---
0429





---
## 代码美观
- [ ] ctrl_alt_o import 优化无用导入
  ctrl_alt_l format  格式化代码 

- [ ] 多段之间空一行
  
- [ ] if结果提出来再比较

- [ ] 注释在controller、service(interface上写，impl上不写)、mapper写（在mapper上详写, xml的sql上中简写）


## restful


- [ ] 
  类mapping 的常量+方法mapping的restful

- [ ] restbody统一返回类型
  
  泛型Void对应无实际数据。
  
  一种是service抛出异常，再由全局异常捕获并设置返回给前端的错误信息。另一种是service向上传递ReturnInfo（类似restbody，是在service层中的错误信息的包装），RestBody再从ReturnInfo包装。
  前者是统一写各种异常类型，关键是msg错误信息和异常类型对应。后者，相当于是每个都各自设置异常类型。


- [ ] 
  controller的入参long: 优化了Long的null检查

## 日志记录
- [ ] 打印入参

- [ ] if分支进入日志
## db

- [x] 主键返回可不写主键。


- [ ] 默认值的不写

- [ ] select *写开

- [ ] 
  sql查重 count


- [ ] mapstruct

- [ ] 非驼峰的阅读量

## 注解优化

- [ ] `@ToString`的callSuper

  ```
  @ToString(callSuper = true)		// 默认false，callSuper = true表示在toString方法中包含父类的toString方法，不然只会打印子类的属性
  ```

  

- [ ] `@valid`和`@Validated`的区别，在于后者多个分组。

  注解的向下传递也要写。

  配合全局捕获，对应抛出的异常类型是 `ValidationException`。

  validationer校验。继承抽象service

## 其他

- [ ] StringUtils.hasText(spring自带)（apcha的common3的不要用）

- [ ] CollectionUtils.isEmpty
  
- [ ] 
  stream已经算老特性了，无须担心别人学不会高级

- [ ] 
  list用notEmpty，string用notblank

- [ ] 重写String。more

---

前端怎么实现局部更新？

