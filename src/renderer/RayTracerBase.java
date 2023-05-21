package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * An abstract class for ray tracing
 */
public abstract class RayTracerBase {
    // the scene
    protected Scene scene;

    /**
     * A constructor to initialize a RayTracerBase object with a scene
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * A method that gets a ray and finds the color of the pixel the ray came out of.
     * @param ray the given ray.
     * @return the traced ray's color.
     */
    public abstract Color traceRay(Ray ray);
}