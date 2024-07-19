UUID: 唯一命名。

## jdk



```java
import java.util.UUID;

/* 带连字符 */
String str = UUID.randomUUID().toString();
System.out.println(str);
// 50feb107-df25-4936-8bfb-f7acfc73ed04

/* qu'diao */
String str = UUID.randomUUID().toString().replace("-", "");
System.out.println(str);
// 9f15b8c356c54f55bfcb0ee3023fce8a
```