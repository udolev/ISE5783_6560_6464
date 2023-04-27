package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

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

    @Override
    public Vector getNormal(Point p) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        // in case of the center point we'll return the direction vector
        if (p.equals(p0)) {
            return v;
        } else {
            double t = v.dotProduct(p.subtract(p0));
            Point o = p0;
            // if the point is on one of the bases
            if (isZero(t) || t == height) { // we decided that if it is on the base circle (the edge of the base), the normal will be the base normal
                return v;
            } else {
                return super.getNormal(p);
            }
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) { return null; }
}

