package factory.easy_factory;

public class ShapeFactory{
    public Shape createShape(String type) {
        if ("Square".equals(type)) {
            return new Square();
        } else if ("Rectangle".equals(type)) {
            return new Rectangle();
        } else {
            return null;
        }
    }
}
