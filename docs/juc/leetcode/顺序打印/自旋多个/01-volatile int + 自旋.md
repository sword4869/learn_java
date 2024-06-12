
互相控制：

​	A方法控制B的count，B方法控制C的count。

顺序：

​	count的初值相同，但跳出while空转的起始阈值不同：第一个阈值是0，其他都是1.
# 只运行一次

```java
// 1114. 按序打印
class Foo {
    // volatile int count1 = 0;
    volatile int count2 = 0;
    volatile int count3 = 0;

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        // while (count1 != 0);
        printFirst.run();
        count2++;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (count2 != 1);
        printSecond.run();
        count3++;
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (count3 != 1);
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
    volatile int count1 = 0;
    volatile int count2 = 0;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // i
            while (count1 != i);
            printFoo.run();
            count2++;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // i+1
            while (count2 != i+1);
            printBar.run();
            count1++;
        }
    }
}
```

# 多个指定顺序

太复杂了，搞不定

```java
public void zero(IntConsumer printNumber) throws InterruptedException {
    	// 同时控制i和j
        for (int i = 0, j = 0; i < n || j < n; ) {
```

```java
for (int i = 0; i < n; i++) {
            if (i % 2 == 0)
                continue;
    		// count和i冲突
            while (count4 != i + 1)
                ;
    		// 打印和i冲突
            System.out.println(i + 1);
```

