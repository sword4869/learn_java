package task;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
    static Object object1 = new Object();
    static Object objec2 = new Object();

    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        // synchronizedDeadLock();
        lockDeadLock();
    }

    private static void synchronizedDeadLock() {
        /*
        申请到 object1
        申请到 object2
        ...                 [死锁]
         */
        new Thread(() -> {
            synchronized (object1) {
                System.out.println("申请到 object1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (objec2) {
                    System.out.println("申请到 object2");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (objec2) {
                System.out.println("申请到 object2");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (object1) {
                    System.out.println("申请到 object1");
                }
            }
        }).start();
    }


    private static void lockDeadLock() {
        /*
        申请到 lock1
        申请到 lock2
        ...                 [死锁]
         */
        new Thread(() -> {
            try{
                lock1.lock();
                System.out.println("申请到 lock1");
                Thread.sleep(10);
                try{
                    lock2.lock();
                }
                finally {
                    lock2.unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock1.unlock();
            }
        }).start();

        new Thread(() -> {
            try{
                lock2.lock();
                System.out.println("申请到 lock2");
                Thread.sleep(10);
                try{
                    lock1.lock();
                }
                finally {
                    lock1.unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock2.unlock();
            }
        }).start();
    }
}
