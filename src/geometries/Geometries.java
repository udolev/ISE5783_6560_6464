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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;
        for (Intersectable intersectable : intersectables) {
            var geoIntersections = intersectable.findGeoIntersections(ray);
            if (geoIntersections != null) {
                if (intersections == null) intersections = new LinkedList<>();
                intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }
}
