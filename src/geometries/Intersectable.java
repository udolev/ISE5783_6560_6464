package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/** This interface will be used for intersectable objects and collections. */
public interface Intersectable {
    /** The function will return the intersection points between a ray and an intersectable.
     * @param ray the ray of which we will find the intersections. */
    List<Point> findIntersections(Ray ray);
}
