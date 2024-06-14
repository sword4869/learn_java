package syn;

public class Clazz {
    public static void main(String[] args) {
        Student student = new Student();
        new Thread(() -> {
            synchronized (student) {
                System.out.println("Thread 1");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1 done");
            }
        }).start();
        
        new Thread(() -> {
            synchronized (Student.class) {
                System.out.println("Thread 2");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2 done");
            }
        }).start();

        new Thread(() -> {
            synchronized (Student.sObject) {
                System.out.println("Thread 3");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 3 done");
            }
        }).start();
    }
}

class Student{
    public Object mObject;
    public static Object sObject = new Object();
}