package lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/*
-XX:BiasedLockingStartupDelay=0

- 偏向锁->hashCode->带hashcode的无锁
- 【偏向锁】->hashCode->【重锁】
- 【带hashcode的无锁】->进入同步区->【轻量锁】
 */
public class BiasedLockAndHashcode {
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        Object o = new Object();
        // 偏向锁状态，调用hashcode后，变成无锁状态
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println(o.hashCode());
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("-----------------------------------------");


        // 偏向锁->进入同步区->【偏向锁】->hashcode->【重锁】
        Object o4 = new Object();
        synchronized (o4) {
            System.out.println(ClassLayout.parseInstance(o4).toPrintable());
            System.out.println(o4.hashCode());
            System.out.println(ClassLayout.parseInstance(o4).toPrintable());
        }
        System.out.println("-----------------------------------------");

        // 偏向锁->hashcode->【带hashcode的无锁】->进入同步区->【轻量锁】
        Object o5 = new Object();
        System.out.println(o5.hashCode());
        System.out.println(ClassLayout.parseInstance(o5).toPrintable());
        synchronized (o5) {
            System.out.println(ClassLayout.parseInstance(o5).toPrintable());
        }
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

38997010
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 12 0c 53 (00000001 00010010 00001100 01010011) (1393299969)
      4     4        (object header)                           02 00 00 00 (00000010 00000000 00000000 00000000) (2)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

-----------------------------------------
2114664380
1012660144
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 b0 f7 5b (00000001 10110000 11110111 01011011) (1542959105)
      4     4        (object header)                           3c 00 00 00 (00111100 00000000 00000000 00000000) (60)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 bc 37 0b (00000001 10111100 00110111 00001011) (188201985)
      4     4        (object header)                           7e 00 00 00 (01111110 00000000 00000000 00000000) (126)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

-----------------------------------------
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 00 79 b1 (00000101 00000000 01111001 10110001) (-1317470203)
      4     4        (object header)                           fb 01 00 00 (11111011 00000001 00000000 00000000) (507)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

1793329556
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           fa c9 b0 d5 (11111010 11001001 10110000 11010101) (-709834246)
      4     4        (object header)                           fb 01 00 00 (11111011 00000001 00000000 00000000) (507)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

-----------------------------------------
445884362
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 ca a7 93 (00000001 11001010 10100111 10010011) (-1817720319)
      4     4        (object header)                           1a 00 00 00 (00011010 00000000 00000000 00000000) (26)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           68 f0 2f d0 (01101000 11110000 00101111 11010000) (-802164632)
      4     4        (object header)                           ed 00 00 00 (11101101 00000000 00000000 00000000) (237)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
 */