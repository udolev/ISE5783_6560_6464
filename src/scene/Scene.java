package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * this class will handle all the scene aspects, such as light and the geometries.
 */
public class Scene {
    // scene name
    public final String name;
    // background color
    public Color background;
    // background light
    public AmbientLight ambientLight;
    // the 3D objects in scene
    public Geometries geometries;
    // scene light sources
    public List<LightSource> lights = new LinkedList<>();
    /**
     * Constructor to initialize the scene with its name.
     *
     * @param name  scene name.
     **/
    public Scene(String name) {
        this.name = name;
        background = Color.BLACK;
        ambientLight = AmbientLight.NONE;
        geometries = new Geometries();
    }

    // Setters
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

}
