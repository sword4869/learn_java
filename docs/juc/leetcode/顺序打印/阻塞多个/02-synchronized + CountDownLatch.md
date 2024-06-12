
CountDownLatch是用来保证最初的执行顺序，交替是靠object的wait和notify。


# 只运行一次

`latch1.countDown();`会让count--，但减到0后再减就不减了，但也不会阻塞。

`latch1.await();`是等待为0才执行。
```java
// 1114. 按序打印
class Foo {
    CountDownLatch latchDone1 = new CountDownLatch(1);
    CountDownLatch latchDone2 = new CountDownLatch(1);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        latchDone1.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        latchDone1.await();
        printSecond.run();
        latchDone2.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        latchDone2.await();
        printThird.run();
    }
}
```

# 两个交替运行

leetcode平台执行juc有问题，去掉CountDownLatch都行。

```java
// 1115. 交替打印 FooBar
class FooBar {
    private int n;
    CountDownLatch latchDone1 = new CountDownLatch(1);

    public FooBar(int n) {
        this.n = n;
    }

    public synchronized void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            printFoo.run();
            latchDone1.countDown();
            notify();
            wait();
        }
        notify();   // 最后一次唤醒，线程B还留在最后一次for中的wait，确保程序能够退出
    }

    public synchronized void bar(Runnable printBar) throws InterruptedException {
        latchDone1.await();
        for (int i = 0; i < n; i++) {
            printBar.run();
            notify();
            wait();
        }
    }
}
```