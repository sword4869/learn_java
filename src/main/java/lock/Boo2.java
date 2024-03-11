package lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Boo2 {
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        new Thread(()->{
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }, "t1").start();
    }
}
//
//