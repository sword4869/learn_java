## String为什么不可改变？

<details>
<summary>bytes+private+final+no setter</summary>

String的底层是字节数组 `byte[]`。

final + private + 且没有提供setter方法。

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112029731.jpg)

</details>

## String 为什么要设计成不可变的


<details>
<summary>效率—线程安全-安全性-哈希表的Key</summary>

- 效率：由于String是不可变的，可以在多个地方共享相同的String对象，避免了重复创建开销。
- 线程安全：多个线程可以同时访问同一个String对象而无需担心数据被修改。
- 安全性：String在很多安全框架和API中广泛使用，如密码学中的加密操作。如果String是可变的，攻击者可以修改String中的值，对应用程序的安全性造成潜在的风险。 
- 哈希表的Key：String不变则hashCode不变，可以将String用在哈希表的Key

需要频繁地字符串拼接，可以使用StringBuilder或StringBuffer类来提高效率。

</details>


## StringBuilder扩容原理


<details>
<summary>16/n*2+2/n1+n2</summary>

- 默认创建一个容量为16的字节数组`byte[] value`。
- 每次扩容，根据添加的内容长度有不同的处理：
  - 如果容量够，那么直接添加。否则容量不够，就扩容。
  - 新容量=(老容量*2+2)。如果还不够，就直接是老容量+添加的内容长度。
- 最大容量是int上限。

```java
// 默认容量为16
StringBuilder sb = new StringBuilder();
System.out.println(sb.capacity());  // 16

// 扩容为原来的2倍 + 2
sb.append("abcdefghijklmnopqrstuvwxyz");
System.out.println(sb.capacity());  // 34 = 16 * 2  + 2

// 如果扩容之后还不够，以实际长度为准
sb.append("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuv");
System.out.println(sb.capacity());  // 74 > 70 = 34 * 2 + 2
```

[🚩字符串-12-字符串相关类的底层原理 P107 - 38:02](https://www.bilibili.com/video/BV17F411T7Ao?p=107&t=2282)

</details>



## StringBuffer和StringBuilder谁是线程安全的


<details>
<summary>details</summary>

StringBuffer是线程安全的。

</details>

## Object有5个虚方法


<details>
<summary>details</summary>

- `public int hashCode()`
- `public boolean equals(Object obj)`
- `public String toString()`
- `protected Object clone()`
- `protected void finalize()`: JDK9被弃用


</details>

## Java的数据类型

## 基本类型和包装类型的区别？

默认值、存储方式、占用空间、等于的比较方式、用途

## 包装类型的缓存机制了解么

## 自动装箱与拆箱了解吗？原理是什么？

## HashMap底层数据结构

## 红黑树和链表的转换

## HashMap的扩容机制吗？