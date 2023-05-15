package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a collection of intersectables.
 */

public class Geometries extends Intersectable {
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

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> lst = null;
        for (Intersectable intersectable : intersectables) {
            List<Point> p = intersectable.findIntersections(ray);
            if (p != null) {
                if (lst == null) lst = new LinkedList<>();
                lst.addAll(p);
            }
        }
        return lst;
    }
}
