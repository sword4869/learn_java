package lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/*
-XX:BiasedLockingStartupDelay=0

证明忘记加入synchronized，锁的状态不会发生变化
- 无锁
- 轻量锁
- 不用测试应该也一样
- 当然不用加入main thread的测试，这不跟没测一样
 */
public class ForgetSynchronized {
    public static void main(String[] args) throws InterruptedException {
        // 偏向锁->偏向锁
        Object o1 = new Object();
        System.out.println(ClassLayout.parseInstance(o1).toPrintable());
        new Thread(()->{
            System.out.println(ClassLayout.parseInstance(o1).toPrintable());
        }, "偏向锁").start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("-----------------------------------------");


        // 无锁->无锁
        Object o2 = new Object();
        System.out.println(o2.hashCode());
        System.out.println(ClassLayout.parseInstance(o2).toPrintable());
        new Thread(()->{
            System.out.println(ClassLayout.parseInstance(o2).toPrintable());
        }, "无锁").start();

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
      0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

-----------------------------------------
1688376486
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 a6 94 a2 (00000001 10100110 10010100 10100010) (-1567316479)
      4     4        (object header)                           64 00 00 00 (01100100 00000000 00000000 00000000) (100)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 a6 94 a2 (00000001 10100110 10010100 10100010) (-1567316479)
      4     4        (object header)                           64 00 00 00 (01100100 00000000 00000000 00000000) (100)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
 */
