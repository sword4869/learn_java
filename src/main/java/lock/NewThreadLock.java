package lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class NewThreadLock {
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            Object o = new Object();
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }, "t").start();
        TimeUnit.SECONDS.sleep(2);
    }
}
