package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * This class will represent a cylinder in a 3D world.
 */
public class Cylinder extends Tube {
    final private double height;

    /**
     * Constructor to initialize a cylinder with a radius, a ray and a height value
     */
    public Cylinder(double radius, Ray ray, double h) {
        super(radius, ray);
        height = h;
    }

    /**
     * Getter
     */
    public double getHeight() {
        return height;
    }

    public Vector getNormal(Point p) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        double t = v.dotProduct(p.subtract(p0));
        Point o = p0;
        if (t != 0) {
            o = p0.add(v.scale(t));
        }
        v=p.subtract(o);
        return v.normalize();

    }
}
