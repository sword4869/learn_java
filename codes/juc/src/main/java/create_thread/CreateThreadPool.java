package src.main.java.create_thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CreateThreadPool {
    public static void main(String[] args) {
        ImplementsRunnable runnable = new ImplementsRunnable();
        ImplementsCallabe callable = new ImplementsCallabe();

        // 1.获取线程池对象
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 2.提交任务
        executorService.submit(runnable);
        // submit的返回结果用 Future接口获取。
        Future<String> future = executorService.submit(callable);
        try {
            String s = future.get();
            System.out.println(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 3. 必须销毁线程池！不shutdown的话，程序不会结束
        executorService.shutdown();
    }
}
/*
// 创建有上限的线程池
ExecutorService pool1 = Executors.newFixedThreadPool(3);
// 创建一个没有上限的线程池
ExecutorService pool2 = Executors.newCachedThreadPool();
// 单例模式
ExecutorService pool3 = Executors.newSingleThreadExecutor();
 */