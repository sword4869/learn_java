import java.util.Scanner;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Student2 {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            System.out.println("Enter your name: ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 123;
        }, threadPool).thenApply((r)->{
            int i = 1/0;
            System.out.println("thenApply: " + r);
            return r+2;
        }).handle((r, e)->{     // 处理上一个阶段的异常
            System.out.println(r);
            if( e != null){
                System.out.println( "handle: " + r + " " + e.getMessage());
                return 1;
            }
            return r+2;
        }).thenApply((r)->{
            System.out.println("thenApply: " + r);
            return r+2;
        })
        .whenComplete((r, e) -> {
            System.out.println("Your name is: " + r);
        }).exceptionally((e)->{
            System.out.println("exception: " + e.getMessage());
            return null;
        });

        System.out.println("main thread");
        // 该方法不会阻塞主线程
        // 线程池不关闭，主线程会一直等待
        // 之前提交的任务会继续执行，但是不会再接受新的任务。
        threadPool.shutdown();
        System.out.println("main thread");
    }
}