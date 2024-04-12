package threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Policy {
    public static void main(String[] args) {
        AbortPolicy();
        DiscardPolicy();
        DiscardOldestPolicy();
        CallerRunsPolicy();
    }

    private static void AbortPolicy() {
        /*
         * 提交5个任务，而该线程池最多可以处理4个任务，当我们使用AbortPolicy这个任务处理策略的时候，就会抛出异常
         * Exception in thread "main" java.util.concurrent.RejectedExecutionException:
         * Task java.util.concurrent.FutureTask@6ce253f1[Not completed, task =
         * java.util.concurrent.Executors$RunnableAdapter@87aac27[Wrapped task =
         * threadPool.Policy$$Lambda$1/0x0000010b56000a00@3e3abc88]] rejected from
         * java.util.concurrent.ThreadPoolExecutor@53d8d10a[Running, pool size = 3,
         * active threads = 3, queued tasks = 1, completed tasks = 0]
         * at java.base/java.util.concurrent.ThreadPoolExecutor$AbortPolicy.
         * rejectedExecution(ThreadPoolExecutor.java:2065)
         * at
         * java.base/java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.
         * java:833)
         * at
         * java.base/java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.
         * java:1365)
         * at java.base/java.util.concurrent.AbstractExecutorService.submit(
         * AbstractExecutorService.java:123)
         * at threadPool.Policy.abort(Policy.java:19)
         * at threadPool.Policy.main(Policy.java:10)
         * pool-1-thread-1---->> 执行了任务
         * pool-1-thread-3---->> 执行了任务
         * pool-1-thread-2---->> 执行了任务
         * pool-1-thread-3---->> 执行了任务
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 3, 20, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(0), new ThreadPoolExecutor.AbortPolicy());

        for (int x = 0; x < 5; x++) {
            threadPoolExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "---->> 执行了任务");
            });
        }
        threadPoolExecutor.shutdown();
    }

    private static void DiscardPolicy() {
        /*
         * 控制台没有报错，仅仅执行了4个任务，有一个任务被丢弃了
         * pool-1-thread-1---->> 执行了任务
         * pool-1-thread-3---->> 执行了任务
         * pool-1-thread-2---->> 执行了任务
         * pool-1-thread-1---->> 执行了任务
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 3, 20, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy());

        for (int x = 0; x < 5; x++) {
            threadPoolExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "---->> 执行了任务");
            });
        }
        threadPoolExecutor.shutdown();
    }

    private static void DiscardOldestPolicy() {
        /*
         * 由于任务1在线程池中等待时间最长，因此任务1被丢弃。
         * pool-2-thread-2---->> 执行了任务2
         * pool-2-thread-1---->> 执行了任务0
         * pool-2-thread-3---->> 执行了任务3
         * pool-2-thread-2---->> 执行了任务4
         */
        ThreadPoolExecutor threadPoolExecutor;
        threadPoolExecutor = new ThreadPoolExecutor(1, 3, 20, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int x = 0; x < 5; x++) {
            // 定义一个变量，来指定指定当前执行的任务;这个变量需要被final修饰
            final int y = x;
            threadPoolExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "---->> 执行了任务" + y);
            });
        }
        threadPoolExecutor.shutdown();
    }

    private static void CallerRunsPolicy() {
        /*
        通过控制台的输出，我们可以看到次策略没有通过线程池中的线程执行任务，而是直接调用任务的run()方法绕过线程池直接执行。
        pool-1-thread-2---->> 执行了任务
        pool-1-thread-1---->> 执行了任务
        pool-1-thread-3---->> 执行了任务
        main---->> 执行了任务
        pool-1-thread-2---->> 执行了任务
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 3, 20, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.CallerRunsPolicy());

        // 提交5个任务
        for (int x = 0; x < 5; x++) {
            threadPoolExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "---->> 执行了任务");
            });
        }
        threadPoolExecutor.shutdown();
    }
}
