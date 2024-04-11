package src.main.java.task;

public class 单次顺序执行 {
    public static void main(String[] args) {
        // 创建线程对象
        Thread t1 = new Thread(() -> {
            System.out.println("t1");
        }) ;

        Thread t2 = new Thread(() -> {
            // join会抛出异常
            try {
                t1.join();                          // 加入线程t1,只有t1线程执行完毕以后，再次执行该线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2");               // T2的任务
        }) ;


        Thread t3 = new Thread(() -> {
            try {
                t2.join();                          // 加入线程t2,只有t2线程执行完毕以后，再次执行该线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3");               // T3的任务
        }) ;

        // 启动线程
        t1.start();
        t2.start();
        t3.start();
    }
}
