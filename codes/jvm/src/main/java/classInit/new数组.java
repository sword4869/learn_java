package classInit;

public class new数组 {
    public static void main(String[] args) {
        /*
         * 不会初始化
         */
        TestArr[] tests = new TestArr[10];
    }
}

class TestArr {
    static {
        System.out.println("静态代码块3");
    }
}