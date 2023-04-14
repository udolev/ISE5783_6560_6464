package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * This class will represent a sphere in a 3D world.
 */
public class Sphere extends RadialGeometry {
    final private Point center;

    /**
     * Constructor to initialize a sphere with a center point and a radius.
     */
    public Sphere(double r, Point p) {
        super(r);
        center = p;
    }

    /**
     * Getter
     */
    public Point getCenter() {
        return center;
    }

    public Vector getNormal(Point p) {
        Vector v = p.subtract(center);
        return v.normalize();
    }
}
