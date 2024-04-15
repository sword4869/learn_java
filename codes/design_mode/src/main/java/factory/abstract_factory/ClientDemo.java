package factory.factory_method;

public class ClientDemo{
    public static void main(String[] args) {
        /*
        不需要修改工厂的代码，只需告知客户使用新的具体工厂。
         */
        ShapeFactory shapeFactory = new RectangleFactory();
        Shape shape = shapeFactory.createShape();
        shape.draw();

        ShapeFactory shapeFactory2 = new SquareFactory();
        Shape shape2 = shapeFactory2.createShape();
        shape.draw();
    }
}
