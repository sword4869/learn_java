import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Guanghao Wei
 * @create 2023-04-11 10:29
 *         使用interrupt() 和isInterrupted()组合使用来中断某个线程
 */
public class InterruptDemo {
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    static int i = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " isInterrupted()的值被改为true，t1程序停止");
                    break;
                }
                System.out.println("-----------hello isInterrupted()");
                i++;
                // 方法1：内部t1.interrupt()
                if (i == 100) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "t1");
        t1.start();

        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 方法2：外部t1.interrupt()
        t1.interrupt();
        // 方法3：t2向t1放出协商，将t1中的中断标识位设为true，希望t1停下来
        new Thread(() -> t1.interrupt(), "t2").start();
    }
}
/**
 * -----------hello isInterrupted()
 * -----------hello isInterrupted()
 * -----------hello isInterrupted()
 * -----------hello isInterrupted()
 * t1 isInterrupted()的值被改为true，t1程序停止
 */