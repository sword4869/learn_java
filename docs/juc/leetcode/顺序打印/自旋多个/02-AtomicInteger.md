相当于包装了下volatile int

# 只运行一次

```java
// 1114. 按序打印
class Foo {
    // AtomicInteger count1 = new AtomicInteger(0);
    AtomicInteger count2 = new AtomicInteger(0);
    AtomicInteger count3 = new AtomicInteger(0);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        count2.incrementAndGet();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while(count2.get() != 1);
        printSecond.run();
        count3.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while(count3.get() != 1);
        printThird.run();
    }
}
```

# 两个交替运行

超时

```java
// 1115. 交替打印 FooBar
class FooBar {
    private int n;
    AtomicInteger count1 = new AtomicInteger(0);
    AtomicInteger count2 = new AtomicInteger(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // i
            while (count1.get() != i);
            printFoo.run();
            count2.incrementAndGet();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // i+1
            while (count2.get() != i+1);
            printBar.run();
            count1.incrementAndGet();
        }
    }
}
```