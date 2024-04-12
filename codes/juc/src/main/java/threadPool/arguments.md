```
corePoolSize：   >=0.
maximumPoolSize：>0（保证线程池起码会有线程），且maximumPoolSize >= corePoolSize
keepAliveTime：  >=0
```
## 当核心线程0，空闲线程2，阻塞队列2时
```java
for (int i = 0; i < 8; i++) {
    pool.submit(new MyRunnable());
    Thread.sleep(1);                // <<<<<<<<<<<<<<<<<<<
}
```

不加这句话，执行4个任务：阻塞队列满了，要创建两个空闲线程。因为提交太快了，空闲线程才刚创建了一个，除了一个任务被分配给这个空闲线程外，其余全部拒绝。剩下三个就是阻塞队列中的任务。
```java
/*
pool-1-thread-1threadPool.MyRunnable@38af3868       // 从阻塞队列拿出的任务
pool-1-thread-2threadPool.MyRunnable@6da6fc34       // 新提交的任务
finished submission...
3 [java.util.concurrent.FutureTask@77459877[Not completed, task = java.util.concurrent.Executors$RunnableAdapter@6b884d57[Wrapped task = threadPool.MyRunnable@38af3868]], 
java.util.concurrent.FutureTask@33c7353a[Not completed, task = java.util.concurrent.Executors$RunnableAdapter@5b2133b1[Wrapped task = threadPool.MyRunnable@72ea2f77]], 
java.util.concurrent.FutureTask@19469ea2[Not completed, task = java.util.concurrent.Executors$RunnableAdapter@681a9515[Wrapped task = threadPool.MyRunnable@3af49f1c]]]
2 [java.util.concurrent.FutureTask@33c7353a[Not completed, task = java.util.concurrent.Executors$RunnableAdapter@5b2133b1[Wrapped task = threadPool.MyRunnable@72ea2f77]], 
java.util.concurrent.FutureTask@19469ea2[Not completed, task = java.util.concurrent.Executors$RunnableAdapter@681a9515[Wrapped task = threadPool.MyRunnable@3af49f1c]]]
pool-1-thread-2
pool-1-thread-1
0
*/
```
加入这句话，执行5个任务：阻塞队列满了，创建两个空闲线程。因为提交慢一些，新提交的任务被分配给已经创建好的2个空闲线程，其他的任务看都满着而被拒绝，然后空闲线程再消耗完阻塞队列里的3个任务。
```java
/*
pool-1-thread-1
pool-1-thread-2
finished submission...
3
pool-1-thread-1
2
pool-1-thread-2
1
pool-1-thread-1
*/
```