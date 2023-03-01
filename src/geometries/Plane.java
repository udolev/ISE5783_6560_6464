package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    final private Point q0;
    final private Vector normal;

    public Plane(Point p1, Point p2, Point p3) {
        normal = null;
        q0 = p1;
    }

    public Plane(Point p, Vector v) {
        q0 = p;
        normal = v.normalize();
    }

    // Getters
    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    public Vector getNormal(Point p) {
        return normal;
    }
}
