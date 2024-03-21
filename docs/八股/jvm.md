@ JVM
## 1. JVM内存分布主要包括以下几个区域

VM（Java虚拟机）内存分布主要包括以下几个区域：

1.  方法区（Method Area）：用于存储类的结构信息，如类的字段、方法、常量池等。在JDK 8及之前，方法区被称为"永久代"（Permanent Generation），而在JDK 8及之后，被移除了永久代，改为使用元空间（Metaspace）来存储。
2.  堆（Heap）：用于存储对象实例。堆是Java程序中最大的一块内存区域，被所有线程共享。堆被划分为新生代（Young Generation）和老年代（Old Generation）两部分。新生代又分为Eden空间和两个Survivor空间。
3.  虚拟机栈（VM Stack）：每个线程在运行时都会创建一个栈，用于存储局部变量、方法参数、返回值等。栈中的每个栈帧对应一个方法的调用，包括方法的局部变量表、操作数栈、动态链接、方法返回地址等。
4.  本地方法栈（Native Method Stack）：与虚拟机栈类似，但是用于执行本地方法（Native Method）。
5.  程序计数器（Program Counter）：用于记录当前线程执行的字节码指令的地址。