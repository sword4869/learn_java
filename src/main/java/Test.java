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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException {
        String[] arr = { "a", "b", "c" };
        List<String> list = List.of(arr);
        Set<String> set = Set.of(arr);
        Map<String, Integer> map = Map.of("a", 1, "b", 2, "c", 3);
        int a = 0;
        int b= 5;
        System.out.println((a+b) >>> 1);
    }

}
