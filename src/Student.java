import java.util.Scanner;
import java.util.StringJoiner;

public class Student {

    public static void main(String[] args) {
        System.out.println('中' + '文');  // 46004
        System.out.println('中' + "文");  // 中文

        System.out.println("abc" + 123);    // abc123
        System.out.println('a' + null);  // abc
        System.out.println(1 + 2 + 'b' + 3 + 4);  // 108

        System.out.println("abc" + true + null);  // abctruenull
        // System.out.println(null);  // 不允许输出null
    }
}