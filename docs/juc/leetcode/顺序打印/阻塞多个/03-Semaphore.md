让第一个执行的Semaphore是1，其他都是0.

先消费acquire当前的信号量，再生产release下一个信号量。

# 只运行一次

Semaphore是acquire消耗1个，release增加1个。

```java
// 1114. 按序打印
class Foo {
    // Semaphore s1 = new Semaphore(1);
    Semaphore s2 = new Semaphore(0);
    Semaphore s3 = new Semaphore(0);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        // s1.acquire();
        printFirst.run();
        s2.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        s2.acquire();
        printSecond.run();
        s3.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        s3.acquire();
        printThird.run();
    }
}
```

# 两个交替运行

```java
// 1115. 交替打印 FooBar
class FooBar {
    private int n;
    Semaphore s1 = new Semaphore(1);
    Semaphore s2 = new Semaphore(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            s1.acquire();
            printFoo.run();
            s2.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            s2.acquire();
            printBar.run();
            s1.release();
        }
    }
}
```