package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * a class for ray tracing
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
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null)
            return scene.background;
        Point closestIntersection = ray.findClosestPoint(intersections);
        return calcColor(closestIntersection);
    }

    /**
     * A method to calculate the color of a given pixel.
     *
     * @param point the given point.
     **/
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
