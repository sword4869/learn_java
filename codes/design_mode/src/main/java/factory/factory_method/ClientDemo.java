package factory.abstract_factory;

public class ClientDemo {
    public static void main(String[] args) {
        /*
        与普通多态方法的区别就只是，把原本ClientDemo里的多态部分，交给工厂类了。

        意义就是，添加新产品时，客户不需要修改客户端的代码，只需要开发者修改工厂的代码
         */
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape = shapeFactory.createShape("Square");
        if (shape != null) {
            shape.draw();
        }
    }
}
