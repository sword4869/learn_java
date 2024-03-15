import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Test {
    public static void main(String[] args) {
        PriorityQueue<Integer> qMin = new PriorityQueue<>();
        PriorityQueue<Integer> qMax = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        qMax.add(1);
        qMax.add(2);
        qMax.add(3);
        System.out.println(qMax.poll());
    }
}
