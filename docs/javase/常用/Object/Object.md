## Object的方法

1. 所有类直接或间接地继承Object类

    ![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407111044320.jpg)

2. 所有方法

    打印和比较

    - `public String toString()`: 如果没有重写，应用对象将打印的是地址值
    - `public boolean equals(Object obj)`: 如果没有重写，比较对象的地址值
    - `public native int hashCode()`

    反射

    - `public final native getClass()`

    锁

    - `public final native void notify(), notifyAll()`
    - `public final void wait(), wait(long), wait(long,int)`: 其中wait(long)是虚方法，其他不是

    克隆和终结

    - `protected native Object clone()`: 浅克隆方法
    - `protected void finalize()`: 对象回收时调用，JDK9被弃用

3. Object有6个虚方法

    `hashCode()`,

    `getClass()`, 

    `notify()`, `notifyAll()`, 

    `wait(long)`, 

    `clone()`

## toString()

如果没有重写，应用对象将打印的是地址值

![Alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407111044322.png)



