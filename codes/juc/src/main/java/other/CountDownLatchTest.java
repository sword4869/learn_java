package other;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) {
        /*
        Thread 0 release latch. 3
        Thread 0 is sleeping for 2 seconds
        Thread 1 release latch. 1
        Thread 2 release latch. 0
        All threads released
        */
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            try {
                System.out.println("Thread 0 release latch. " + countDownLatch.getCount());
                countDownLatch.countDown();     // 非阻塞
                System.out.println("Thread 0 is sleeping for 2 seconds");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(500);
                countDownLatch.countDown();
                System.out.println("Thread 1 release latch. " + countDownLatch.getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                countDownLatch.countDown();
                System.out.println("Thread 2 release latch. " + countDownLatch.getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All threads released");
    }
}
