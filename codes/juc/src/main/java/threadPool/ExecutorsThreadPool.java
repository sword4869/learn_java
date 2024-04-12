package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsThreadPool {
    public static void main(String[] args) {
        newFixedThreadPool();
        newCachedThreadPool();
        newSingleThreadExecutor();
        newScheduledThreadPool();
    }

    public static void newFixedThreadPool(){
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for(int i = 0 ; i < 3; i++){
            pool.submit(()->{
                System.out.println("newFixedThreadPool");
            });
        }
        pool.shutdown();
    }

    public static void newCachedThreadPool(){
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i = 0 ; i < 3; i++){
            pool.submit(()->{
                System.out.println("newCachedThreadPool");
            });
        }
        pool.shutdown();
    }

    public static void newSingleThreadExecutor(){
        AtomicInteger count = new AtomicInteger();
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for(int i = 0 ; i < 3; i++){
            pool.submit(()->{
                System.out.println("newSingleThreadExecutor "+ count.get());
                count.getAndIncrement();
            });
        }
        pool.shutdown();
    }

    public static void newScheduledThreadPool(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        for(int i = 0 ; i < 6; i++){
            pool.schedule(()->{
                System.out.println("newScheduledThreadPool");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, i, TimeUnit.SECONDS);
        }
        pool.shutdown();
    }
}
