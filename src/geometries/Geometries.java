package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a collection of intersectables.
 */

public class Geometries implements Intersectable {
    final private List<Intersectable> intersectables;

    public Geometries() {
        intersectables = new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable... geometries) {
        intersectables = List.of(geometries);
    }

    public void add(Intersectable... geometries) {
        intersectables.addAll(List.of(geometries));
    }

    public List<Point> findIntersections(Ray ray) {
        // Firstly, we check that there are any intersections
        Boolean isIntersected = Boolean.FALSE;
        for (Intersectable intersectable : intersectables) {
            if (!(intersectable.findIntersections(ray) == null))
                isIntersected = Boolean.TRUE;
        }
        if (!isIntersected)
            return null;
        List<Point> intersections = new LinkedList<Point>();
        for (Intersectable intersectable : intersectables) {
            if (!(intersectable.findIntersections(ray) == null))
                intersections.addAll(intersectable.findIntersections(ray));
        }
        return intersections;
    }
}
