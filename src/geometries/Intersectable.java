package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * This interface will be used for intersectable objects and collections.
 */
public abstract class Intersectable {
    /**
     * The function will return the intersection points between a ray and an intersectable.
     *
     * @param ray the ray of which we will find the intersections.
     */
    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * PDS class representing a point and the geometry it is on.
     */
    public static class GeoPoint {
        // The geometry of the point
        public Geometry geometry;
        // The point
        public Point point;

        /**
         * Constructor to initialize a geoPoint object
         *
         * @param geometry the geometry of the point.
         * @param point    the point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof GeoPoint other)
                return point.equals(other.point) && geometry.equals(other.geometry);
            return false;
        }

        @Override
        public String toString() {
            return geometry.getClass() + point.toString();
        }
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
