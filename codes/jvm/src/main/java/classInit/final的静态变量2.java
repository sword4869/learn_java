package classInit;

public class final的静态变量2 {
    public static void main(String[] args) {
        System.out.println(TestB.o);
        // 静态代码块
        // java.lang.Object@5305068a
    }
}

class TestB {
    public final static Object o = new Object();
    
    static{
        System.out.println("静态代码块");
    }
}
