## knife4j
```xml
<!--knife4j-->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi2-spring-boot-starter</artifactId>
    <version>4.1.0</version>
</dependency>
```

```yml
knife4j:
  enable: true
  openapi:
    title: 用户管理接口文档
    description: "用户管理接口文档"
    email: zhanghuyi@itcast.cn
    concat: 虎哥
    url: https://www.itcast.cn
    version: v1.0.0
    group:
      default:
        group-name: default
        api-rule: package
        api-rule-resources:
          - com.sword.crud.controller     # 去看哪的controller
```
```
http://localhost:8080/doc.html
```
### header参数

比如，绕过登录认证。

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112200682.png)

## Swagger 

```xml
到底哪个管用，没试过
<dependency>
	<groupId>org.springdoc</groupId>
	<artifactId>springdoc-openapi-ui</artifactId>
	<version>1.2.30</version> 
</dependency>


<dependency>
	<groupId>com.spring4all</groupId>
	<artifactId>swagger-spring-boot-starter</artifactId>
	<version>1.9.0.RELEASE</version> 
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

```yml
swagger:
  title: "启明内容管理系统"
  description: "内容系统管理系统对课程相关信息进行管理"
  base-package: com.qiming.content
  enabled: true
  version: 1.0.0
```

## Swagger注解

### Controller 

类上 `@Api("用户")`

方法 `@ApiOperation("简单增 save")`

方法参数 `@ApiParam("用户id")`

```java
@Api("用户")
@RestController
public class UserController{
    @ApiOperation("简单删除 removeById")
    @DeleteMapping("{id}")
	@ApiResponse(code = 200, message = "添加成功", response = DepartmentValueObject.class)	// 单个
    @ApiResponses(value = {@ApiResponse(code = 200, message = "[部门信息]", response = DepartmentValueObject.class)})	// 多个
    public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id){
    	userService.removeById(id);
    }
}
```

### VO

类上 `@ApiModel(description = "用户VO实体")`

字段 `@ApiModelProperty("用户id")`

```java
package com.sword.crud.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户VO实体")
public class UserVO {
    
    @ApiModelProperty("用户id")   // 第一个参数就是value
    private Long id;
    
    @ApiModelProperty(value = "用户名", example = "Jack")		// example对apifox自动生成很有用，
    private String username;
    
    @ApiModelProperty(value = "机构id", example = "1", notes = "机构id", required = true)	// 默认 required = false
    private Long institutionId;
}
```

