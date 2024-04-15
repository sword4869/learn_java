package factory.abstract_factory;

public class SquareFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Square();
    }

    @Override
    public Color setColor() {
        return new Green();
    }
}
