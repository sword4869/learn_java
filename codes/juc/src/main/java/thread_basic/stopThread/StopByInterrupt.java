package thread_basic.stopThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StopByInterrupt {
    // interrupt() 只是设置 interrupted 标志位，并不能暂定线程、阻塞线程。
    /*public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("running...");
            }
        });
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
        System.out.println("stop...");
    }*/


    public static void interruptTimedWaiting() throws InterruptedException {
        /*
        结果：由sleep抛出的被打断的异常
        running...
        running...
        TIMED_WAITING
        stop...
        Exception in thread "Thread-0" java.lang.RuntimeException: java.lang.InterruptedException: sleep interrupted
            at src.main.java.thread_basic.stopThread.StopByInterrupt.lambda$interruptTimedWaiting$0(StopByInterrupt.java:40)
            at java.base/java.lang.Thread.run(Thread.java:842)
        Caused by: java.lang.InterruptedException: sleep interrupted
            at java.base/java.lang.Thread.sleep(Native Method)
            at src.main.java.thread_basic.stopThread.StopByInterrupt.lambda$interruptTimedWaiting$0(StopByInterrupt.java:38)
            ... 1 more
         */
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("running...");
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        Thread.sleep(10);
        System.out.println(thread.getState());
        thread.interrupt();
        System.out.println("stop...");
    }

    public static void interruptWaiting() throws InterruptedException {
        /*
        running...
        WAITING
        stop...
        Exception in thread "Thread-0" java.lang.RuntimeException: java.lang.InterruptedException
            at src.main.java.thread_basic.stopThread.StopByInterrupt.lambda$interruptWait$1(StopByInterrupt.java:58)
            at java.base/java.lang.Thread.run(Thread.java:842)
        Caused by: java.lang.InterruptedException
            at java.base/java.lang.Object.wait(Native Method)
            at java.base/java.lang.Object.wait(Object.java:338)
            at src.main.java.thread_basic.stopThread.StopByInterrupt.lambda$interruptWait$1(StopByInterrupt.java:56)
            ... 1 more
         */
        Object LOCK = new Object();
        Thread thread = new Thread(() -> {
            synchronized(LOCK){
                System.out.println("running...");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
        Thread.sleep(100);
        System.out.println(thread.getState());
        thread.interrupt();
        System.out.println("stop...");
    }

    public static void interruptBlocked() throws InterruptedException {
        // 有问题......................要么是RUNNABLE,要么是WAITING,我们想要的是BLOCKED
        Lock lock = new ReentrantLock();
        Thread thread1 = new Thread(() -> {
            System.out.println("running...");
            try{
                lock.lock();                    // RUNNABLE
                lock.wait();
                // lock.lockInterruptibly();    // WAITING
                System.out.println("running...get locked");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });
        try{
            thread1.start();
            Thread.sleep(100);
            lock.lock();
            System.out.println(thread1.getState());
            thread1.interrupt();
            System.out.println(thread1.getState());
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // interruptTimedWaiting();
        // interruptWaiting();
        interruptBlocked();
    }
}
