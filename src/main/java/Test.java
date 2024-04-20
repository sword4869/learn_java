import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println("sdf".substring(0, 3));  // sdf
        // System.out.println("sdf".substring(0, 4));  // 不允许超出的 endIndex
        
        System.out.println("sdf".substring(3)); // 允许 beginIndex 是长度，即刚好超出1个索引
        // System.out.println("sdf".substring(4)); // 不允许超出更多的 beginIndex
    }
}
