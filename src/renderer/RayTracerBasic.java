package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * A class for ray tracing
 */
public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;


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
        GeoPoint intersection = findClosestIntersection(ray);
        return intersection == null ? scene.background : calcColor(intersection, ray);
    }

    /**
     * A method to calculate the color of a given geoPoint.
     *
     * @param gp the given geoPoint.
     **/
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).
                add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * A method to calculate the  source lights effect on a geoPoint color for a given ray.
     *
     * @param ray the ray.
     * @param gp  the geoPoint.
     **/
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
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
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcColorGLobalEffect(constructReflectedRay(gp, v, n), level, k, material.kR).add(calcColorGLobalEffect(constructRefractedRay(gp, v, n), level, k, material.kT));
    }

    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v.subtract(n.scale(v.dotProduct(n)).scale(2)), n);
    }

    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }

    private Color calcColorGLobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
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

//    private boolean unshaded(GeoPoint gp, LightSource ls, Vector l, Vector n) {
//        Vector lightDirection = l.scale(-1);  // from point to light source
//        Ray lightRay = new Ray(gp.point, lightDirection, n);
//
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(lightRay.getP0()));
//        if (intersections == null) return true;
//        for (GeoPoint intersection : intersections) {
//            if ((intersection.geometry.getMaterial().kT).equals(Double3.ZERO))
//                return false;
//        }
//        return true;
//    }

    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);  // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(lightRay.getP0()));
        if (intersections == null) return Double3.ONE;

        Double3 ktr = Double3.ONE;

        for (GeoPoint intersection : intersections) {
            ktr = ktr.product(intersection.geometry.getMaterial().kT);
        }
        return ktr;
    }
}
