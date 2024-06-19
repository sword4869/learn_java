### 默认判断地址值

```java
// 只是判断是不是同一个地址值
public boolean equals(Object obj) {
    return (this == obj);
}
```
# equals和hashCode的关系

如果不用哈希表，那么无关。

如果用哈希表，那么equals和hashCode都必须重写。

​	哈希表中添加元素，是比较元素值是否相同的，即重写的equals（默认比较地址）。

​	一个个比较太费劲，那么引入hashCode来加速比较。hashCode不同的，那么属性必然不相同；相同，有可能是哈希冲突，要再比较equals属性。即需要重写hashCode。

## IDEA自动重写

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406181506903.jpg)