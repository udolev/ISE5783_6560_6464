package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import static primitives.Util.alignZero;


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
        if (intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * A method to calculate the color of a given geoPoint.
     *
     * @param gp the given geoPoint.
     **/
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(gp, ray));
    }

    /**
     * A method to calculate the  source lights effect on a geoPoint color for a given ray.
     *
     * @param ray the ray.
     * @param gp  the geoPoint.
     **/
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * A method to calculate the diffusive part of the lights effect.
     *
     * @param nl       n*l parameters for calculation.
     * @param material the objects material.
     **/
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * A method to calculate the specular part of the lights effect.
     *
     * @param nl       n*l - to simplify use of n and l.
     * @param v        a vector from the camera to thr point.
     * @param l        the light's direction.
     * @param n        the normal tho the geometry at a specific point.
     * @param material the objects material.
     **/
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        // the reflectance vector
        Vector r = l.subtract(n.scale(2 * nl));
        double vr = -v.dotProduct(r);
        return material.kS.scale((vr > 0) ? Math.pow(vr, material.nShininess) : 0);
    }
}
