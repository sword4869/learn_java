索引页和数据页。

先从磁盘读取索引页到内存，找对对应的行后，再去磁盘读取数据页。



主键索引a，普通列b

select a  where a = 1: 用到索引

select a where a > 1: 也用到了索引

select a where b = 1: 全表扫描



没有用到索引的情况就是全表扫描，b+树的叶子节点是一条链表，那就顺着这条链表找。



## 联合索引

![00dc9a63ca1bd72ed195ba72bd642c8](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407302129684.png)

> 单纯问是否走索引?

只要where中有 a，那就用到了索引。

> 高级点：a字段有没有用上索引，b字段有没有用上索引