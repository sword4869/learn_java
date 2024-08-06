## 基于REST风格的URL

1、@GetMapping，处理 Get 请求。

2、@PostMapping，处理 Post 请求 

3、@PutMapping，用于更新大部分资源 　

4、@PatchMapping，用于更新少部分资源

5、@DeleteMapping，处理删除请求 　

描述模块的功能通常使用复数，也就是加s的格式来描述，表示此类资源，而非单个资源。如：users、emps、books…
```
http://localhost:8080/users/1  GET：查询id为1的用户
http://localhost:8080/users    POST：新增用户
http://localhost:8080/users    PUT：修改用户
http://localhost:8080/users/1  DELETE：删除id为1的用户
```

**类mapping 的常量+方法mapping的restful，而不是方法上写**

e.g. `@DeleteMapping(WebURIMappingConstant.REQUEST_MAPPING_TEACHER+ "/{teacherId}")` → `@RequestMapping(WebURIMappingConstant.REQUEST_MAPPING_TEACHER)` + `@DeleteMapping("/{teacherId}")`。

```java
@RestController
@RequestMapping(WebURIMappingConstant.REQUEST_MAPPING_TEACHER)		// "/users"
public class TeacherController {
    /**
     * 获取教师信息（分页）
     */
    @GetMapping
    public Rest<JsonPagedVO<TeacherInfoVO>> getTeachers(@Validated TeacherInfoCriteria teacherInfoCriteria) {
        LOGGER.info("获取教师信息 {}", teacherInfoCriteria);
        JsonPagedVO<TeacherInfoVO> teacherList = teacherService.getTeacherList(teacherInfoCriteria);
        return RestBody.okData(teacherList);
    }
    
    /**
     * 获取教师信息（分页）
     */
    @GetMapping("/{teacherId}")
    public Rest<TeacherInfoVO> getTeacherById(@PathVariable long teacherId) {
        return RestBody.okData(teacherList);
    }
    
    /**
     * 更新教师信息
     */
    @PutMapping
    public Rest<Void> updateTeacher(@Validated @RequestBody TeacherInfoDto teacherInfoDto) {
    }
    
    /**
     * 新增教师信息
     */
    @PostMapping
    public Rest<Void> addTeacher(@Validated @RequestBody TeacherInfoDto teacherInfoDto) {
    }
    
    /**
     * 删除教师信息
     */
    @DeleteMapping("/{teacherId}")
    public Rest<Void> deleteTeacher(@PathVariable long teacherId) {
    }
```

controller的入参long: 优化了Long的null检查，用long接收到null会报类型不匹配的错。

```java
@DeleteMapping(WebURIMappingConstant.REQUEST_MAPPING_INSTITUTION_BASIC_TEACHER + "/{teacherId}")
public Rest<Void> deleteTeacher(@PathVariable long teacherId) {
    
    
@DeleteMapping(WebURIMappingConstant.REQUEST_MAPPING_INSTITUTION_BASIC_TEACHER + "/{teacherId}")
public Rest<Void> deleteTeacher(@PathVariable Long teacherId) {
    Objects.requireNonNull(teacherId, "teacherId is null");
```

## restbody统一返回类型

code, data, msg, (identifier)

泛型Void对应无实际数据。

```java
package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBody<T> {
    private int code = 200; // 响应码
    private String msg = "ok";  //响应信息 描述字符串
    private T data; //返回的数据
    private String identifier = "success";

    public RestBody() {
    }

    public static RestBody<Void> ok() {
        return new RestBody();
    }

    public static RestBody<Void> ok(String msg) {
        RestBody<Void> restBody = new RestBody();
        restBody.setMsg(msg);
        return restBody;
    }

    public static <T> RestBody<T> okData(T data) {
        RestBody<T> restBody = new RestBody();
        restBody.setData(data);
        return restBody;
    }

    public static <T> RestBody<T> okData(T data, String msg) {
        RestBody<T> restBody = new RestBody();
        restBody.setData(data);
        restBody.setMsg(msg);
        return restBody;
    }

    public static RestBody<Void> failure(String msg, String identifier) {
        RestBody<Void> restBody = new RestBody();
        restBody.setMsg(msg);
        restBody.setIdentifier(identifier);
        return restBody;
    }

    public static RestBody<Void> failure(int httpStatus, String msg) {
        RestBody<Void> restBody = new RestBody();
        restBody.setCode(httpStatus);
        restBody.setMsg(msg);
        restBody.setIdentifier("-9999");
        return restBody;
    }

    public static <T> RestBody<T> failureData(T data, String msg, String identifier) {
        RestBody<T> restBody = new RestBody();
        restBody.setData(data);
        restBody.setMsg(msg);
        restBody.setIdentifier(identifier);
        return restBody;
    }
}
```



## 异常处理

一种是service抛出异常，再由全局异常捕获并设置返回给前端的错误信息。另一种是service向上传递ReturnInfo（类似restbody，是在service层中的错误信息的包装），RestBody再从ReturnInfo包装。

前者是统一写各种异常类型，关键是msg错误信息和异常类型对应。后者，相当于是每个都各自设置异常类型。



