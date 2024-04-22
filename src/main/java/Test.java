public class Test {    
    // 初始化时赋值
    private static final int a = 1;

    // 声明时不赋值，错误
    // private static final int b;

    // 声明时不赋值，静态代码块赋值
    private static final int c;
    static {
        c = 3;
    }

    public static void main(String[] args) {
        System.out.println(a);
        // System.out.println(b);
        System.out.println(c);
    }
}