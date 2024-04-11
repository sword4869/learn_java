package src.main.java.thread_basic;

public class ThreadState {
    public static  void directBlocked() throws InterruptedException {
        /*
        main线程先抢到锁, 子线程抢不到锁而由 runnable 进入 blocked.
        new: NEW
        main hold lock: BLOCKED
        waiting...
        continue: RUNNABLE
        run exit: TERMINATED
         */
        Object object = new Object();
        Thread thread = new Thread(() -> {
            synchronized (object) {
                System.out.println("waiting...");
                try {
                    object.wait(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("continue: " + Thread.currentThread().getState());   // RUNNABLE
            }
        });
        System.out.println("new: " + thread.getState());       // New

        synchronized (object){
            thread.start();
            // 细节: start() "The result is that two threads are running concurrently",
            // 让当前线程和被调用线程一起执行, 所以没有 sleep 时会立刻输入 RUNNABLE.
            // 而加上 sleep 后, 留给子线程抢不到锁而进入阻塞的时间, 输出Blocked.
            Thread.sleep(1);
            System.out.println("main hold lock: " + thread.getState());       // BLOCKED
        }

        Thread.sleep(1000);
        System.out.println("run exit: " + thread.getState());        // TERMINATED
    }
    public static void waitToBlocked() throws InterruptedException {
        /*
        先让子线程抢到锁, wait() 进入等待状态, 此时main抢到锁而让子线程进入 blocked, main释放锁后子线程才抢到锁, 由blocked进入 runnable
        new: NEW                                    [创建线程]
        waiting...                                  [调用wait方法]
        main hold lock: WAITING                     [wait()而进入waiting]
        main hold lock: BLOCKED                     [notify(), main线程抢到锁, 不释放. 子线程进入blocked]
        continue: RUNNABLE                          [main线程释放锁, 子线程就能重新抢到锁, 进入RUNNABLE]
        run exit: TERMINATED                        [子线程run方法正常结束]
        */
        Object object = new Object();
        Thread thread = new Thread(() -> {
            synchronized (object) {
                System.out.println("waiting...");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("continue: " + Thread.currentThread().getState());   // RUNNABLE
            }
        });
        System.out.println("new: " + thread.getState());       // New
        thread.start();

        // 要先让子线程抢到锁, 由runnable进入等待状态
        Thread.sleep(100);
        synchronized (object){
            Thread.sleep(500);
            System.out.println("main hold lock: " + thread.getState());       // WAITING
            object.notify();
            System.out.println("main hold lock: " + thread.getState());       // BLOCKED
        }
        Thread.sleep(1000);
        System.out.println("run exit: " + thread.getState());        // TERMINATED
    }

    public static void waitingToBlocked() throws InterruptedException {
        /*
        先让子线程抢到锁, wait() 进入等待状态, 此时main抢到锁而让子线程进入 blocked, main释放锁后子线程才抢到锁, 由blocked进入 runnable
        new: NEW                                     [创建线程]
        waiting...                                   [调用wait方法]
        main hold lock: TIMED_WAITING                [wait的限时等待方法]
        main hold lock: BLOCKED                      [main线程抢到锁, 不释放. 子线程进入blocked]
        continue: RUNNABLE                           [main线程释放锁, 子线程就能重新抢到锁, 进入RUNNABLE]
        run exit: TERMINATED                         [子线程run方法正常结束]
        */
        Object object = new Object();
        Thread thread = new Thread(() -> {
            synchronized (object) {
                System.out.println("waiting...");
                try {
                    object.wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("continue: " + Thread.currentThread().getState());   // RUNNABLE
            }
        });
        System.out.println("new: " + thread.getState());       // New
        thread.start();

        // 要先让子线程抢到锁, 由runnable进入等待状态
        Thread.sleep(100);
        synchronized (object){
            Thread.sleep(500);
            System.out.println("main hold lock: " + thread.getState());       // TIMED_WAITING
            Thread.sleep(2000);
            System.out.println("main hold lock: " + thread.getState());       // BLOCKED
        }

        Thread.sleep(4000);
        System.out.println("run exit: " + thread.getState());        // TERMINATED
    }

    public static void main(String[] args) throws InterruptedException {
        directBlocked();
        waitToBlocked();
        waitingToBlocked();
    }
}
