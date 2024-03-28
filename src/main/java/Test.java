import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException {
        // mybatis的标签表格
        Boolean a = null;
        // boolean error1 = Boolean.valueOf(a); // 自动拆箱，会报空指针异常
        // boolean error2 = Boolean.parseBoolean(a); // 只能传入字符串，会报空指针异常
        // boolean error3 = a ? true : false; // 还是自动拆箱，会报空指针异常
        // boolean error4 = a.booleanValue(); // 报空指针异常
        // boolean error5 = a.booleanValue() ? true : false; // 报空指针异常
        boolean b = a == null ? false : a;
        System.out.println(b);

        ArrayList<String> list = new ArrayList<>();
Collections.addAll(list, "张无忌-男-15", "张无忌-男-15", "周芷若-女-14");

        // Map<String, List<String>> map = list.stream().collect(Collectors.groupingBy(s -> s.split("-")[2]));
        // System.out.println(map);    // {14=[周芷若-女-14], 15=[张无忌-男-15, 张无忌-男-15]}

        Map<String, Integer> map = new HashMap<>();

        // 创建或更新一个键值对
        map.compute("a", (k,v)-> v == null ? 1 : v + 1);    
        map.compute("a", (k,v)-> v == null ? 1 : v + 1);
        System.out.println(map);    // {a=2}

        // 只创建，不更新
        map.computeIfAbsent("b", k -> 1);   // 创建
        map.computeIfAbsent("b", k -> 2);   // 不会执行
        System.out.println(map);    // {a=2, b=1}

        // 只更新，不创建
        map.computeIfPresent("b", (k,v) -> v + 1);   // 更新
        map.computeIfPresent("c", (k,v) -> v + 1);   // 不会执行
        System.out.println(map);    // {a=2, b=2}
    }

}
