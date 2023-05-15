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

    // constructor
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    public abstract Color traceRay(Ray ray);
}
