package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;

public class DepthOfFieldTest {
    @Test
    void testDOF(){
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(700).setApertureSize(10).setFocalPlaneDistance(300);
        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(new Sphere(30d, new Point(0, 0, 0)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(15d, new Point(0, 0, 0)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                new Triangle(new Point(0,0,-100), new Point(0,70,-100), new Point(70,0,-100)).setEmission(new Color(ORANGE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30)));

        scene.lights.add(new SpotLight(new Color(350, 200, 200), new Point(0, 0, 500), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(1E-6));

        ImageWriter imageWriter = new ImageWriter("Depth of field", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
