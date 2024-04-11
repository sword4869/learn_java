package src.main.java.create_thread;

public class ExtendsThread extends Thread{
    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ExtendsThread thread1 = new ExtendsThread();
        ExtendsThread thread2 = new ExtendsThread();

        // 调用start方法启动线程
        thread1.start();
        thread2.start();
        // Thread-0
        // Thread-1
    }
}
