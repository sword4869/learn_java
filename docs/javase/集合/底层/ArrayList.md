# 1. 源码分析（jdk1.8）

分析ArrayList源码主要从三个方面去翻阅：成员变量，构造函数，关键方法

## 1.1. 成员变量

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130147.png)

ArrayList底层是动态数组 `Object []`。

数组名字：elementDate，定义变量size（已有元素数量）。

## 1.2. 构造方法

三种构造方法，来初始化elementDate数组：
- 默认空参构造 `new ArrayList()`：
  
    `elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA`
- 指定容量的带参构造 `new ArrayList(int initialCapacity)`：可以按照指定的容量初始化数组
    - 容量非0：`elementData = new Object[capacity]`; 
    - 容量为0：`elementData = EMPTY_ELEMENTDATA`
- 将collection对象转换成数组 `new ArrayList(Collection<? Extends E> c)`

    collection对象转数组`.toArray()`。

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130148.png)

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130149.png)


## 1.3. 空参构造的添加数据

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130150.png)


- 空参构造：ArrayList的数组初始容量为0
- 第一次添加数据：扩容，初始化容量为10
- 第2到10次添加数据：不扩容。
- 第11次添加数据：扩容为15.

添加逻辑：
  - （扩容时机）确保数组容量够用：默认容量10和已有元素数量+1，选取最大值作为最小容量；判断最小容量与数组长度，不够则扩容。
  - 扩容规则：新数组的大小为，最小容量与原数组1.5倍中的最大值；检查是否超过最大容量；`Arrays.copyOf`拷贝老数组到新数组中。
  - 将新元素添加到位于size的位置上，size++。
  - 返回添加成功 true。

## 删除元素

```java
// 移除指定index位置的元素
public E remove(int index) {
    // 校验索引是否越界
    rangeCheck(index);

    modCount++;
    // 获取即将移除的元素值
    E oldValue = elementData(index);
    // 计算数组移动的长度
    int numMoved = size - index - 1;
    if (numMoved > 0)
        // 从移除数据的索引位置往后开始到末尾的这些数据往前移动
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    // 将最后一个位置元素置为null，数组长度size-1                     
    elementData[--size] = null; // clear to let GC do its work
    // 返回被移除的元素值
    return oldValue;
}
```

1. 获取即将移除的元素值
2. 计算数组移动的长度 `size - index - 1`
3. 使用 `System.arraycopy` 移动index后面的元素往前。
4. 将最后一个位置元素置为null，`size--`

# 2. 面试题

## JDK版本

JDK6 new 无参构造的 ArrayList 对象时，直接创建了长度是 10 的 Object[] 数组 elementData

## 2.1. ArrayList list=new ArrayList(10)中的list扩容几次

- ArrayList的数组指定了容量为 10，**未扩容**

## 2.2. 如何实现数组和List之间的转换

- 数组转List ，使用JDK中java.util.Arrays工具类的asList方法
- List转数组，使用List的toArray方法。无参toArray方法返回 Object数组，传入初始化长度的数组对象，返回该对象数组

## 2.3. 用Arrays.asList转List后，如果修改了数组内容，list受影响吗？List用toArray转数组后，如果修改了List内容，数组受影响吗

- 数组转List受影响，List转数组不受影响。
- asList受影响。第一，它的底层使用的Arrays类中的一个内部类ArrayList来构造的集合。第二，这个集合使用的还是传入的数组。
- toArray不会影响。toArray在底层是进行了数组的拷贝，跟原来的元素就没啥关系了。


##  2.4. ArrayList和LinkedList的区别是什么？

- 底层数据结构

  - ArrayList 是动态数组的数据结构实现

  - LinkedList 是双向链表的数据结构实现

- 操作数据效率
  - ArrayList按照下标查询的时间复杂度O(1)， LinkedList不支持下标查询
  - 查找（未知索引）： ArrayList需要遍历，链表也需要链表，时间复杂度都是O(n)
  - 新增和删除
    - ArrayList 只有**尾部**增删快O(1)
    - LinkedList **头尾**增删快O(1)
    - 两者在其他都需要遍历链表，时间复杂度是O(n)

- 内存空间占用

  - ArrayList底层是数组，内存连续，节省内存
  - LinkedList 是双向链表需要存储数据，和两个指针，更占用内存

- 线程安全
  - ArrayList和LinkedList都**不是线程安全的**
## ArrayList是线程安全的吗

不是。

添加元素时`size++`，删除元素时`size--`，由于++操作不具有原子性，两个线程可能会对同一索引位置进行操作，导致数据缺失。


## 2.5. ArrayList和LinkedList需要保证线程安全，有两种方案
- 因为局部变量是线程安全的，所以将其在方法内使用。
- 使用线程安全的ArrayList和LinkedList

通过Collections 的 synchronizedList 方法将 ArrayList、LinkedList 转换成线程安全的容器后再使用。
```java
List<Object> syn1 = Collections.synchronizedList(new ArrayList<>());
List<Object> syn2 = Collections.synchronizedList(new LinkedList<>());
```
LinkedList 换成`ConcurrentLinkedQueue`来使用