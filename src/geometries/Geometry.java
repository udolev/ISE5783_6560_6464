package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * This interface will be a basic form for all geometries.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Getter to receive the geometry's emission color.
     **/
    public Color getEmission() {
        return emission;
    }

    /**
     * Setter to initialize/set the emission color of the geometry.
     *
     * @param emission the emission color of the geometry.
     **/
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }


    /**
     * The function will return the normal to a geometric object in some point p.
     *
     * @param p the point we will find the normal to.
     */
    public abstract Vector getNormal(Point p);
}
