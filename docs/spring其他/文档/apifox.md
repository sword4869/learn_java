## 识别的注释类型

可以识别

```java
/**
* 日期格式化
*/
private LocalDateTime localDateTime;

@ApiModelProperty(value = "日期格式化2")
private LocalDateTime localDateTime;
```

不能识别

```java
// 日期格式化3
private LocalDateTime localDateTime;
```

## 操作过程

无须maven package，修改文件后直接对项目上传apifox，即可看到修改。

修改java字段可以看到变化，但是对注释有问题：删除、修改原来的注释后，apifox中的注释还是不变。