package threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Arguments {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                0,  // 核心线程数
                2,  // 最大线程数
                10,  // 空闲线程存活时间
                TimeUnit.SECONDS,   // 时间单位
                new ArrayBlockingQueue<>(3),   // 阻塞队列
                Executors.defaultThreadFactory(),   // 线程工厂
                new ThreadPoolExecutor.DiscardPolicy()
        );  // 拒绝策略
        for (int i = 0; i < 8; i++) {
            pool.submit(new MyRunnable());
            // Thread.sleep(100);                // <<<<<<<<<<<<<<<<<<<
        }

        String finished = "finished submission...";

        int j = 0;
        while (j <= 90000000000L) {
            if(j == 0){
                System.out.println(finished + "\n" + pool.getQueue().size() + " " + pool.getQueue());
            }
            else{
                System.out.println(pool.getQueue().size() + " " + pool.getQueue());
            }
            j++;
            if(pool.getQueue().size() == 1){
                break;
            }
        }
        pool.shutdown();
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + this);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}