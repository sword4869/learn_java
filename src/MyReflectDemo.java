import java.util.Random;

public class MyReflectDemo {
    public static void main(String[] args) {
        int sum = getSum(1, 2, 3, 4, 5);
    }

    public static int getSum(int... args) {
        // System.out.println(args);  //[I@119d7047
        System.out.println(args.length); // 0
        int sum = 0;
        for (int i : args) {
            sum = sum + i;
        }
        return sum;
    }
}