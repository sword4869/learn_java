import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        splitString("1 + 1");
    }
    public static String[] splitString(String s) {
        List<String> tokens = new LinkedList<>();
        char[] chars = s.toCharArray();
        int n = s.length();
        StringBuilder keepNum = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (chars[i] == '(' || chars[i] == ')' || chars[i] == '+' || chars[i] == '*' || chars[i] == '/') {
                tokens.add(chars[i] + "");
            } else if (chars[i] == ' ') {

            } else if (Character.isDigit(chars[i])) {
                if (i == n - 1 || !Character.isDigit(chars[i + 1])) {
                    keepNum.append(chars[i]);
                    tokens.add(keepNum.toString());
                    keepNum.setLength(0);
                } else {
                    keepNum.append(chars[i]);
                }
            } else {
                if (Character.isDigit(chars[i + 1])) {
                    keepNum.append(chars[i]);
                } else {
                    tokens.add(chars[i] + "");
                }
            }
        }
        System.out.println(tokens);
        return tokens.toArray(new String[0]);
    }
}
