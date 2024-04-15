package strategy;

public class ClientDemo {
    public static void main(String[] args) {
        int num1 = 4, num2 = 34;
        NumContext numContext = new NumContext(new Add());
        System.out.println(numContext.executeStrategy(num1, num2));

        numContext = new NumContext(new Sub());
        System.out.println(numContext.executeStrategy(num1, num2));
    }
}
