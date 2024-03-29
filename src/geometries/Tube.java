package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * This class will represent a tube in a 3D world.
 */
public class Tube extends RadialGeometry {
    final protected Ray axisRay;

    /**
     * Constructor to initialize a tube with a radius and a ray.
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        axisRay = ray;
    }

    /**
     * Getter
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public Vector getNormal(Point p) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        double t = v.dotProduct(p.subtract(p0));
        Point o = p0;
        if (t != 0) {
            o = p0.add(v.scale(t));
        }
        Vector n = p.subtract(o);
        return n.normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Tube other)
            return super.equals(other) && axisRay.equals(other.axisRay);
        return false;
    }
}