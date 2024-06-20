# IoC控制反转是什么

原本由程序自己主动去创建所依赖的对象，现在由IoC容器负责容器的创建和注入，IoC容器将创建好的依赖对象注入程序就行。

依赖注入是IoC的另一个说法，侧重描述IoC容器负责管理对象之间的依赖关系。

比如，Service对象内要创建Dao → Service只需接受IoC容器注入其创建好的Dao对象。

![image-20240619211631388](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406192116448.png)

[Spring第一课:IOC控制反转，什么是反转，什么又是控制？_spring框架中的控制反转ioc是指-CSDN博客](https://blog.csdn.net/qq_41376740/article/details/82454121)

```java
public class BusinessService {
	// 创建所依赖的对象
    private UserDao dao = new UserDao();

    private User findUser() {
        return dao.getUser("1");
    }
    
    public static void main(String[] args) {
        BusinessService businessService = new BusinessService();
        businessService.findUser();
    }
}
```

```java
// 伪IoC
public class BusinessService {
    // 等着注入就行
    private UserDao dao;
    public BusinessService(UserDao dao) {
        this.dao = dao;
    }

    public User findUser() {
        return dao.getUser("1");
    }
    
    public static void main(String[] args) {
        UserDao dao = new UserDao();
        BusinessService businessService = new BusinessService(dao);
        businessService.findUser();
    }
}
```

