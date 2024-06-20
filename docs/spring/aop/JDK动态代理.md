

JDK动态代理使用Java**反射机制**生成代理对象。我们只需要**定义目标接口**和**实现目标接口的目标类**，JDK动态代理会在运行时在内存中动态地创建一个实现目标接口的代理类。


`java.lang.reflect` 包中的两个类 `Proxy`和`InvocationHandler`
-  `InvocationHandler`：实现该接口的`invoke`方法，定义横切逻辑，然后通过反射机制调用目标类的代码。动态的将横切逻辑和业务逻辑编织在一起。 
- `Proxy`: 类是所有动态代理类的父类，它有一个名为 `newProxyInstance`的方法，这个方法生成动态代理对象
  方法接收三个参数： 
  - `ClassLoader loader`类加载器（目标接口的）
  - `Class<?>[] interfaces` 目标接口
  - `InvocationHandler h`调用处理器对象



特点：

​	**实现简单**，无需为每个接口手动创建对应的代理类。

​	**跨平台**，JDK动态代理是基于Java反射机制实现的，因此具有跨平台的特性。

​	**性能开销**，因为反射机制，不适合高频使用。

​	**仅支持接口代理**。

```java
// 目标接口
public interface MyInterface {
    void doSomething();
}

// 目标对象
public class MyInterfaceImpl implements MyInterface {
    @Override
    public void doSomething() {
        System.out.println("Doing something...");
    }
}

public class LoggingAspect implements InvocationHandler {
    private Object target;

    // 注入目标对象
    public LoggingAspect(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before method: " + method.getName());
        // method指定目标方法，target指定目标对象
        Object result = method.invoke(target, args);
        System.out.println("After method: " + method.getName());
        return result;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        // 使用JDK动态代理创建代理对象
        MyInterface myInterface = new MyInterfaceImpl();
        InvocationHandler handler = new LoggingAspect(myInterface);
        MyInterface myService = (MyInterface) Proxy.newProxyInstance(
                myInterface.getClass().getClassLoader(),
                myInterface.getClass().getInterfaces(),
                handler);
        
        
        // 使用JDK动态代理创建代理对象
        MyInterface myService = (MyInterface) Proxy.newProxyInstance(
                MyInterface.class.getClassLoader(),
                new Class<?>[]{MyInterface.class},
                handler);

        // 调用代理对象的方法
        myService.doSomething();
    }
}
```

