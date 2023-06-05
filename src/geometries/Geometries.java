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

    /**
     * A default contractor.
     **/
    public Geometries() {
        intersectables = new LinkedList<Intersectable>();
    }

    /**
     * A contractor to initialise a new Geometries object with a list of intersectables.
     **/
    public Geometries(Intersectable... geometries) {
        intersectables = List.of(geometries);
    }

    /**
     * A method to add new intersectables objects to a given geometries item.
     **/
    public void add(Intersectable... geometries) {
        intersectables.addAll(List.of(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable intersectable : intersectables) {
            var geoIntersections = intersectable.findGeoIntersections(ray, maxDistance);
            if (geoIntersections != null) {
                if (intersections == null) intersections = new LinkedList<>();
                intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }
}
