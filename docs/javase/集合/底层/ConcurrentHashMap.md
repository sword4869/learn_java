- [1. concurrentHashMap](#1-concurrenthashmap)
- [2. JDK1.7 中concurrentHashMap](#2-jdk17-中concurrenthashmap)
- [3. JDK1.8中concurrentHashMap](#3-jdk18中concurrenthashmap)

---
## 1. concurrentHashMap
ConcurrentHashMap 是一种**线程安全**的高效Map集合

底层数据结构：

- JDK1.7：分段数组 segment +链表实现

- JDK1.8：和HashMap1.8的结构一样，数组+链表/红黑树。

## 2. JDK1.7 中concurrentHashMap

数据结构: 分段数组 segment +链表实现
- 提供了一个 **segment 数组**，在初始化 ConcurrentHashMap 的时候可以指定数组的长度，默认是16，一旦初始化之后中间**不可扩容**
- 在每个segment中都可以挂一个**HashEntry数组**，数组里面可以存储具体的元素，ashEntry数组是**可以扩容的**
- 在HashEntry存储的数组中存储的元素，如果发生冲突，则可以挂**单向链表**
- Segment 是一种可重入锁 ReentrantLock，每个 Segment 守护一个HashEntry 数组里得元 素，当对 HashEntry 数组的数据进行修改时，必须首先获得对应的 Segment 锁

- 先去计算key的hash值，`hash(key)`
put流程
- 以hash值，确定segment数组下标，确定hashEntry数组中的下标，来操作数据
- ReentrantLock对segment数组下标进行加锁，获取锁失败就cas自旋锁进行尝试。

![](../../../../images/image-20230505092654811.png)

![](../../../../images/image-20230505093055382.png)

## 3. JDK1.8中concurrentHashMap

在JDK1.8中，数据结构同HashMap：数组+红黑树+链表

采用 CAS + Synchronized来保证并发安全进行实现

- CAS控制**数组节点的添加**

- synchronized只锁定当前**链表或红黑二叉树的首节点**，只要hash不冲突，就不会产生并发的问题 , 效率得到提升

![](../../../../images/image-20230505093507265.png)



