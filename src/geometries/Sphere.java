package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    @Override
    public Vector getNormal(Point p) {
        Vector v = p.subtract(center);
        return v.normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) { return null; }
}
