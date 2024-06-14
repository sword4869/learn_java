package syn;

public class Clazz2 {
    public static void main(String[] args) {
        Student2 student = new Student2();
        new Thread(() -> {
            Student2.sFunction();
        }).start();
        new Thread(() -> {
            student.mFunction();
        }).start();
    }
}

class Student2{
    public Object mObject;
    public static Object sObject = new Object();

    public synchronized void mFunction(){
        System.out.println("mFunction");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("mFunction done");
    }

    public synchronized static void sFunction(){
        System.out.println("sFunction");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sFunction done");
    }
}