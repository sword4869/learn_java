package factory.ordinary;

public class ClientDemo {
    public static void main(String[] args) {
        Shape shape = createShape("Square");
        if (shape != null) {
            shape.draw();
        }
    }

    public static Shape createShape(String type) {
        if ("Square".equals(type)) {
            return new Square();
        } else if ("Rectangle".equals(type)) {
            return new Rectangle();
        } else {
            return null;
        }
    }
}
