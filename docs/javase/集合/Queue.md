- [1. Queue接口](#1-queue接口)
- [2. Deque接口](#2-deque接口)
  - [2.1. LinkedList](#21-linkedlist)
  - [2.2. ArrayDeque](#22-arraydeque)
- [3. PriorityQueue](#3-priorityqueue)

---
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