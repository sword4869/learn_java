package thread_basic.volatile_test;

public class JIT {
    static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        /*
        stop变量被修改, 并不会被别的线程察觉到. 因为JIT优化  while (!stop) 成 while(true) 了.
        第二个线程察觉到, 是因为使用 System.out.print, 内部会获取 synchronized, 从而JIT不会优化这里.
        第三个线程就是直接使用 synchronized 来演示.

        sout stopped...                 []
        main：modify stop to true...
        synchronized stopped...
                                        [线程1循环不停止]
         */
        new Thread(() -> {
            while (!stop) {

            }
            System.out.println("no sout stopped...");
        }).start();

        new Thread(() -> {
            while (!stop) {
                System.out.print("");
            }
            System.out.println("sout stopped...");
        }).start();

        new Thread(() -> {
            synchronized(JIT.class){
                while (!stop) {
                    System.out.print("");
                }
                System.out.println("synchronized stopped...");
            }
        }).start();

        // 另起一个别的线程也一样.
        Thread.sleep(100);
        stop = true;
        System.out.println(Thread.currentThread().getName() + "：modify stop to true...");
    }
}
