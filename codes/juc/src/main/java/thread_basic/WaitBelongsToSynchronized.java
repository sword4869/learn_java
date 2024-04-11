package thread_basic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitBelongsToSynchronized {
    // wait()和notify()方法的调用必须具有内置锁 synchronized(this) 的代码块内或同步方法才能调用，否则就会报该错误。
    // 如果用了显式锁 Lock 就不要用 wait() 和 notify() 了，它们是两套加锁机制，不能混着用的。

    static Lock lock = new ReentrantLock();
    static Object object = new Object();
    public static void synchronizedWait() throws InterruptedException {
        /* object.wait() 要配合 synchronized 使用 */
        // object.wait();
        // java.lang.IllegalMonitorStateException: current thread is not owner

        synchronized (object){
            object.wait(1000);
        }
    }

    public static void LockWaitIsError() throws InterruptedException {
        try{
            lock.lock();
            // lock.wait(1000);
            // Exception in thread "main" java.lang.IllegalMonitorStateException
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        synchronizedWait();
        LockWaitIsError();
    }
}
