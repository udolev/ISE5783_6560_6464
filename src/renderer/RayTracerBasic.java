package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import scene.Scene;


/**
 * A class for ray tracing
 */
public class RayTracerBasic extends RayTracerBase {
    //constructor
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * A method to generate the color for a ray.
     *
     * @param ray the given ray.
     **/
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return scene.background;
        GeoPoint closestIntersection = ray.findClosestGeoPoint(intersections);
        return calcColor(closestIntersection);
    }

    /**
     * A method to calculate the color of a given geoPoint.
     *
     * @param gp the given geoPoint.
     **/
    private Color calcColor(GeoPoint gp) {
        return scene.ambientLight.getIntensity()
                .add(gp.geometry.getEmission());
    }
}
