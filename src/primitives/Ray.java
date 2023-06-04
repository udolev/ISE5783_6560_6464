package primitives;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import geometries.Intersectable.GeoPoint;

/**
 * This will represent a ray in a 3D world.
 * A ray will be represented by a head point and a direction vector.
 */
public class Ray {
    final private Point p0;
    final private Vector dir;

    private static final double DELTA = 0.1;

    /**
     * Constructor to initialize Ray object with its head point and direction vector
     *
     * @param p head of the ray
     * @param v direction of the ray
     */
    public Ray(Point p, Vector v) {
        p0 = p;
        dir = v.normalize();
    }

    public Ray(Point head, Vector direction, Vector normal) {
        double vn = direction.dotProduct(normal);
        if (isZero(vn))
            p0 = head;
        else
            p0 = head.add(normal.scale(vn > 0 ? DELTA : -DELTA));
        dir = direction.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return p0.equals(other.p0) && dir.equals(other.dir);
        return false;
    }

    @Override
    public String toString() {
        return "p0=" + p0 + ", dir=" + dir;
    }

    public Point getPoint(double t) {
        if (isZero(t))
            return p0;
        return p0.add(dir.scale(t));
    }

    /**
     * The function finds the closest point to the head ray out of a list of points.
     *
     * @param points list of points from which we will find the closest.
     * @return the closest point to the head of the ray.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null ? null : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        if (intersections == null)
            return null;

        GeoPoint closestGeoPoint = intersections.get(0);
        double shortestDistance = p0.distance(closestGeoPoint.point);
        double d;

        for (GeoPoint intersection : intersections) {
            d = p0.distance(intersection.point);
            if (d < shortestDistance) {
                shortestDistance = d;
                closestGeoPoint = intersection;
            }
        }

        return closestGeoPoint;
    }

    public boolean inRange(Point point, double maxDistance) {
        double t = p0.distance(point);
        if (alignZero(t - maxDistance) <= 0) return true;
        return false;
    }
}
