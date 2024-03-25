
## 5. Collections工具类

## 快速构建
空
```java
List<Object> emptyList = Collections.emptyList();
Set<Object> emptySet = Collections.emptySet();
Map<Object, Object> emptyMap = Collections.emptyMap();
```
singleton
```java
Set<Object> set = Collections.singleton(user);
List<Object> list = Collections.singletonList(user);
Map<Object, Object> map = Collections.singletonMap(user);
```
## 其他
仅限单列集合。

![Alt text](../../../images/image-48.png)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CollectionsDemo2 {
    public static void main(String[] args) {
        System.out.println("-------------sort默认规则--------------------------");
        ArrayList<Integer> list1 = new ArrayList<>();
        //addAll  批量添加元素
        Collections.addAll(list1, 10, 1, 2, 4, 8, 5, 9, 6, 7, 3);

        //shuffle 打乱
        Collections.shuffle(list1);

        // 默认规则，需要重写Comparable接口compareTo方法。Integer已经实现，按照从小打大的顺序排列
        // 如果是自定义对象，需要自己指定规则
        Collections.sort(list1);
        System.out.println(list1);

        Collections.sort(list1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        System.out.println(list1);

        Collections.sort(list1, (o1, o2) -> o2 - o1);
        System.out.println(list1);

        System.out.println("-------------binarySearch--------------------------");
        // 需要元素有序
        ArrayList<Integer> list2 = new ArrayList<>();
        Collections.addAll(list2, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(Collections.binarySearch(list2, 9)); // 8
        System.out.println(Collections.binarySearch(list2, 1)); // 0
        System.out.println(Collections.binarySearch(list2, 20));    // -11

        System.out.println("-------------copy--------------------------");
        ArrayList<Integer> list3 = new ArrayList<>();
        ArrayList<Integer> list4 = new ArrayList<>();
        Collections.addAll(list3, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collections.addAll(list4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        // dest <- src
        // 会覆盖原来的元素
        // 注意点：如果list3的长度 > list4的长度，方法会报错
        Collections.copy(list4, list3);
        System.out.println(list3);
        System.out.println(list4);

        System.out.println("-------------fill--------------------------");
        ArrayList<Integer> list5 = new ArrayList<>();
        Collections.addAll(list5, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // 把集合中现有的所有数据，都修改为指定数据
        Collections.fill(list5, 100);
        System.out.println(list5);

        System.out.println("-------------max/min--------------------------");
        ArrayList<Integer> list6 = new ArrayList<>();
        Collections.addAll(list6, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // 求最大值或者最小值
        System.out.println(Collections.max(list6));
        System.out.println(Collections.min(list6));

        System.out.println("-------------max/min指定规则--------------------------");
        ArrayList<String> list7 = new ArrayList<>();
        Collections.addAll(list7, "a", "aa", "aaa", "aaaa");
        // String中默认是按照字母的abcdefg顺序进行排列的
        // 现在我要求最长的字符串
        // 默认的规则无法满足，可以自己指定规则
        // 求指定规则的最大值或者最小值
        System.out.println(Collections.max(list7, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        }));

        System.out.println("-------------swap--------------------------");
        ArrayList<Integer> list8 = new ArrayList<>();
        Collections.addAll(list8, 1, 2, 3);
        Collections.swap(list8, 0, 2);
        System.out.println(list8);

    }
}
```
