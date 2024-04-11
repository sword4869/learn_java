package thread_basic;

public class WaitSleepCase {
    static final Object LOCK = new Object();

    private static void illegalWait() throws InterruptedException {
        LOCK.wait();
    }

    private static void waiting() throws InterruptedException {
        /*
        线程抢到锁后waiting，会释放锁。

        结果：
        waiting...
        other...     【立即输出】
         */
        Thread t1 = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println("waiting...");
                    LOCK.wait(5000L);
                } catch (InterruptedException e) {
                    System.out.println("interrupted...");
                    e.printStackTrace();
                }
            }
        }, "t1");
        t1.start();

        Thread.sleep(100);  // 先让线程抢到锁
        synchronized (LOCK) {
            System.out.println("other...");
        }
    }

    private static void sleeping() throws InterruptedException {
        /*
        线程抢到锁后sleep，并不会释放锁。

        结果：
        sleeping...
        other...    【等待5秒才输出结果】
         */
        Thread t1 = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println("sleeping...");
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    System.out.println("interrupted...");
                    e.printStackTrace();
                }
            }
        }, "t1");
        t1.start();

        Thread.sleep(100);      // 先让线程抢到锁
        synchronized (LOCK) {
            System.out.println("other...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        sleeping();
        waiting();
    }
}