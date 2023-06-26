package renderer;

import geometries.*;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;


public class AntialiasingTest {
    private Intersectable sphere = new Sphere(60d, new Point(0, 0, -200))                                         //
            .setEmission(new Color(BLUE))                                                                                  //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
    private Material trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);

    private Scene scene = new Scene("Test scene");
    private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
            .setVPSize(200, 200).setVPDistance(1000)                                                                       //
            .setRayTracer(new RayTracerBasic(scene));

    /**
     * Helper function for the tests in this module
     */
    void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
        scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        camera.enableAntialiasing().setNumOfRaysInLine(20).setImageWriter(new ImageWriter(pictName, 1000, 1000)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void testShadowWithAntialiasing() {
        sphereTriangleHelper("Shadow With Antialiasing", //
                new Triangle(new Point(-60, -30, 0), new Point(-30, -60, 0), new Point(-58, -58, -4)), //
                new Point(-100, -100, 200));
    }

    private final Scene scene1 = new Scene("Test scene").setBackground(new Color(WHITE));
    private final Camera camera1 = new Camera(new Point(0, 0, 1000),
            new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(200, 200).setVPDistance(1500).enableAntialiasing();
    @Test
    void testAntialiasing() {
        scene1.geometries.add(new Sphere(50, new Point(0,0,-50))
                .setEmission(new Color(BLACK)));

        ImageWriter imageWriter = new ImageWriter("Black Sphere without AA", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }
}
