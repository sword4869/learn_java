package classInit;

public class final的静态变量1 {
    public static void main(String[] args) {
        System.out.println(TestA.a);
        System.out.println(TestA.b);
        System.out.println(TestA.c);
        // 0
        // b
        // 静态代码块
        // 3
    }
}

class TestA {
    public static final int a = 0;
    public static final String b = "b";
    public static final int c = Integer.valueOf("3");

    static{
        System.out.println("静态代码块");
    }
}
