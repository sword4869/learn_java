import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException {
        // mybatis的标签表格
        Boolean a = null;
        // boolean error1 = Boolean.valueOf(a);   // 自动拆箱，会报空指针异常
        // boolean error2 = Boolean.parseBoolean(a);  // 只能传入字符串，会报空指针异常
        // boolean error3 = a ? true : false;   // 还是自动拆箱，会报空指针异常
        // boolean error4 = a.booleanValue();   // 报空指针异常
        // boolean error5 = a.booleanValue() ? true : false;   // 报空指针异常
        boolean b = a == null ? false : a;
        System.out.println(b);
    }
}
