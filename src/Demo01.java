
public class Demo01 {
    public static void main(String[] args) {
        Test[] tests = new Test[10];    // 不会触发静态代码块
        // new Test();   // 会触发静态代码块
    }
}

class Test{
    static{
        System.out.println("Test类的静态代码块");
    }
}