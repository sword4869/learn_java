`@Repository`和`@Mapper`都是作用在dao层接口，声名bean，交给spring 容器管理



## 单独`@Repository`

不可以单独使用，否则会报错误，要想用，必须配置扫描地址（@MapperScannerConfigurer）



## 单独`@Mapper`

`@Mapper`是mybatis的注解。不需要配置扫描地址，可以单独使用

```java
@Mapper
public interface VideoMapper{
```

有很多mapper接口时，就需要写很多@Mappe注解。可以在项目启动类中加入`@MapperScan` 或 `@MapperScans`，从而不用再在接口上添加任何注解。

```java
@SrpingBootApplication
@MapperScan("com.safesoft.domain.*.mapper")		// 单个时
@MapperScans({@MapperScan("com.safesoft.domain.*.mapper"), @MapperScan("com.safesoft.domain.*.repository")})	// 多个时
public class TestApplication{
```

## `@Mapper`+`@Repository`

在idea中单独使用@Mapper注解，在@Autowired时，idea会提示找不到bean，但是不影响运行，如果想消除爆红，可以将@Mapper注解跟@Repository注解一起用，这样便可消除爆红
