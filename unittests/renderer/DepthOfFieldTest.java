package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * this class will be used to test the depth of field feature.
 */
public class DepthOfFieldTest {
    // This test will generate a sphere and a triangle behind it, while the focus is on the sphere
    @Test
    void testDOF() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(700).
                setApertureSize(10).setFocalPlaneDistance(300).enableAdaptiveSuperSampling();
        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(new Sphere(30d, new Point(0, 0, 0)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(15d, new Point(0, 0, 0)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                new Triangle(new Point(0, 0, -100), new Point(0, 70, -100), new Point(70, 0, -100)).setEmission(new Color(ORANGE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)));

        scene.lights.add(new SpotLight(new Color(350, 200, 200), new Point(0, 0, 500), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(1E-6));

        ImageWriter imageWriter = new ImageWriter("Depth of field", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    //this test will generate a picture of two spheres, one in focus and without it.
    @Test
    void testDOF2() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(700).
                setApertureSize(10).setFocalPlaneDistance(350);
        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(new Sphere(30d, new Point(0, 0, 0)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Sphere(15d, new Point(-50, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));

        scene.lights.add(new SpotLight(new Color(350, 200, 200), new Point(0, 0, 500), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(1E-6));

        ImageWriter imageWriter = new ImageWriter("Depth of field (2)", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    // this test will create a picture of a cube made of blue spheres, with a green plane behind it.(DOF)
    @Test
    void test10ObjectsDOF() {
        Camera camera = new Camera(new Point(400, 0, 400), new Vector(-1, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(300).setApertureSize(8).setFocalPlaneDistance(185).enableAdaptiveSuperSampling();

        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        Material planeMaterial = new Material().setKd(0.5).setKs(0.5);
        Color planeColor = new Color(GREEN);
        scene.geometries.add(generateSpheresCube(), new Plane(new Point(0, -50, 0), new Vector(-1, 0, -1)).
                setEmission(planeColor).setMaterial(planeMaterial));

        scene.lights.add(new SpotLight(new Color(350, 200, 200), new Point(1000, 0, 400), new Vector(-1, 0, -1))//
                .setKl(4E-5).setKq(1E-6));
        scene.lights.add(new DirectionalLight(new Color(WHITE), new Vector(1, 0, -1)));
        scene.lights.add(new PointLight(new Color(WHITE), new Point(0, 1000, 0)));

        ImageWriter imageWriter = new ImageWriter("Depth Of Field Final", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }

    private static final int SPHERES_IN_LINE = 5;
    private static final int SINGLE_SPHERE_RADIUS = 10;
    private static final Material SPHERE_MATERIAL = new Material().setKd(0.5).setKs(0.5).setShininess(100).setKr(0.1);

    /**
     * this function will generate a cube made of spheres.
     */
    Geometries generateSpheresCube() {
        Geometries spheresCube = new Geometries();
        double coordinateDelta = SINGLE_SPHERE_RADIUS * 2;
        Point leftTop3d = new Point(-SINGLE_SPHERE_RADIUS * (SPHERES_IN_LINE - 1), SINGLE_SPHERE_RADIUS * (SPHERES_IN_LINE - 1), SINGLE_SPHERE_RADIUS * (SPHERES_IN_LINE - 1));
        Point leftTop2d, startOfLine, current;
        for (int i = 0; i < SPHERES_IN_LINE; ++i) {
            leftTop2d = leftTop3d.add(new Point(0, 0, -coordinateDelta * i));
            for (int j = 0; j < SPHERES_IN_LINE; ++j) {
                startOfLine = leftTop2d.add(new Point(0, -coordinateDelta * j, 0));
                for (int k = 0; k < SPHERES_IN_LINE; ++k) {
                    current = startOfLine.add(new Point(coordinateDelta * k, 0, 0));
                    spheresCube.add(new Sphere(SINGLE_SPHERE_RADIUS, current).setEmission(new Color(BLUE)).setMaterial(SPHERE_MATERIAL));
                }
            }
        }
        return spheresCube;
    }

    // this test will generate a picture of 5 reflective spheres in 5 different colors, on a black and white floor (while using DOF).
    @Test
    void testSpheresFloorDOF() {
        Camera camera = new Camera(new Point(40, 100, 20), new Vector(1, 1, 0), new Vector(0, 0, 1)) //
                .setVPSize(7, 7).setVPDistance(5).setApertureSize(1.5).setFocalPlaneDistance(60).setNumOfRaysInLine(9);

        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        Geometries floor = new Geometries();
        for (int i = 0; i < 300; i += 20) {
            for (int j = 0; j < 300; j += 20) {
                floor.add(new Polygon(new Point(i, j, 0), new Point(i + 20, j, 0), new Point(i + 20, j + 20, 0), new Point(i, j + 20, 0)).
                        setEmission((i % 40 == 0 ? ((j % 40 == 0) ? new Color(white) : new Color(BLACK)) : (j % 40 != 0) ? new Color(WHITE) : new Color(BLACK))));
            }
        }
        int shininess = 301;
        scene.geometries.add(floor);
        scene.geometries.add(new Sphere(20, new Point(100, 160, 20))
                .setEmission(new Color(BLUE)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(shininess).setKr(0.5)));
        scene.geometries.add(new Sphere(20, new Point(180, 160, 20))
                .setEmission(new Color(RED)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(shininess).setKr(0.5)));
        scene.geometries.add(new Sphere(20, new Point(140, 160, 20))
                .setEmission(new Color(GREEN)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(shininess).setKr(0.5)));
        scene.geometries.add(new Sphere(20, new Point(60, 160, 20))
                .setEmission(new Color(75, 0, 130)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(shininess).setKr(0.5)));
        scene.geometries.add(new Sphere(20, new Point(220, 160, 20))
                .setEmission(new Color(yellow)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(shininess).setKr(0.5)));
        scene.lights.add(new DirectionalLight(new Color(WHITE), new Vector(0, 1, 0)));
        scene.lights.add(new DirectionalLight(new Color(white), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("Reflective Spheres on Black-White Floor with DOF", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }

}
