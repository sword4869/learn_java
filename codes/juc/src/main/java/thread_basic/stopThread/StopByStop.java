package thread_basic.stopThread;

public class StopByStop {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("running...");
            }
        });
        thread.start();
        Thread.sleep(10);
        thread.stop();
        System.out.println("stop...");
    }
}
