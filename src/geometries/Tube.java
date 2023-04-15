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
        double t = v.dotProduct(p.subtract(p0));
        Point o = p0;
        if (t != 0) {
            o = p0.add(v.scale(t));
        }
        Vector n = p.subtract(o);
        return n.normalize();
    }
}