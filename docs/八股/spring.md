- [三级缓存怎么解决循环依赖](#三级缓存怎么解决循环依赖)
- [spring注解](#spring注解)
- [spring的aop是如何实现的](#spring的aop是如何实现的)
- [AOP 优点](#aop-优点)
- [Spring AOP概念](#spring-aop概念)
- [Spring 动态代理失效的场景有哪些？](#spring-动态代理失效的场景有哪些)
- [怎么证明Spring Boot 动态代理默认实现是 JDK 动态代理还是 CGLIB？](#怎么证明spring-boot-动态代理默认实现是-jdk-动态代理还是-cglib)


---

## 三级缓存怎么解决循环依赖

## spring注解

![alt text](../../images/image-131.png)



## spring的aop是如何实现的


Spring AOP的实现方式有两种：**动态代理和字节码操作**。
- 动态代理: **JDK动态代理和CGLIB动态代理**

    如果被代理的目标对象实现了 `InvocationHandler` 接口，Spring AOP将使用JDK动态代理来创建代理对象；如果没有实现接口，将使用CGLIB动态代理来创建代理对象。

   - JDK 动态代理：通过反射来接收被代理的类，使用 JDK 的 java.lang.reflect.Proxy 类来创建代理对象。
   - CGLIB 动态代理：CGLIB 则是一个代码生成的类库，CGLIB 是基于继承的代理，通过CGLIB库生成目标对象的子类来实现代理

- 字节码操作：AspectJ AOP
    
    AspectJ 是一个独立的AOP框架，Spring可以集成AspectJ。
    
    通过使用AspectJ注解或XML配置来修改字节码。



## AOP 优点

1. 模块化

    通过将公共行为（如日志记录、事务管理）提取为独立的切面，可以使代码更加模块化，避免在多个地方重复编写相同的代码，提高代码的可重用性、可维护性和可读性。

2. 解耦

    将业务逻辑与横切关注点分离，使得非功能性需求（如日志记录、事务管理、安全检查等）可以集中管理和维护，而不是分散在各个业务模块中。从而降低业务逻辑的耦合性，提高程序的可扩展性。

## Spring AOP概念

在Spring中，AOP通过将切面织入到目标对象的方法中，从而实现横切关注点的模块化。

当目标对象的方法被调用时，AOP代理会在方法执行前、执行后或抛出异常时执行切面的相关逻辑，实现横切关注点的功能，如日志记录、事务管理等。
-   **关键概念**：
    -   **切面（Aspect）**：切面是一个包含了横切关注点声明的模块化单元，它可以有多个切入点和通知组成。
    -   **切入点（Pointcut）**：切入点定义了匹配通知应该被织入的方法或方法执行点的规则表达式。
    -   **通知（Advice）**：通知是在特定切入点处执行的代码片段，分为多种类型，如前置通知（Before advice）、后置通知（After returning advice）、异常后通知（After throwing advice）、最终通知（After (finally) advice）以及环绕通知（Around advice）。
-   **织入（Weaving）**：织入是指将切面应用到目标对象来创建一个新的代理对象的过程。在 Spring AOP 中使用动态代理，织入发生在运行时。
-   **代理工厂**：Spring 内部通过 ProxyFactory、Advisor、AdvisorChainFactory 等来创建和管理代理对象。
-   **执行流程**：当客户端通过代理对象调用目标方法时，代理对象会拦截这个调用，根据切面配置找到对应的通知，根据通知类型执行不同的增强逻辑。
  
## Spring 动态代理失效的场景有哪些？

## 怎么证明Spring Boot 动态代理默认实现是 JDK 动态代理还是 CGLIB？