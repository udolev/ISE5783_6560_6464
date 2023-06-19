package primitives;

import java.util.LinkedList;
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

    /**
     * a method to go through a list of geopoints and find the one closest to the ray's head.
     *
     * @param intersections the list of points
     * @return
     */
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

    /**
     * a method to check if a certain point is further away from the ray's head as a given maximum distance.
     *
     * @param point       the given point
     * @param maxDistance the maximum distance
     * @return
     */
    public boolean inRange(Point point, double maxDistance) {
        double t = p0.distance(point);
        if (alignZero(t - maxDistance) <= 0) return true;
        return false;
    }

    /**
     * a method to generate a beam of rays from every point in a given list to one target point.
     *
     * @param points the list of points
     * @param target the target point
     * @return a list of rays from each point to the target point
     */

    static public List<Ray> generateRayBeamToPoint(List<Point> points, Point target) {
        List rayBeam = new LinkedList<Ray>();
        Vector direction;
        for (Point point : points) {
            direction = target.subtract(point);
            rayBeam.add(new Ray(point, direction));
        }
        return rayBeam;
    }

    /**
     * a method to generate a beam of rays from a head point to a given list of points.
     *
     * @param points the list of points
     * @param head the head point
     * @return a list of rays to each point from the head point
     */
    static public List<Ray> generateRayBeamFromPoint(List<Point> points, Point head) {
        List rayBeam = new LinkedList<Ray>();
        Vector direction;
        for (Point point : points) {
            direction = point.subtract(head);
            rayBeam.add(new Ray(head, direction));
        }
        return rayBeam;
    }
}
