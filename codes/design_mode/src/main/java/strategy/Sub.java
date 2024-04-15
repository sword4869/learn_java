package strategy;

public class Sub implements NumStrategy{
    @Override
    public int operate(int num1, int num2) {
        return num1 - num2;
    }
}
