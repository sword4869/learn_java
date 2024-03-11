package lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Boo {
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
        objectObjectHashMap.put(o, o);
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
// Object的hash值初始化，只当调用hashcode()后才有值。