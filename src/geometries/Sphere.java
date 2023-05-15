package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

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
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        // if ray starts at the center then there is only one intersection point
        if (center.equals(p0)) {
            return List.of(ray.getPoint(radius));
        }

        Vector u = center.subtract(p0);
        double tm = u.dotProduct(v);
        double d = alignZero(Math.sqrt(u.dotProduct(u) - tm * tm));
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        } else if (t1 > 0 && (t2 < 0 || isZero(t2))) {
            return List.of(ray.getPoint(t1));
        } else if ((t1 < 0 || isZero(t1)) && t2 > 0) {
            return List.of(ray.getPoint(t2));
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Sphere other)
            return super.equals(other) && center.equals(other.center);
        return false;
    }
}
