
LinkedList 底层使用的是 **双向链表** 数据结构（JDK1.6 之前为循环链表，JDK1.7 取消了循环。

用的双链表是不带哨兵的形式。

![Alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112132575.png)


查询慢,增删快,但是如果操作的是首尾元素，速度极快。

所以多了很多首尾操作的特有API

线程不安全。