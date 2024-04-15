package other;

public class ThreadLocalTest {
    public static void main(String[] args) {
        /*
        Thread B World  
        Thread A Hello
        Thread B null       [remove()方法执行后，ThreadLocalMap中的Entry对象被置为null]
        Thread A null
         */
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        new Thread(()->{
            threadLocal.set("Hello");
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
            threadLocal.remove();
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        }, "Thread A").start();

        new Thread(()->{
            threadLocal.set("World");
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
            threadLocal.remove();
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        }, "Thread B").start();
    }
}
