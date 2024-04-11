package thread_basic.stopThread;

public class StopByStopFlag {
    // 不能这么写，因为局部变量 isStopped 会被JIT优化为True，必须通过 volatile 的实例或静态变量来可见
    /*
    public static void stopByFlag() throws InterruptedException {
        boolean isStopped = false;
        boolean finalisStopped = isStopped;
        new Thread(()->{
            while(!finalisStopped){
                System.out.println("running...");
            }
            System.out.println("stopped...");
        }).start();

        Thread.sleep(10);
        isStopped = true;
    }

    public static void main(String[] args) throws InterruptedException {
        stopByFlag();
    }
    */

    volatile static boolean isStopped = false;
    volatile boolean isStopped2 = false;

    // 静态变量的 stopflag
    public static void stopByFlag() throws InterruptedException {
        new Thread(()->{
            while(!isStopped){
                System.out.println("running...");
            }
            System.out.println("stopped...");
        }).start();

        Thread.sleep(10);
        new Thread(()->{
            isStopped = true;
            System.out.println("set stop");
        }).start();
    }

    // 实例变量的 stopflag
    public void stopByFlag2() throws InterruptedException {
        new Thread(()->{
            while(!isStopped2){
                System.out.println("running...");
            }
            System.out.println("stopped...");
        }).start();

        Thread.sleep(10);
        isStopped2 = true;
    }

    // 用线程自身的中断状态作为 stopflag
    public static void interruptAsStopFlag() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("running...");
            }
        });
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
        System.out.println("stop...");
    }


    public static void main(String[] args) throws InterruptedException {
        stopByFlag();
        // new StopByStopFlag().stopByFlag2();
        // interruptAsStopFlag();
    }
}
