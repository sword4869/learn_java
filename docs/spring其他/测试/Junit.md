Junit4和Junit5与很大不同，我们用4.

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
</dependency>

或者

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```
https://zhuanlan.zhihu.com/p/86624354

```java
import org.junit.Test;

public class TestCase {
    @Test
    public void test(){			// 不同于SpringBootTest， junit需要public
        System.out.println("test");
        
        assertEquals(3, element);

        // dynamicArray 实现了 Iterable 接口
        assertIterableEquals(List.of(1,2,3), dynamicArray);
    }
}
```

```java
package com.example;
import org.apache.commons.lang3.time.StopWatch;

public class A {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch(); // Instantiate the StopWatch object
        // Rest of the code...
    }
}
```