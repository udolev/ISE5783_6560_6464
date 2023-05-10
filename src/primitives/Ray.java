package primitives;

import java.util.List;

import static primitives.Util.isZero;

/**
 * This will represent a ray in a 3D world.
 * A ray will be represented by a head point and a direction vector.
 */
public class Ray {
    final private Point p0;
    final private Vector dir;

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

    public Point findClosestPoint(List<Point> points) {
        if (points == null)
            return null;

        Point closestPoint = points.get(0);
        double shortestDistance = p0.distance(closestPoint);
        double d;

        for (Point point : points) {
            d = p0.distance(point);
            if (d < shortestDistance) {
                shortestDistance = d;
                closestPoint = point;
            }
        }

        return closestPoint;
    }
}
