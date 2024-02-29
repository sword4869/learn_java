import java.util.Scanner;
import java.util.StringJoiner;

public class Student {

    public static void main(String[] args) {
        // 默认容量为16
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.capacity());  // 16

        // 扩容为原来的2倍 + 2
        sb.append("abcdefghijklmnopqrstuvwxyz");
        System.out.println(sb.capacity());  // 34 = 16 * 2  + 2

        // 如果扩容之后还不够，以实际长度为准
        sb.append("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuv");
        System.out.println(sb.capacity());  // 74 > 70 = 34 * 2 + 2
    }
}