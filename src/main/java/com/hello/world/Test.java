package com.hello.world;

import java.io.Serializable;
import java.lang.annotation.Annotation;

@Deprecated
public final class Test implements Serializable{
    public static void main(String[] args) throws ClassNotFoundException {
        Class clazz = Test.class;

        // 获取类的全限定名
        System.out.println(clazz.getName()); // com.hello.world.Test

        // 获取类的简单名
        System.out.println(clazz.getSimpleName()); // Test

        // 获取类的包名
        System.out.println(clazz.getPackage().getName()); // com.hello.world

        // 获取类的父类
        System.out.println(clazz.getSuperclass()); // class java.lang.Object

        // 获取类的接口
        Class[] interfaces = clazz.getInterfaces();
        for (Class c : interfaces) {
            System.out.println(c); // interface java.io.Serializable
        }

        // 获取类的修饰符: 0-默认不写, 1-public, 2-private, 4-protected, 8-static, 16-final, 32-synchronized, 64-volatile, 128-transient, 256-native, 512-interface, 1024-abstract, 2048-strict, 4096-synthetic, 8192-annotation, 16384-enum
        System.out.println(clazz.getModifiers()); // 17

        // 获取类的类加载器
        ClassLoader classLoader = clazz.getClassLoader();
        System.out.println(classLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2

        // 获取类的注解
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation); // @java.lang.Deprecated(forRemoval=false, since="")
        }
    }
}
