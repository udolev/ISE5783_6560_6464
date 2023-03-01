package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
    final private double height;

    public Cylinder(double radius, Ray ray, double h) {
        super(radius, ray);
        height = h;
    }

    // Getter
    public double getHeight() {
        return height;
    }

    public Vector getNormal(Point p) {
        return null;
    }
}
