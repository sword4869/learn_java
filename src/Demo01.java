
public class Demo01 {
    public static void main(String[] args) {
        Thread.currentThread().setContextClassLoader(null);
        System.out.println(Thread.currentThread().getContextClassLoader());
    }
}

class A02{
    static int a = 0;
    static {
        a = 1;
        System.out.println("A02");
    }
}

class B02 extends A02{
    static int a = 2;
    static {
        System.out.println("B02");
    }
}
/* A02
B02
2 */