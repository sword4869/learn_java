- [1. List接口](#1-list接口)
  - [1.1. 遍历](#11-遍历)
- [2. ArrayList类](#2-arraylist类)
- [3. LinkedList类](#3-linkedlist类)
- [4. Stack类](#4-stack类)


---

## 1. List接口


索引操作
- 添加：add(e), **add(i, e)**
- 删除：remove(e), **remove(i, e)**
- 获取：**get(i)**
- 更新：**set(i, e)**
- 其他：clear(), contains(e), isEmpty(), size()

```java
// 1.添加元素
// boolean Collection.add(E e)  将指定的元素添加到此集合的尾部
list.add("aaa");
list.add("ccc");
// void List.add(int index, E element)  将指定的元素插入此列表中的指定位置
// 细节：原来索引上的元素会依次往后移
list.add(1, "bbb");


// 2.删除元素
// boolean Collection.remove(Object o)  删除指定的元素，返回删除是否成功
boolean remove1 = list.remove("ccc");
System.out.println(remove1); //true
// E List.remove(int index)   删除指定索引处的元素，返回被删除的元素
String remove = list.remove(0);
System.out.println(remove); //aaa


// 3.修改元素
// E List.set(int index,E element)  修改指定索引处的元素，返回被修改的元素原本的值
String result = list.set(0, "QQQ");
System.out.println(result);

// 4. 获取元素
// E List.get(int index)  返回指定索引处的元素
String s = list.get(0);
System.out.println(s);
```

> `list.remove(1)` 删除的是1这个元素。还是1索引上的元素 ? 

删除的是1索引。因为在调用方法的时候。如果方法出现了重载现象优先调用实参跟形参类型一致的那个方法。`1` 是 `int`

```java
// boolean Collection.remove(Object o)
// E List.remove(int index)

// 此时 remove 方法是不会自动装箱的。所以对应int index
list.remove(1);

// 手动装箱。所以对应 Object o
Integer i = Integer.valueOf(1);
list.remove(i);
```

### 1.1. 遍历
List系列集合的五种遍历方式：
1. 迭代器：删除
2. 增强for：仅仅遍历
3. Lambda表达式：仅仅遍历
4. 普通for循环：操作索引
5. 列表迭代器：添加和删除

```java
List<String> list = new ArrayList<>();
list.add("aaa");
list.add("bbb");
list.add("ccc");

// 1.迭代器
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String str = it.next();
    System.out.println(str);
}

// 2.增强for
for (String s : list) {
    System.out.println(s);
}

// 3.Lambda表达式
list.forEach(s -> System.out.println(s));

// 4.普通for循环
// size方法跟get方法还有循环结合的方式，利用索引获取到集合中的每一个元素
for (int i = 0; i < list.size(); i++) {
    // i:依次表示集合中的每一个索引
    String s = list.get(i);
    System.out.println(s);
}

// 5.列表迭代器
// 获取一个列表迭代器的对象，里面的指针默认也是指向0索引的
// 额外添加了一个方法：在遍历的过程中，可以添加元素
ListIterator<String> list_it = list.listIterator();
while (list_it.hasNext()) {
    String str = list_it.next();
    if ("aaa".equals(str)) {
        // 删除当前已获取的元素（而不是指针指向的元素）
        list_it.remove();
    }
    if ("bbb".equals(str)) {
        // 在指针指向的位置添加元素（当前元素的后面）
        list_it.add("qqq");
    }
}
```

## 2. ArrayList类

同List
- 添加：add(e), add(i, e)
- 删除：remove(e), remove(i, e)
- 获取：get(i)
- 更新：set(i, e)
```java
// ArrayList(Collection<? extends E> c)
ArrayList<String> a = new ArrayList<>(m.values());
```


## 3. LinkedList类

- 添加头：push(e), **addFirst()**
- 添加尾：add/offer(e), **addLast()**
- 删除：poll/pop/remove(), remove(i, e), **removeFirst()**, **removeLast()**
- 获取：get(i), **getFirst()**, **getLast()**
- 更新：set(i, e)

还实现了Deque接口。

![alt text](../../../images/image-16.png)

注意：是栈，则一定是`push`，而不是`add`

```java
/* 栈 */
LinkedList<Integer> stack = new LinkedList<>();
stack.push(12);
stack.push(13);
stack.push(14);
System.out.println(stack.pop()); // 14
System.out.println(stack.poll()); // 13
System.out.println(stack.remove()); // 12

/* 队列 */
LinkedList<Integer> queue = new LinkedList<>();
queue.offer(12);
queue.add(13);
System.out.println(queue.pop()); // 12
// poll……remove……
```
## 4. Stack类
- 添加：**push**/add(e)
- 删除：**pop**
- 获取：**peek**()
- 其他：clear(), contains(e), isEmpty(), **empty**(), size()