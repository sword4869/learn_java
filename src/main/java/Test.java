import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
       HashMap<String, String> m = new HashMap<>();
       m.put("abc", "123");
       System.out.println(m.get("abc"));
       ArrayList<String> a  =  new ArrayList<>(m.values());
       for(String b: a){
        System.out.println(b);
       }
    }
}
