

## 1. Queue接口
- 入队：offer/add(e)
- 出队：poll/remove()。空时poll弹null，remove抛异常。
- 查看栈顶：peek/element()。同上。
## 2. Deque接口
- 入队：offer/add/**push**(e), **offerFirst**/**addFirst**(e), **offerLast**/**addLast**(e)
- 出队：poll/remove/**pop**(), **pollFirst**/**removeFirst**(e), **pollLast**/**removerLast**(e)
- 查看栈顶：peak/element(), **peakFirst**/**getFirst**(), **peakLast**/**getLast**()
- 其他：**size**(), **isEmpty**(), **contains**(e)
### 2.1. LinkedList

### 2.2. ArrayDeque

`Queue<String> q = new ArrayDeque<>();`

## 3. PriorityQueue
默认，队列头是最小的元素。

自定义类型，则需要实现`Comparable`接口的`compareTo`方法。

```java
PriorityQueue<int[]> edges = new PriorityQueue<>((a, b) -> a[2] - b[2]);
```
```java
PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
pq.add(1);
pq.add(2);
pq.add(3);
System.out.println(pq.poll());
System.out.println(pq.poll());
System.out.println(pq.poll());
// 3 2 1
```
```java
PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparing(Integer::intValue).reversed());
```