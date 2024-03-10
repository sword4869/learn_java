import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Boo {
    public static void main(String[] args) throws InterruptedException {
        HashMap<String, String> h = new HashMap<>();
        h.put(null, null);
        System.out.println(h.get(null));
        System.out.println(h.size());
    }

    public void doIt() {
        Integer i = null;
        System.out.println(i);
    }
}
// 静态内部类内能用this吗？
// HashMap的key能为null