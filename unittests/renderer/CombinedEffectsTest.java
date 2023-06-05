package renderer;

import geometries.Polygon;
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

public class CombinedEffectsTest {
    @Test
    void generateTest() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(700);
        Scene scene = new Scene("Test scene");
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.geometries.add( //
                new Polygon(new Point(0, 100, -100), new Point(100, 100, -80),
                        new Point(100, -100, -80), new Point(0,-100,-100)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.8)), //
                new Polygon(new Point(0, 100, -100), new Point(-100, 100, -80),
                        new Point(-100, -100, -80), new Point(0,-100,-100)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.8)), //
                new Sphere(30d, new Point(0, 0, 0)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(30).setKt(0.6)),
                new Triangle(new Point(0,10,0), new Point(0,-10,0), new Point(-10,-10,0)).
                setEmission(new Color(GREEN).scale(2)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)));

        scene.lights.add(new SpotLight(new Color(350, 200, 200), new Point(15, 15, 70), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(1E-6));

        ImageWriter imageWriter = new ImageWriter("combinedEffects", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
