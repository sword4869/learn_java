package lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/*
-XX:BiasedLockingStartupDelay=0

证明在Main线程中synchronized和开启一个新线程synchronized一样的效果
- 偏向锁
- 无锁(hashcode)->轻量锁
- 不测了……
 */
public class MainThreadIsSameAsNewThreadWhenSynchronized {
    public static void main(String[] args) throws InterruptedException {
        // 偏向锁->偏向锁：main
        Object o1 = new Object();
        System.out.println(ClassLayout.parseInstance(o1).toPrintable());
        synchronized (o1) {
            System.out.println(ClassLayout.parseInstance(o1).toPrintable());
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println("-----------------------------------------");

        // 偏向锁->偏向锁: new Thread
        Object o2 = new Object();
        System.out.println(ClassLayout.parseInstance(o2).toPrintable());
        new Thread(() -> {
            synchronized (o2) {
                System.out.println(ClassLayout.parseInstance(o2).toPrintable());
            }
        }, "t").start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("-----------------------------------------");


        // 无锁->轻量锁：main
        Object o3 = new Object();
        o3.hashCode();
        System.out.println(ClassLayout.parseInstance(o3).toPrintable());
        synchronized (o3) {
            System.out.println(ClassLayout.parseInstance(o3).toPrintable());
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println("-----------------------------------------");

        // 无锁->轻量锁: new Thread
        Object o4 = new Object();
        o4.hashCode();
        System.out.println(ClassLayout.parseInstance(o4).toPrintable());
        new Thread(() -> {
            synchronized (o4) {
                System.out.println(ClassLayout.parseInstance(o4).toPrintable());
            }
        }, "t").start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("-----------------------------------------");

    }
}
/*
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 00 69 80 (00000101 00000000 01101001 10000000) (-2140602363)
      4     4        (object header)                           ee 01 00 00 (11101110 00000001 00000000 00000000) (494)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

-----------------------------------------
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 18 59 a8 (00000101 00011000 01011001 10101000) (-1470556155)
      4     4        (object header)                           ee 01 00 00 (11101110 00000001 00000000 00000000) (494)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

-----------------------------------------
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 00 69 80 (00000101 00000000 01101001 10000000) (-2140602363)
      4     4        (object header)                           ee 01 00 00 (11101110 00000001 00000000 00000000) (494)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

-----------------------------------------
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 80 04 a8 (00000101 10000000 00000100 10101000) (-1476100091)
      4     4        (object header)                           ee 01 00 00 (11101110 00000001 00000000 00000000) (494)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

-----------------------------------------
 */