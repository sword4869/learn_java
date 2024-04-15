package classInit;

public class 非final的静态变量 {
    public static void main(String[] args) {
        /*
         * 静态代码块1
         * 0
         */
        System.out.println(Test1.a);

        /*
         * 静态代码块2
         * 2
         */
        System.out.println(Test2.b);

        /*
         * 静态代码块3
         * hello
         */
        System.out.println(Test3.c);

        /*
         * 静态代码块4
         * java.lang.Object@1b6d3586
         */
        System.out.println(Test4.d);
    }
}

class Test1 {
    public static int a;
    static {
        System.out.println("静态代码块1");
    }
}

class Test2 {
    public static int b = 2;
    static {
        System.out.println("静态代码块2");
    }
}

class Test3{
    public static String c = "hello";
    static {
        System.out.println("静态代码块3");
    }
}

class Test4 {
    public static Object d = new Object();
    static {
        System.out.println("静态代码块4");
    }
}


