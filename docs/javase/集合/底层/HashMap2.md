
## 2. HashMap集合

HashMap底层是哈希表结构的

注意：
- 依赖hashCode方法和equals方法保证**键的唯一**
- 如果键要存储的是自定义对象，需要重写hashCode和equals方法
![Alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112132159.png)

### 2.1. 底层

```java
/*
- Node<K,V>[] table 哈希表结构中数组的名字
- DEFAULT_INITIAL_CAPACITY： 数组默认长度16
- DEFAULT_LOAD_FACTOR： 默认加载因子0.75


HashMap里面每一个对象包含以下内容：
1.1 链表中的键值对对象
    int hash;         //键的哈希值
    final K key;      //键
    V value;          //值
    Node<K,V> next;   //下一个节点的地址值
            
            
1.2 红黑树中的键值对对象
    int hash;         		//键的哈希值
    final K key;      		//键
    V value;         	 	//值
    TreeNode<K,V> parent;  	//父节点的地址值
    TreeNode<K,V> left;		//左子节点的地址值
    TreeNode<K,V> right;	//右子节点的地址值
    boolean red;			//节点的颜色
*/

HashMap<String,Integer> hm = new HashMap<>(); // 还没有创建数组
hm.put("aaa" , 111); // put时才创建数组
hm.put("bbb" , 222);
hm.put("ccc" , 333);
hm.put("ccc" , 444);

/*
* 添加元素的时候至少考虑三种情况：
* 1. 数组位置为null
* 2. 数组位置不为null，键不重复，挂在下面形成链表或者红黑树
* 3. 数组位置不为null，键重复，元素覆盖
*/

// 参数一：键
// 参数二：值
// 返回值：被覆盖元素的值，如果没有覆盖，返回null
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}

// 利用键计算出对应的哈希值 hashCode，再把哈希值进行一些额外的处理
// 简单理解：返回值就是返回键的哈希值
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

// 参数一：键的哈希值
// 参数二：键
// 参数三：值
// 参数四：如果键重复了是否保留
//      true，表示老元素的值保留，不会覆盖
//      false，表示老元素的值不保留，会进行覆盖
// 参数五：是否需要扩容
final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
    // 定义一个局部变量，用来记录哈希表中数组的地址值。
    // 意义是不用每次都去堆中取，在栈中处理，效率更高
    Node<K, V>[] tab;
    // 把 HashMap 中数组的地址值，赋值给局部变量tab
    tab = table;

    // 临时的第三方变量，用来记录键值对对象的地址值
    Node<K, V> p;

    // 表示当前数组的长度
    int n;
    n = tab.length;

    // 表示索引
    int i;


    if (tab == null || n == 0) {
        // resize 方法的作用：
        // 1.如果当前是第一次添加数据，底层会创建一个默认长度为16，加载因子为0.75的数组
        // 2.如果不是第一次添加数据，会看数组中的元素是否达到了扩容的条件
        //      如果没有达到扩容条件，底层不会做任何操作
        //      如果达到了扩容条件，底层会把数组扩容为原先的两倍，并把数据全部转移到新的哈希表中
        tab = resize();
        // 表示把当前数组的长度赋值给n
        n = tab.length;
    }

    // 获取当前键值对对象在数组中应存入的位置：拿着数组的长度跟键的哈希值进行计算
    i = (n - 1) & hash;
    // 获取数组中对应元素的数据
    p = tab[i];

    // 情况一：数组位置为null
    if (p == null) {
        // 底层会创建一个键值对对象，直接放到数组当中
        tab[i] = newNode(hash, key, value, null);
    } else {
        Node<K, V> e;
        K k;

        // ① 键的hash值相同：数组中键值对的哈希值与当前要添加键值对的哈希值相同
        // ② 键相同：考虑到hash值相同，但是键不同的情况，所以比较键是否相同（基本数据类型直接比较，引用数据类型调用equals方法）
        if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
            e = p;
        } else if (p instanceof TreeNode) {
            // 情况二：数组位置不为null，键不重复，红黑树
            // 判断数组中获取出来的键值对是不是红黑树中的节点
            // 如果是，则调用方法putTreeVal，把当前的节点按照红黑树的规则添加到树当中。
            // 树中重复返回非null，不重复返回null
            e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
        } else {
            // 情况二和三：数组位置不为null，链表
            for (int binCount = 0;; ++binCount) {
                // 情况二：数组位置不为null，键不重复，红黑树
                // 进入if，表示 e 为 null，键不重复，创建一个新的节点
                if ((e = p.next) == null) {
                    // 此时就会创建一个新的节点，挂在下面形成链表
                    p.next = newNode(hash, key, value, null);
                    // 判断当前链表长度是否超过8，如果超过8，就会调用方法treeifyBin
                    // treeifyBin方法的底层还会继续判断
                    //      判断数组的长度是否大于等于64
                    //      如果同时满足这两个条件，就会把这个链表转成红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1)
                        treeifyBin(tab, hash);
                    break;
                }
                // 情况三：数组位置不为null，键重复，元素覆盖
                // e：链表中键重复的键和hash值相同的键值对对象
                // 直接break，e 不为 null
                if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                    break;
                }
                // 更新p的值，继续遍历链表
                p = e;
            }
        }

        // 情况三：元素覆盖，数组中键值对的个数不变，返回被覆盖元素的值
        // 如果e为null，表示当前不需要覆盖任何元素
        // 如果e不为null，表示当前的键是一样的，值会被覆盖
        if (e != null) {
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null) {
                e.value = value;
            }
            afterNodeAccess(e);
            // 返回被覆盖元素的值
            return oldValue;
        }
    }

    // 情况一和二：数组中键值对的个数加1
    // threshold：记录的就是数组的长度 * 0.75，哈希表的扩容时机 16 * 0.75 = 12
    if (++size > threshold) {
        resize();
    }

    // 表示当前没有覆盖任何元素，返回null
    return null;
}
```