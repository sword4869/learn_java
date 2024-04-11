package src.main.java.create_thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ImplementsCallabe implements Callable<String> {
    @Override
    public String call() {
        // 可以声明抛出异常
        // public String call() throws Exception {
        return Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        ImplementsCallabe callable = new ImplementsCallabe();
        // FutureTask是Future的实现类
        FutureTask<String> ft = new FutureTask<>(callable);
        Thread thread1 = new Thread(ft);
        Thread thread2 = new Thread(ft);
        thread1.start();
        thread2.start();

        // 调用 FutureTask 的get方法获取执行结果
        String s = null;
        try {
            s = ft.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(s);
    }

}