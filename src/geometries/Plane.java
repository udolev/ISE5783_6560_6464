package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * This class will represent a plane in a 3D world.
 * It will be used to represent all flat objects.
 */
public class Plane implements Geometry {
    final private Point q0;
    final private Vector normal;

    /**
     * Constructor to initialize Plane based object with three Points
     *
     * @param p1 first Point value
     * @param p2 second Point value
     * @param p3 third Point value
     */
    public Plane(Point p1, Point p2, Point p3) {
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
            throw new IllegalArgumentException("plane construction failed due to two of the points being the same");
        }
        // we'll check if all of the points are on the same straight line by checking if two of the vectors
        // constructed by subtracting two of the points are parallel
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        try {
            v1.crossProduct(v2);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("plane construction failed due to all of the points being on the same line");
        }

        normal = (v1.crossProduct(v2)).normalize();
        q0 = p1;
    }

    /**
     * Constructor to initialize Plane based object with a Vector and a Point
     *
     * @param v the plane's vector
     * @param p the plane's Point
     */
    public Plane(Point p, Vector v) {
        q0 = p;
        normal = v.normalize();
    }

    /**
     * Getters
     */
    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point p) {
        return getNormal();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector v = ray.getDir();
        Point p0 = ray.getP0();
        double nv = normal.dotProduct(v);
        // ray is parallel to the plane or p0 is q0 (to prevent zero vector)
        if (isZero(nv) || q0.equals(p0)) {
            return null;
        }

        double nQMinusP0 = normal.dotProduct(q0.subtract(p0));
        double t = alignZero(nQMinusP0 / nv);
        // ray's p0 is on the plane or the ray's direction vector is pointed at the other direction of the plane
        if (isZero(t) || t < 0) {
            return null;
        }

        return List.of(ray.getPoint(t));
    }
}
