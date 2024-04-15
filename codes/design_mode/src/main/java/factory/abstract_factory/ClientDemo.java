package factory.abstract_factory;

public class ClientDemo{
    public static void main(String[] args) {
        ShapeColorFactory shapeColorFactory = new RectangleFactory();
        Shape shape = shapeColorFactory.createShape();
        Color color = shapeColorFactory.setColor();
        shape.draw();
        color.setColor();

        ShapeColorFactory shapeColorFactory2 = new SquareFactory();
        Shape shape2 = shapeColorFactory2.createShape();
        Color color2 = shapeColorFactory.setColor();
        shape2.draw();
        color2.setColor();
    }
}
