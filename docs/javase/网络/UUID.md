解决文件名重复的问题

```java
import java.util.UUID;

String str = UUID.randomUUID().toString().replace("-", "");
System.out.println(str);
// 9f15b8c356c54f55bfcb0ee3023fce8a
```