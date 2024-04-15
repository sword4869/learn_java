package singleton;

public class Singleton5 {
    private Singleton5(){}

    private static class SingletonHolder{
        private static final Singleton5 SINGLETON = new Singleton5();   // final
    }
    public static final Singleton5 getInstance(){   // final
        return SingletonHolder.SINGLETON;
    }
}
