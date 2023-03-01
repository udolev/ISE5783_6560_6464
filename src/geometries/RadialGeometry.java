package geometries;

public abstract class RadialGeometry implements Geometry {
    final protected double radius;

    public RadialGeometry(double r) {
        radius = r;
    }

    public double getRadius() {
        return radius;
    }
}
