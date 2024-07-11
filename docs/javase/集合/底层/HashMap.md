
# 红黑树

性质1：节点要么是**红色**,要么是**黑色**

性质2：根节点是**黑色**

性质3：叶子节点都是黑色的空节点

性质4：红黑树中红色节点的子节点都是黑色

性质5：从任一节点到叶子节点的所有路径都包含相同数目的黑色节点

**在添加或删除节点的时候，如果不符合这些性质会发生旋转，以达到所有的性质，保证红黑树的平衡**

（3）红黑树的复杂度

增删查的时间复杂度都为O(log n)

- 添加：
  - 添加先要从根节点开始找到元素添加的位置，时间复杂度O(log n)
  - 添加完成后涉及到复杂度为O(1)的旋转调整操作
  - 故整体复杂度为：O(log n)

- 删除：
  - 首先从根节点开始找到被删除元素的位置，时间复杂度O(log n)
  - 删除完成后涉及到复杂度为O(1)的旋转调整操作
  - 故整体复杂度为：O(log n)

### 散列表

在HashMap中的最重要的一个数据结构就是散列表，在散列表中又使用到了红黑树和链表

#### 散列表（Hash Table）概述

根据键（Key）直接访问在内存存储位置值（Value）的数据结构

将键(key)映射为数组下标，通过散列函数 hashValue = hash(key)

#### 散列函数和散列冲突

散列函数的基本要求：

- 散列函数计算得到的散列值必须是大于等于0的正整数，因为hashValue需要作为数组的下标。

- 如果key1==key2，那么经过hash后得到的哈希值也必相同即：hash(key1) == hash(key2）

散列冲突(或者哈希冲突，哈希碰撞，**就是指多个key映射到同一个数组下标位置**)


#### 散列冲突-链表法（拉链）

在散列表中，数组的每个下标位置我们可以称之为桶（bucket）或者槽（slot），每个桶(槽)会对应一条链表，所有散列值相同的元素我们都放到相同槽位对应的链表中。

#### 时间复杂度-散列表

1，插入操作，通过散列函数计算出对应的散列槽位，将其插入到对应链表中即可，插入的时间复杂度是 O(1)

2，当查找、删除一个元素时，我们同样通过散列函数计算出对应的槽，然后遍历链表查找或者删除

- 平均情况下基于链表法解决冲突时查询的时间复杂度是O(1)

- 散列表可能会退化为链表,查询的时间复杂度就从 O(1) 退化为 O(n)

- 将链表法中的链表改造为其他高效的动态数据结构，比如红黑树，查询的时间复杂度是 O(logn)

将链表法中的链表改造红黑树还有一个非常重要的原因，可以防止DDos攻击

# HashMap底层数据结构

底层使用hash表数据结构。HashMap内部维护了一个Entry数组，用于存储键值对对象Entry。计算key的hash值确定元素在数组中的下标，去这个位置看没有发生哈希冲突（key相同是没有，key不同则冲突），没有冲突则更新value，冲突则存入链表或红黑树中。

具体是Java7是数组+链表，Java8是数组+（链表 | 红黑树）。

# 红黑树和链表的转换

- 添加元素时，当**链表长度大于8并且数组长度达到64**时，将链表转化为红黑树，以减少搜索时间。
- 扩容 resize时，红黑树拆分成的树的结点数小于等于临界值6个，则退化成链表

# JDK1.8

## hashMap常见属性

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130819.png)

## HashMap的put方法的具体流程吗？

1. 判断键值对数组table是否为空或为null，否则执行resize()进行扩容（初始化）

2. 根据键值key计算hash值得到数组索引

3. 判断table[i]==null，是null则直接新建节点添加

4. 如果table[i]==null , 非null

   4.1 判断table[i]的首个元素是否和key一样，如果相同直接覆盖value

   4.2 判断table[i] 是否为treeNode，即table[i] 是否是红黑树，如果是红黑树，则直接在树中插入键值对

   4.3 遍历table[i]，链表的尾部插入数据。然后判断链表长度是否大于8，大于8的话把链表转换为红黑树，在红黑树中执行插入操作，遍历过程中若发现key已经存在直接覆盖value

5. 插入成功后，判断实际存在的键值对数量size是否超多了最大容量threshold（数组长度*0.75），如果超过，进行扩容。


![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130820.png)

- HashMap是懒惰加载，在创建对象时并没有初始化数组

- 在无参的构造函数中，设置了默认的加载因子是0.75

添加数据流程图

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130821.png)

具体的源码：

```java
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}

final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    //判断数组是否未初始化
    if ((tab = table) == null || (n = tab.length) == 0)
        //如果未初始化，调用resize方法 进行初始化
        n = (tab = resize()).length;
    //通过 & 运算求出该数据（key）的数组下标并判断该下标位置是否有数据
    if ((p = tab[i = (n - 1) & hash]) == null)
        //如果没有，直接将数据放在该下标位置
        tab[i] = newNode(hash, key, value, null);
    //该数组下标有数据的情况
    else {
        Node<K,V> e; K k;
        //判断该位置数据的key和新来的数据是否一样
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            //如果一样，证明为修改操作，该节点的数据赋值给e,后边会用到
            e = p;
        //判断是不是红黑树
        else if (p instanceof TreeNode)
            //如果是红黑树的话，进行红黑树的操作
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        //新数据和当前数组既不相同，也不是红黑树节点，证明是链表
        else {
            //遍历链表
            for (int binCount = 0; ; ++binCount) {
                //判断next节点，如果为空的话，证明遍历到链表尾部了
                if ((e = p.next) == null) {
                    //把新值放入链表尾部
                    p.next = newNode(hash, key, value, null);
                    //因为新插入了一条数据，所以判断链表长度是不是大于等于8
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        //如果是，进行转换红黑树操作
                        treeifyBin(tab, hash);
                    break;
                }
                //判断链表当中有数据相同的值，如果一样，证明为修改操作
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                //把下一个节点赋值为当前节点
                p = e;
            }
        }
        //判断e是否为空（e值为修改操作存放原数据的变量）
        if (e != null) { // existing mapping for key
            //不为空的话证明是修改操作，取出老值
            V oldValue = e.value;
            //一定会执行  onlyIfAbsent传进来的是false
            if (!onlyIfAbsent || oldValue == null)
                //将新值赋值当前节点
                e.value = value;
            afterNodeAccess(e);
            //返回老值
            return oldValue;
        }
    }
    //计数器，计算当前节点的修改次数
    ++modCount;
    //当前数组中的数据数量如果大于扩容阈值
    if (++size > threshold)
        //进行扩容操作
        resize();
    //空方法
    afterNodeInsertion(evict);
    //添加操作时 返回空值
    return null;
}
```



## HashMap的扩容机制吗？

- 在**添加元素或初始化**的时候需要调用resize方法进行扩容，第一次添加数据初始化数组长度为16，以后每次每次扩容都是达到了**扩容阈值（数组长度 * 0.75）**

- 每次扩容的时候，创建一个新的数组，**其容量是原数组的两倍**。

- 需要把老数组中的数据挪动到新的数组中
  - 没有hash冲突的节点，则直接使用 e.hash & (newCap - 1) 计算新数组的索引位置
  - 如果是红黑树，走红黑树的添加
  - 如果是链表，则需要遍历链表，可能需要拆分链表，判断(e.hash & oldCap)是否为0，该元素的位置要么停留在原始位置，要么移动到原始位置+增加的数组大小这个位置上

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130822.png)

扩容的流程：

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130823.png)

源码：

```java
//扩容、初始化数组
final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
    	//如果当前数组为null的时候，把oldCap老数组容量设置为0
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        //老的扩容阈值
    	int oldThr = threshold;
        int newCap, newThr = 0;
        //判断数组容量是否大于0，大于0说明数组已经初始化
    	if (oldCap > 0) {
            //判断当前数组长度是否大于最大数组长度
            if (oldCap >= MAXIMUM_CAPACITY) {
                //如果是，将扩容阈值直接设置为int类型的最大数值并直接返回
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            //如果在最大长度范围内，则需要扩容  OldCap << 1等价于oldCap*2
            //运算过后判断是不是最大值并且oldCap需要大于16
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold  等价于oldThr*2
        }
    	//如果oldCap<0，但是已经初始化了，像把元素删除完之后的情况，那么它的临界值肯定还存在，       			如果是首次初始化，它的临界值则为0
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        //数组未初始化的情况，将阈值和扩容因子都设置为默认值
    	else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
    	//初始化容量小于16的时候，扩容阈值是没有赋值的
        if (newThr == 0) {
            //创建阈值
            float ft = (float)newCap * loadFactor;
            //判断新容量和新阈值是否大于最大容量
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
    	//计算出来的阈值赋值
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
        //根据上边计算得出的容量 创建新的数组       
    	Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    	//赋值
    	table = newTab;
    	//扩容操作，判断不为空证明不是初始化数组
        if (oldTab != null) {
            //遍历数组
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                //判断当前下标为j的数组如果不为空的话赋值个e，进行下一步操作
                if ((e = oldTab[j]) != null) {
                    //将数组位置置空
                    oldTab[j] = null;
                    //判断是否有下个节点
                    if (e.next == null)
                        //如果没有，就重新计算在新数组中的下标并放进去
                        newTab[e.hash & (newCap - 1)] = e;
                   	//有下个节点的情况，并且判断是否已经树化
                    else if (e instanceof TreeNode)
                        //进行红黑树的操作
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    //有下个节点的情况，并且没有树化（链表形式）
                    else {
                        //比如老数组容量是16，那下标就为0-15
                        //扩容操作*2，容量就变为32，下标为0-31
                        //低位：0-15，高位16-31
                        //定义了四个变量
                        //        低位头          低位尾
                        Node<K,V> loHead = null, loTail = null;
                        //        高位头		   高位尾
                        Node<K,V> hiHead = null, hiTail = null;
                        //下个节点
                        Node<K,V> next;
                        //循环遍历
                        do {
                            //取出next节点
                            next = e.next;
                            //通过 与操作 计算得出结果为0
                            if ((e.hash & oldCap) == 0) {
                                //如果低位尾为null，证明当前数组位置为空，没有任何数据
                                if (loTail == null)
                                    //将e值放入低位头
                                    loHead = e;
                                //低位尾不为null，证明已经有数据了
                                else
                                    //将数据放入next节点
                                    loTail.next = e;
                                //记录低位尾数据
                                loTail = e;
                            }
                            //通过 与操作 计算得出结果不为0
                            else {
                                 //如果高位尾为null，证明当前数组位置为空，没有任何数据
                                if (hiTail == null)
                                    //将e值放入高位头
                                    hiHead = e;
                                //高位尾不为null，证明已经有数据了
                                else
                                    //将数据放入next节点
                                    hiTail.next = e;
                               //记录高位尾数据
                               	hiTail = e;
                            }
                            
                        } 
                        //如果e不为空，证明没有到链表尾部，继续执行循环
                        while ((e = next) != null);
                        //低位尾如果记录的有数据，是链表
                        if (loTail != null) {
                            //将下一个元素置空
                            loTail.next = null;
                            //将低位头放入新数组的原下标位置
                            newTab[j] = loHead;
                        }
                        //高位尾如果记录的有数据，是链表
                        if (hiTail != null) {
                            //将下一个元素置空
                            hiTail.next = null;
                            //将高位头放入新数组的(原下标+原数组容量)位置
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
    	//返回新的数组对象
        return newTab;
    }
```
## 通过hash计算后找到数组的下标，是如何找到的呢，你了解hashMap的寻址算法吗？
![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130824.png)

- key是null，hash值就是0
- 非null。首先计算出key的hashCode值， 然后通过这个hash值**右移16位按位异或**，得到最后的hash值。主要作用就是使原来的hash值更加均匀，减少hash冲突


![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130825.png)

在putValue的方法中，计算数组下标，没有直接采用取模的方式，而是使用了`hash &(数组长度-1)`。

## 为何HashMap的数组长度一定是2的次幂？

1.  计算索引时效率更高：如果是 2 的 n 次幂可以使用位与运算代替取模`hash &(数组长度-1)`

2.  扩容时重新计算索引效率更高： hash & oldCap == 0 的元素留在原来位置 ，否则新位置 = 旧位置 + oldCap

## 为何HashMap的数组长度一定是2的次幂？
- 计算索引时效率更高：如果是 2 的 n 次幂可以使用位与运算代替取模
- 扩容时重新计算索引效率更高：在进行扩容是会进行判断 hash值按位与运算旧数组长租是否 == 0。如果等于0，则把元素留在原来位置 ，否则新位置是等于旧位置的下标+旧数组长度


## hashmap是线程安全的吗？想要使用线程安全的map该怎么做呢？

不是线程安全的

可以采用ConcurrentHashMap进行使用，它是一个线程安全的HashMap

## HashSet与HashMap的区别？

HashSet实现了Set接口, 仅存储对象; HashMap实现了 Map接口, 存储的是键值对.

HashSet底层其实是用HashMap实现存储的。

- 依靠HashMap来存储元素值，(利用hashMap的key键进行存储)，而value值默认为Object对象. 
- HashSet也不允许出现重复值，判断标准和HashMap断标准相同，两个元素的hashCode相等并且通过equals()方法返回true.

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130826.png)


## HashTable与HashMap的区别

| **区别**       | **HashTable**                  | **HashMap**      |
| -------------- | ------------------------------ | ---------------- |
| 数据结构       | 数组+链表                      | 数组+链表+红黑树 |
| 是否可以为null | Key和value都不能为null         | 可以为null       |
| hash算法       | key的hashCode()                | 二次hash         |
| 扩容方式       | 当前容量翻倍 +1                | 当前容量翻倍     |
| 线程安全       | 同步(synchronized)的，线程安全 | 非线程安全       |

在实际开中不建议使用HashTable，在多线程环境下可以使用ConcurrentHashMap类

# JDK7

## HashMap的jdk1.7和jdk1.8有什么区别

JDK1.8之前采用的数组+链表，即拉链法。链表是头插法

JDK1.8之后采用数组+链表+红黑树，链表是尾插法

## hashmap在1.7情况下的多线程死循环问题吗？

HashMap JDK 1.7 的数据结构是：数组+链表，链表使用**头插法**。**头插法会反转链表顺序**。

**头插法 + 多线程并发操作 + HashMap 扩容**，这几个点加在一起就形成了 HashMap 的死循环。

所以，JDK 8 使用**尾插法**，保持与扩容前一样的顺序，就避免了jdk7中死循环的问题。

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130827.png)

<details>

<summary>具体复现过程</summary>

```java
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;


/**
 * 测试在JDK 1.7中 HashMap出现死循环
 */
public class Main {
    static class TestObject{
        String key;

        public TestObject(String key){
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestObject that = (TestObject) o;
            return Objects.equals(key, that.key);
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

    /**
     * 这个map 桶的长度为2，当元素个数达到  2 * 1.5 = 3 的时候才会触发扩容
     */
    private static HashMap<TestObject, String> map = new HashMap<>(2, 1.5f);

    public static void main(String[] args) throws IOException, InterruptedException {
        map.put(new TestObject("A"), "A");
        map.put(new TestObject("B"), "B");
        map.put(new TestObject("C"), "C");
        System.out.println("此时元素已经达到3了，再往里面添加就会产生扩容操作：" + map);
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread("T1") {
            public void run() {
                map.put(new TestObject("D"), "D");
                System.out.println(Thread.currentThread().getName() + "扩容完毕");
                countDownLatch.countDown();
            }
        }.start();
        new Thread("T2") {
            public void run() {
                map.put(new TestObject("E"), "E");
                System.out.println(Thread.currentThread().getName() + "扩容完毕 ");
                countDownLatch.countDown();
            }
        }.start();

        countDownLatch.await();

        // 死循环后打印直接OOM，思考一下为什么？
        System.out.println(map);
        /*
        此时元素已经达到3了，再往里面添加就会产生扩容操作：{Main$TestObject@1=C, Main$TestObject@1=B, Main$TestObject@1=A}
        T1扩容完毕 
        T2扩容完毕 
        Exception in thread "T2" java.lang.OutOfMemoryError: Java heap space
            at java.util.Arrays.copyOf(Arrays.java:2367)
            at java.lang.AbstractStringBuilder.expandCapacity(AbstractStringBuilder.java:130)
            at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:114)
            at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:415)
            at java.lang.StringBuilder.append(StringBuilder.java:132)
            at java.lang.StringBuilder.append(StringBuilder.java:128)
            at java.util.AbstractMap.toString(AbstractMap.java:521)
            at java.lang.String.valueOf(String.java:2849)
            at java.lang.StringBuilder.append(StringBuilder.java:128)
            at Main$2.run(Main.java:53)
         */
    }
}
```

1. 扩容`resize()`中的`transfer()`转移老数组到新数组部分，在已经获取`next`后面打线程级断点。
2. 线程T1运行完毕后，我们再去运行线程T2.
    - 扩容前是 `C->B->A` ，T1和T2在`if(rehash)`处暂停，`e`和`next`都分别指向`C`和`B`。
    - 先运行线程T1，线程T1运行完毕后的结果是 `A->B->C` 。
    - 切换到T2线程继续运行，T2此时，`e` 指向 `C`，`next` 指向 `B`。
    - 第一轮while，将 `C` 插入到新数组，`A->B->[C->null]`（注意 `newTable` 是局部变量，所以 `newTable` 是空的）。
    - 第二轮while，现在 e 是 `B`，next 是 `C`。将 `B` 插入到新数组，`A->[B->C->null]`.
    - 第三轮while产生死循环，现在 e `C`, next 是 `null`, `e.next = newTable[i]`不再是插入新数组，而是让C指向了B，形成了死循环。next是null，下一轮while循环直接退出。
3. 线程T1、T2加入元素完毕后，main线程打印map陷入死循环。

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130828.png)

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130829.png)

```
Thread.currentThread().getName().equals("T1")||Thread.currentThread().getName().equals("T2")
```

> 线程T1运行完毕后

![线程T1运行完毕后](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130830.png)

> 注意newTable是局部变量，所有新数组是没问题的

![注意newTable是局部变量，所有新数组是没问题的](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130831.png)

> 第三次while循环，产生死循环

![死循环](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130832.png)

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130833.png)

> 退出transfer后，原来T1的新数组被T2的新数组所覆盖

![退出transfer后，原来T1的新数组被T2的新数组所覆盖](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130834.png)

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112130835.png)

<details/>
