


# 只运行一次

```java
// 1114. 按序打印
class Foo {
    AtomicInteger count = new AtomicInteger(0);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        count.incrementAndGet();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while(count.get() != 1);
        printSecond.run();
        count.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while(count.get() != 2);
        printThird.run();
    }
}
```
# 两个交替运行

```java
// 1115. 交替打印 FooBar
class FooBar {
    private int n;
    AtomicInteger count = new AtomicInteger(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (count.get() % 2 != 0);
            printFoo.run();
            count.incrementAndGet();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (count.get() % 2 != 1);
            printBar.run();
            count.incrementAndGet();
        }
    }
}
```