package src.main.java.create_thread;

public class ImplementsRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ImplementsRunnable runnable = new ImplementsRunnable();
        // Runable实现类对象交给Thread构造参数
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }
}
