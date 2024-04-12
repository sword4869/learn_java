package thread_basic.thread_state;

public class DoubleStart {
    private static void startAgainWhenRunnable() {
        /*
        RUNNABLE的线程（这里通过中断标志作为stopflag），不能再次start

        startAgainWhenRunnable: RUNNABLE
        java.lang.IllegalThreadStateException
         */
        Thread thread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
            }
        });
        thread.start();

        try {
            System.out.println("startAgainWhenRunnable: " + thread.getState());
            thread.start();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            thread.interrupt();
        }
    }

    private static void startAgainWhenTerminated(){
        /*
        TERMINATED的线程，不能再次start

        startAgainWhenTerminated: TERMINATED
        java.lang.IllegalThreadStateException
         */
        Thread thread = new Thread(() -> {
        });
        thread.start();
        try {
            Thread.sleep(100);
            System.out.println("startAgainWhenTerminated: " + thread.getState());
            thread.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args){
        startAgainWhenRunnable();
        startAgainWhenTerminated();
    }
}
