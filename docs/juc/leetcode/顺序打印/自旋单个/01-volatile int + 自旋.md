

通过取余来控制，不同的余数对应不同的方法

​	volatile 修饰的 int count

​	自旋

​		`while(count % total != target)`的空转

​		每个执行完会`count++`

# 只运行一次

可用简化为每个count值对应一个方法

```java
// 1114. 按序打印
class Foo {
    volatile int count=0;

    public Foo() {
        
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        count++;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // while(count % 3 != 1); 
        while (count!=1);
        printSecond.run();
        count++;
    }

    public void third(Runnable printThird) throws InterruptedException {
        // while(count % 3 != 2); 
        while (count!=2);
        printThird.run();
    }
}
```

# 两个交替运行

```java
// 1115. 交替打印 FooBar
class FooBar {
    private int n;
    volatile int count = 0;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while(count % 2 != 0);            
        	printFoo.run();
            count++;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while(count % 2 != 1);            
        	printBar.run();
            count++;
        }
    }
}
```

# 多个指定顺序

顺序就是ABAC

A(0)B(1)A(2)C(3)

```java
// 1116. 打印零与奇偶数
class ZeroEvenOdd {
    private int n;
    volatile int count = 0;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (count % 4 != 0 && count % 4 != 2);
            printNumber.accept(0);
            count++;
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // index 是 0, 2; 打印是 0+1, 2+1
            if (i % 2 == 1)
                continue;
            while (count % 4 != 1);
            printNumber.accept(i + 1);
            count++;
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // index 是 1, 3; 打印是 1+1, 3+1
            if (i % 2 == 0)
                continue;
            while (count % 4 != 3);
            printNumber.accept(i + 1);
            count++;
        }
    }
}
```

