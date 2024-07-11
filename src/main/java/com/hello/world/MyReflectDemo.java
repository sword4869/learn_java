package com.hello.world;
import java.lang.reflect.Parameter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MyReflectDemo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        /*
         * 【Class类中用于获取构造方法的方法】
         * Constructor<?>[] getConstructors() 
         *			返回所有public的构造方法（包括父类）
         * Constructor<?>[] getDeclaredConstructors()
         * 			返回所有类自己的构造方法（public、protected、缺省、private）（不包括父类）
         * 
         * Constructor<T> getConstructor(Class<?>... parameterTypes) 返回单个public构造方法
         * Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes) 返回单个自己的构造方法
         * 
         * 
         * 【Constructor类中的成员方法】
         * int getModifiers() 修饰符: 0-默认权限, 1-public, 2-private, 4-protected
         * Parameter[] getParameters() 参数
         * 
         * void setAccessible(boolean flag) 设置为true,表示取消访问检查
         * T newInstance(Object ... initargs) 根据指定的构造方法创建对象: 如果是private修饰的，需要先调用setAccessible(true)方法
         */

        // 1.获取class字节码文件对象
        Class class1 = Student.class;

        // 2.获取构造方法
        Constructor[] cons1 = class1.getConstructors();
        for (Constructor con : cons1) {
            System.out.println(con);
        }
        // public com.hello.world.Student()


        Constructor[] cons2 = class1.getDeclaredConstructors();
        for (Constructor con : cons2) {
            System.out.println(con);
        }
        // private com.hello.world.Student(java.lang.String,int)
        // protected com.hello.world.Student(int)
        // com.hello.world.Student(java.lang.String)
        // public com.hello.world.Student()

        Constructor con1 = class1.getConstructor();
        System.out.println(con1);
        // public com.hello.world.Student()

        Constructor con2 = class1.getDeclaredConstructor(String.class);
        System.out.println(con2);
        // com.hello.world.Student(java.lang.String)

        // 【Constructor类中的成员方法】
        Constructor con = class1.getDeclaredConstructor(int.class);
        int modifiers = con.getModifiers();
        System.out.println(modifiers);
        // 4

        Parameter[] parameters = con.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter);
        }
        // com.hello.world.Student(java.lang.String)
        // 4

        con.setAccessible(true);
        Student stu = (Student) con.newInstance(23);
    }
}

class Person {
    private String name;
    private int age;

    public Person() {
    }

    Person(String name) {
        this.name = name;
    }

    protected Person(int age) {
        this.age = age;
    }

    private Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Student extends Person {
    public Student() {
    }

    Student(String name) {
        super(name);
    }

    protected Student(int age) {
        super(age);
    }

    private Student(String name, int age) {
        super(age);
    }
}