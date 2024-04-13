package other;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {
        /*
        Thread 0 get semaphore
        Thread 1 get semaphore
        Thread 2 get semaphore
        Thread 3 get semaphore
        Thread 1 release semaphore
        Thread 0 release semaphore
        Thread 2 release semaphore
        Thread 3 release semaphore
         */
        Semaphore semaphore = new Semaphore(3);

        for(int i=0;i<4;i++){
            final int j = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("Thread " + j + " get semaphore");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    semaphore.release();
                    System.out.println("Thread " + j + " release semaphore");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
