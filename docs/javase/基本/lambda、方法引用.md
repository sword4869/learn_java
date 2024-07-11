
```java
public class FunctionDemo1 {
    public static void main(String[] args) {
        Integer[] arr = { 3, 5, 4, 1, 6, 2 };

        // 匿名内部类
        // Comparator是一个函数式接口
        Arrays.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        // lambda表达式 
        Arrays.sort(arr, (Integer o1, Integer o2) -> {
            return o2 - o1;
        });

        // lambda表达式简化格式
        Arrays.sort(arr, (o1, o2)->o2 - o1 );

        // 方法引用
        Arrays.sort(arr, FunctionDemo1::subtraction);
        
        System.out.println(Arrays.toString(arr));
    }
    
    // 把这个方法当做抽象方法的方法体
    // 可以是Java已经写好的，也可以是一些第三方的工具类
    public static int subtraction(int num1, int num2) {
        return num2 - num1;
    }
}
```

```java
// 函数式接口
interface MyString {
    String mySubString(String s, int x, int y);
}

public class MyStringDemo {
    public static void main(String[] args) {
        // 匿名内部类
        useMyString(new MyString() {
            @Override
            public String mySubString(String s, int x, int y) {
                return s.substring(x, y);
            }
        });

        // Lambda简化写法
        useMyString((s, x, y) -> s.substring(x, y));

        // 引用类的实例方法
        useMyString(String::substring);
    }

    private static void useMyString(MyString my) {
        String s = my.mySubString("HelloWorld", 2, 5);
        System.out.println(s);
    }
}
```

## lambda

lambda 表达式中不能修改外部变量的值
```java
String r = null;
int rv = 0;
m.forEach((k, v)->{
    // error: local variables referenced from a lambda expression must be final or effectively final
    if(v > rv){
    if(v > rv){
        rv = v;
        r = k;
    }
});
```

## 1. 方法引用

方法引用符`::`

1. 引用处必须是函数式接口
2. 被引用的方法的形参和返回值需要跟抽象方法保持一致。


```java
ArrayList<String> list = new ArrayList<>();
Collections.addAll(list, "张无忌,15", "周芷若,14", "赵敏,13", "张强,20", "张三丰,100", "张翠山,40", "张良,35", "王二麻子,37", "谢广坤,41");
// 第一个 Student::new 是引用构造方法
// 第二个 Student::new 是引用数组的构造方法
Student[] arr = list.stream().map(Student::new).toArray(Student[]::new);
```

### 1.1. 引用静态方法

`类名::静态方法名`



```java
ArrayList<String> list = new ArrayList<>();
Collections.addAll(list, "1", "2", "3", "4", "5");

// 把他们都变成int类型
list.stream().map(new Function<String, Integer>() {
    @Override
    public Integer apply(String s) {
        int i = Integer.parseInt(s);
        return i;
    }
}).forEach(s -> System.out.println(s));

// public static int parseInt(String s) throws NumberFormatException {
//     return parseInt(s,10);
// }
list.stream()
        .map(Integer::parseInt)
        .forEach(s -> System.out.println(s));
```

### 1.2. 引用成员方法

- 其他类：`其他类对象::成员方法名`
- 本类：`this::成员方法名`
- 父类：`super::成员方法名`

PS: 后两者的引用处不能是静态方法中，因为this、super关键字没有，所以必须在成员方法中引用。

```java
import java.util.ArrayList;
import java.util.Collections;

public class Son extends Father{
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "张无忌", "周芷若", "赵敏", "张强", "张三丰");

        // 静态方法中不能使用this关键字，所以创建对象来当成其他类对象
        list.stream().filter(new Son()::stringJudge)
                .forEach(s -> System.out.println(s));

        // 本类中
        new Son().print(list);

        // 父类中
        new Son().print2(list);
    }

    public boolean stringJudge(String s) {
        return s.startsWith("张") && s.length() == 3;
    }

    // 本类中的成员方法
    public void print(ArrayList<String> list) {
        list.stream().filter(this::stringJudge)
                .forEach(s -> System.out.println(s));
    }

    // 父类中的成员方法
    public void print2(ArrayList<String> list) {
        list.stream().filter(super::stringJudge)
                .forEach(s -> System.out.println(s));
    }
}

class Father {
    public boolean stringJudge(String s) {
        return s.startsWith("张") && s.length() == 3;
    }
}
```
### 1.3. 类名引用成员方法

`类名::成员方法`

这里的类名，仅限于抽象方法的第一个参数的类型。

“被引用的方法的形参和返回值需要跟抽象方法保持一致”变成了“抽象方法形参从第二个开始一致”。

```java
ArrayList<String> list = new ArrayList<>();
Collections.addAll(list, "aaa", "bbb", "ccc", "ddd");
list.stream().map(new Function<String, String>() {
    @Override
    public String apply(String s) {
        return s.toUpperCase();
    }
}).forEach(s -> System.out.println(s));
list.stream().map(String::toUpperCase).forEach(s -> System.out.println(s));
```
### 1.4. 引用构造方法

`类名::new`

```java

public class FunctionDemo4 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "张无忌,15", "周芷若,14", "赵敏,13", "张强,20", "张三丰,100", "张翠山,40", "张良,35", "王二麻子,37",
                "谢广坤,41");
        List<Student> newList = list.stream().map(new Function<String, Student>() {
            @Override
            public Student apply(String s) {
                String[] arr = s.split(",");
                String name = arr[0];
                int age = Integer.parseInt(arr[1]);
                return new Student(name, age);
            }
        }).collect(Collectors.toList());
        System.out.println(newList);

        List<Student> newList2 = list.stream().map(Student::new).collect(Collectors.toList());
        System.out.println(newList2);
    }
}


class Student {
    private String name;
    private int age;

    public Student() {
    }

    // 需要匹配函数式接口中的抽象方法apply的形参只有一个
    public Student(String str) {
        String[] arr = str.split(",");
        this.name = arr[0];
        this.age = Integer.parseInt(arr[1]);
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Student{name = " + name + ", age = " + age + "}";
    }
}
```

### 1.5. 类名引用成员方法

`类名::成员方法`

这里的类名，仅限于抽象方法的第一个参数的类型。

“被引用的方法的形参和返回值需要跟抽象方法保持一致”变成了“抽象方法形参从第二个开始一致”。

```java
ArrayList<String> list = new ArrayList<>();
Collections.addAll(list, "aaa", "bbb", "ccc", "ddd");
list.stream().map(new Function<String, String>() {
    @Override
    public String apply(String s) {
        return s.toUpperCase();
    }
}).forEach(s -> System.out.println(s));
list.stream().map(String::toUpperCase).forEach(s -> System.out.println(s));
```
```java
ArrayList<Student> list = new ArrayList<>();
list.add(new Student("zhangsan", 23));
list.add(new Student("lisi", 24));
list.add(new Student("wangwu", 25));

String[] arr = list.stream().map(new Function<Student, String>() {
    @Override
    public String apply(Student student) {
        return student.getName();
    }
}).toArray(String[]::new);

String[] arr = list.stream().map(Student::getName).toArray(String[]::new);
```
### 1.6. 引用数组的构造方法

`数据类型[]::new`

```java
ArrayList<Integer> list = new ArrayList<>();
Collections.addAll(list, 1, 2, 3, 4, 5);
Integer[] arr = list.stream().toArray(new IntFunction<Integer[]>() {
    @Override
    public Integer[] apply(int value) {
        return new Integer[value];
    }
});
Integer[] arr2 = list.stream().toArray(Integer[]::new);
```