
##  Random

```java
import java.util.Random;

public class MyReflectDemo {
       public static void main(String[] args)  {
        Random r = new Random();
        int a = r.nextInt();      // [0, 2^32)
        int b = r.nextInt(10);      // [0, 10)
        int c = r.nextInt(5, 10);    // [5, 10)

        boolean nextBoolean = r.nextBoolean();
    }
}
```
