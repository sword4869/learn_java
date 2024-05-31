public class Test {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat();
        Dog.bark();
    }
}

class Dog{
    // 静态变量
    public static String name = "Tommy";

    // 成员变量
    public String color = "black";

    // 静态方法: 只能访问静态变量。
    // 不可以访问成员变量，也不可以调用成员方法。
    public static void bark(){
        System.out.println(name + " is barking");
    }

    // 成员方法：可以访问类中的所有成员（静态变量、成员变量、调用静态方法）
    public void eat(){
        System.out.println(name + " is " + color);
        bark();
    }
}