package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {
    final private Ray axisRay;

    public Tube(double radius, Ray ray) {
        super(radius);
        axisRay = ray;
    }

    // Getter
    public Ray getAxisRay() {
        return axisRay;
    }

    public Vector getNormal(Point p) {
        return null;
    }
}
