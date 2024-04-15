package factory.abstract_factory;

public class RectangleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Rectangle();
    }

    @Override
    public Color setColor() {
        return new Blue();
    }
}
