package lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.Objects;
/*
-XX:BiasedLockingStartupDelay=0

验证：创建一个对象，不调用hashCode()方法前，是没有hashcode值的
- 默认Object无hashcode
- 即使重写hashCode方法也没有hashcode
 */
public class ObjectWithHashcodeUtilCall {
    public static void main(String[] args) throws InterruptedException {
        // 默认Object无hashcode
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        // 即使重写hashCode方法也没有hashcode
        Student o2 = new Student();
        System.out.println(ClassLayout.parseInstance(o2).toPrintable());
    }
}
class Student{
    int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

lock.Student object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           d5 cb 00 f8 (11010101 11001011 00000000 11111000) (-134165547)
     12     4    int Student.id                                0
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
*/