
解决方案：

第一：

在程序运行的时候加入vm参数`-Xint`表示禁用即时编辑器，不推荐，得不偿失（其他程序还要使用）

第二：

在修饰`stop`变量的时候加上`volatile`,表示当前代码禁用了即时编辑器，问题就可以解决，代码如下：

```java
static volatile boolean stop = false;
```