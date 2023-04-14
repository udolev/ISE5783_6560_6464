package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * This class will represent a tube in a 3D world.
 */
public class Tube extends RadialGeometry {
    final protected Ray axisRay;

    /**
     * Constructor to initialize a tube with a radius and a ray.
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        axisRay = ray;
    }

    /**
     * Getter
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    public Vector getNormal(Point p) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        v=p.subtract(p0);
        return v.normalize();
    }
}
