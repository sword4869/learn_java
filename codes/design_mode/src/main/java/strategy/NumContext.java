package strategy;

public class NumContext {
    NumStrategy numStrategy;
    public NumContext(NumStrategy numStrategy){
        this.numStrategy = numStrategy;
    }

    public int executeStrategy(int num1, int num2){
        return numStrategy.operate(num1, num2);
    }
}
