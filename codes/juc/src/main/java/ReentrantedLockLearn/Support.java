package ReentrantedLockLearn;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Support {
    static Lock lock = new ReentrantLock();

    // 一般锁
    public static void normalLock(){
        // try-finally 释放锁
        try{
            lock.lock();
        } finally {
            lock.unlock();
        }
    }

    public static void lockInterruptibly() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("lockInterruptibly is triggered");
                return;
            }
            // finally {
            //     lock.unlock();
            // }

            // 注意: 这里不能写在 finally 中, 因为捕获到中断, 并没有获取锁, 否则会报错.
            lock.unlock();
        });

        // 让main线程抢占锁, 而子线程处于等待, 等待中被中断.
        try{
            lock.lock();
            Thread.sleep(1);
            thread.start();
            Thread.sleep(1);
            System.out.println(thread.getState());
            thread.interrupt();
        } finally {
            lock.unlock();
        }
        System.out.println(thread.getState());
    }

    public static void main(String[] args) throws InterruptedException {
        normalLock();
        lockInterruptibly();
    }
}
