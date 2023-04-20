package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * This class will represent a triangle in a 3D world.
 */
public class Triangle extends Polygon {
    /**
     * Constructor to initialize a triangle using 3 points.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // find intersection with the triangle's plane
        List<Point> intersect = plane.findIntersections(ray);
        if (intersect == null)
            return null;
        Point p = intersect.get(0);
        // check if the intersection point is inside the triangle
        Point p0 = vertices.get(0);
        Point p1 = vertices.get(1);
        Point p2 = vertices.get(2);
        if (p.equals(p0) || p.equals(p1) || p.equals(p2))
            return null;

        Vector v0 = p1.subtract(p0);
        Vector v1 = p2.subtract(p1);
        Vector v2 = p0.subtract(p2);

        Vector n1;
        Vector n2;
        Vector n3;
        try {
            n1 = v0.crossProduct(p0.subtract(p));
            n2 = v1.crossProduct(p1.subtract(p));
            n3 = v2.crossProduct(p2.subtract(p));
        } catch (IllegalArgumentException e) {
            return null;
        }

        if (n1.dotProduct(n2) > 0 && n1.dotProduct(n3) > 0 && n2.dotProduct(n3) > 0) {
            return List.of(p);
        }

        return null;
    }
}