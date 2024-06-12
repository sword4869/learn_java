
交替：

​	检测时，检测上一个flag是否完成。

​	执行时，当前的flagDone完成，并且上一个flag归零（表示消费了）。

使用flag来控制睡眠条件，选择while而不是if是因为first会唤醒所有，就可能被唤醒多次。


PS：为什么flag不需要 volatile

# 只运行一次


```java
// 1114. 按序打印
// synchronized object 
class Foo {
    boolean firstDone = false;
    boolean secondDone = false;
    Object object = new Object();

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (object) {
            printFirst.run();
            firstDone = true;
            object.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (object) {
            while (!firstDone) {
                object.wait();
            }
            printSecond.run();
            secondDone = true;
            object.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (object) {
            while (!secondDone) {
                object.wait();
            }
            printThird.run();
        }
    }
}
```

```java
// 1114. 按序打印
// synchronized method
class Foo {
    boolean firstDone = false;
    boolean secondDone = false;
    // boolean thirdDone = false;
    // int start = 0;

    public Foo() {

    }

    public synchronized void first(Runnable printFirst) throws InterruptedException {
        // while (start != 0 && !thirdDone) {
        //     wait();
        // }
        printFirst.run();
        firstDone = true;
        // thirdDone = false;
        notifyAll();
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {
        while (!firstDone) {
            wait();
        }
        printSecond.run();
        secondDone = true;
        // firstDone = false;
        notifyAll();
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {
        while (!secondDone) {
            wait();
        }
        printThird.run();
        // thirdDone = true;
        // secondDone = false;
        // notifyAll();
    }
}
```
# 两个交替运行

```java
// 1115. 交替打印 FooBar
// 锁定范围小的object
class FooBar {
    private int n;
    boolean firstDone = false;
    boolean secondDone = false;
    Object object = new Object();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (object) {
                while (i != 0 && !secondDone) {
                    object.wait();
                }
                printFoo.run();
                firstDone = true;
                secondDone = false;
                object.notify();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (object) {
                while (!firstDone) {
                    object.wait();
                }
                printBar.run();
                secondDone = true;
                firstDone = false;
                object.notify();
            }
        }
    }
}
```



```java
// 1115. 交替打印 FooBar
// 锁定范围大的object
class FooBar {
    private int n;
    boolean firstDone = false;
    boolean secondDone = false;
    Object object = new Object();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        synchronized (object) {
            for (int i = 0; i < n; i++) {
                while (i != 0 && !secondDone) {
                    object.wait();
                }
                printFoo.run();
                firstDone = true;
                secondDone = false;
                object.notify();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        synchronized (object) {
            for (int i = 0; i < n; i++) {
                while (!firstDone) {
                    object.wait();
                }
                secondDone = true;
                firstDone = false;
                printBar.run();
                object.notify();
            }
        }
    }
}
```

```java
// 1115. 交替打印 FooBar
// synchronized method
class FooBar {
    private int n;
    boolean firstDone = false;
    boolean secondDone = false;

    public FooBar(int n) {
        this.n = n;
    }

    public synchronized void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (i != 0 && !secondDone) {
                wait();
            }
            printFoo.run();
            firstDone = true;
            secondDone = false;
            notify();
        }
    }

    public synchronized void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            while (!firstDone) {
                wait();
            }
            secondDone = true;
            firstDone = false;
            printBar.run();
            notify();
        }
    }
}
```

# 多个指定顺序

```java
// 1116. 打印零与奇偶数
class ZeroEvenOdd {
    private int n;
    boolean firstDone = false;
    boolean secondDone = false;
    boolean thirdDone = false;
    boolean fourthDone = false;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public synchronized void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // System.out.println(!(i == 0 || fourthDone || secondDone));
            // System.out.println(i == 0);
            while(!(i == 0 || fourthDone || secondDone)){
                wait();
            }
            printNumber.accept(0);
            System.out.println(0);
            if(i == 0 || fourthDone){
                firstDone = true;
                fourthDone = false;
            }else{
                thirdDone = true;
                secondDone = false;
            }
            notifyAll();
        }
    }

    public synchronized void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            if (i % 2 == 1)
                continue;
            while(!firstDone){
                wait();
            }
            printNumber.accept(i + 1);
            System.out.println(i+1);
            secondDone = true;
            firstDone = false;
            notifyAll();
        }
    }

    public synchronized void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0)
                continue;
            while(!thirdDone){
                wait();
            }
            printNumber.accept(i + 1);
            System.out.println(i+1);
            fourthDone = true;
            thirdDone = false;
            notifyAll();
        }
    }
}
```

