package singleton;

public class Singleton4 {
    private Singleton4(){}

    private volatile static Singleton4 singleton = null;        // volatile

    public static Singleton4 getInstance(){
        if(singleton == null){
            synchronized (Singleton4.class){
                if(singleton == null){
                    singleton = new Singleton4();
                }
            }
        }
        return singleton;
    }
}
