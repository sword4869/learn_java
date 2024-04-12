package ReentrantedLockLearn;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Support {
    static Lock lock = new ReentrantLock();

    static Condition condition1 = lock.newCondition();
    static Condition condition2 = lock.newCondition();

    // 一般锁
    public static void reentrantLock(){
        /*
        获取锁1
        获取锁2
        释放锁2
        释放锁1
         */
        // try-finally 获取锁和释放锁
        // **同一个线程**再次获取锁、释放锁
        try{
            lock.lock();
            System.out.println("获取锁1");
            try{
                lock.lock();
                System.out.println("获取锁2");
            }finally {
                lock.unlock();
                System.out.println("释放锁2");
            }
        } finally {
            lock.unlock();
            System.out.println("释放锁1");
        }
    }

    public static void lockInterruptibly() throws InterruptedException {
        /*
        WAITING                             [main线程抢到锁，线程等待]
        lockInterruptibly is triggered      [中断触发, 线程退出]
        TERMINATED
         */
        Thread thread = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("lockInterruptibly is triggered");
                return;
            }
            // 注意: 这里不能写在 finally 中, 因为捕获到中断, 并没有获取锁, 否则会报错.
            // finally {
            //     lock.unlock();
            // }
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
            Thread.sleep(1);    // 需要等一下，中断没有那么快，不然下面输出 RUNNABLE
        } finally {
            lock.unlock();
        }
        System.out.println(thread.getState());
    }

    public static void lockTimeoutOnce(){
        /*
        获取锁失败       [tryLock()只尝试一次，就直接退出]
         */
        Thread thread = new Thread(() -> {
            try {
                if (!lock.tryLock()) {
                    System.out.println("获取锁失败");
                    return;
                }
            } catch (Exception e) {
                System.err.println("中断 " + e);
            }

            try {
                System.out.println("获取锁成功");
            } finally {
                lock.unlock();
            }
        });

        try {
            lock.lock();
            thread.start();
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void lockTimeoutDuration(){
        /*
        获取锁成功       [等待500ms，抢到了。等不到则退出]
         */
        Thread thread = new Thread(() -> {
            try {
                if (!lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                    System.out.println("获取锁失败");
                    return;
                }
            } catch (Exception e) {
                System.err.println("中断 " + e);
            }

            try {
                System.out.println("获取锁成功");
            } finally {
                lock.unlock();
            }
        });

        try {
            lock.lock();
            thread.start();
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void lockCondition(){
        /*
        条件绑定到Lock锁上，先获取锁，才能等待await/唤醒signal

        开始唤醒条件
        condition1 await success
        condition2 await success
        condition2 await success
         */
        new Thread(()->{
            try{
                lock.lock();
                condition1.await();
                System.out.println("condition1 await success");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(()->{
            try{
                lock.lock();
                condition2.await();
                System.out.println("condition2 await success");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(()->{
            try{
                lock.lock();
                condition2.await();
                System.out.println("condition2 await success");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).start();

        try{
            Thread.sleep(1);
            lock.lock();
            System.out.println("开始唤醒条件");
            condition1.signal();
            condition2.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        reentrantLock();
        lockInterruptibly();
        lockTimeoutOnce();
        lockTimeoutDuration();
        lockCondition();
    }
}
