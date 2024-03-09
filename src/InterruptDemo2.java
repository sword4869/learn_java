import java.util.concurrent.TimeUnit;

/**
 * @author Guanghao Wei
 * @create 2023-04-11 11:13
 * 执行interrupt方法将t1标志位设置为true后，t1没有中断，仍然完成了任务后再结束
 * 在2000毫秒后，t1已经结束称为不活动线程，设置状态为没有任何影响
 */
public class InterruptDemo2 {
    public static void main(String[] args) {
        //实例方法interrupt()仅仅是设置线程的中断状态位为true，不会停止线程
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 300; i++) {
                System.out.println("------: " + i);
            }
            /**
             * ------: 298
             * ------: 299
             * ------: 300
             * t1线程调用interrupt()后的中断标志位02：true
             */
            System.out.println("t1线程调用interrupt()后的中断标志位02：" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1线程默认的中断标志位：" + t1.isInterrupted());//false

        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();//true
        /**
         * ------: 251
         * ------: 252
         * ------: 253
         * t1线程调用interrupt()后的中断标志位01：true
         */
        System.out.println("t1线程调用interrupt()后的中断标志位01：" + t1.isInterrupted());//true

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //2000毫秒后，t1线程已经不活动了，不会产生任何影响
        System.out.println("t1线程调用interrupt()后的中断标志位03：" + t1.isInterrupted());//false

    }
}