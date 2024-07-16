```xml
<!-- await -->
<dependency>
    <groupId>org.awaitility</groupId>
    <artifactId>awaitility</artifactId>
    <version>4.2.1</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```



```java
import java.util.concurrent.TimeUnit;
import static org.awaitility.Awaitility.await;

@SpringBootTest
public class EmailTest {
    @Test
    void runner(){
		// 使用await等待2分钟。
        Integer i = 12;
        await().atMost(2, TimeUnit.MINUTES).until(() -> i==1);
    }
}
```

[Usage · awaitility/awaitility Wiki (github.com)](https://github.com/awaitility/awaitility/wiki/Usage#simple)