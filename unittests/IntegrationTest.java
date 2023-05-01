import static org.junit.jupiter.api.Assertions.*;

import geometries.Plane;
import geometries.Triangle;
import org.junit.jupiter.api.Test;

import geometries.Geometry;
import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;

import java.util.List;

public class IntegrationTest {
    private static final int nX = 3;
    private static final int nY = 3;
    private static final int W = 3;
    private static final int H = 3;
    private static final int d = 1;
    static final Point ZERO_POINT = new Point(0, 0, 0);

    @Test
    void testSphereIntegration() {
        // TC01: check that the amount of intersections with a small sphere in front of the camera is correct (2 points)
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera.setVPSize(W, H);
        camera.setVPDistance(d);
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        assertEquals(2, countIntersections(nX, nY, sphere, camera), "Sphere integration: wrong amount of intersections");

        // TC02: check that the amount of intersections with a big sphere in front of the camera is correct (18 points)
        camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 1, 0), new Vector(0, 0, -1));
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        assertEquals(18, countIntersections(nX, nY, sphere, camera), "Sphere integration: wrong amount of intersections");

        // TC03: check that the amount of intersections with a medium sphere in front of the camera is correct (10 points)
        sphere = new Sphere(2, new Point(0, 0, -2));
        assertEquals(10, countIntersections(nX, nY, sphere, camera), "Sphere integration: wrong amount of intersections");

        // TC04: check that the amount of intersections when the camera is inside a big sphere is correct (9 points)
        sphere = new Sphere(4, new Point(0, 0, -2));
        assertEquals(9, countIntersections(nX, nY, sphere, camera), "Sphere integration: wrong amount of intersections");

        // TC05: check that the amount of intersections when the sphere is behind the camera is correct (0 points)
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0, countIntersections(nX, nY, sphere, camera), "Sphere integration: wrong amount of intersections");
    }

    @Test
    void testPlaneIntegration() {
        // TC01: check that the amount of intersections with a plane which is orthogonal to the camera and all pixels' rays intersect is correct (9 points)
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera.setVPSize(W, H);
        camera.setVPDistance(d);
        Plane plane = new Plane(new Point(0, 0, -4), new Vector(0, 0, 1));
        assertEquals(9, countIntersections(nX, nY, plane, camera), "Plane integration: wrong amount of intersections");

        // TC02: check that the amount of intersections with a random plane where all pixels' rays intersect is correct (9 points)
        plane = new Plane(new Point(0, 1.5, -1), new Vector(0, -1.5, 1));
        assertEquals(9, countIntersections(nX, nY, plane, camera), "Plane integration: wrong amount of intersections");

        // TC03: check that the amount of intersections with a plane which is parallel to some of the pixel's rays is correct (6 points)
        plane = new Plane(new Point(0, 1.5, -1), new Vector(0, 1, -1));
        assertEquals(9, countIntersections(nX, nY, plane, camera), "Plane integration: wrong amount of intersections");
    }

    @Test
    void testTriangleIntegration() {
        // TC01: check that the amount of intersections with a small close triangle which is orthogonal to the camera is correct (1 points)
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera.setVPSize(W, H);
        camera.setVPDistance(d);
        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(1, countIntersections(nX, nY, triangle, camera), "Plane integration: wrong amount of intersections");

        // TC02: check that the amount of intersections with a bigger, close, thin triangle which is orthogonal to the camera is correct (2 points)
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, countIntersections(nX, nY, triangle, camera), "Plane integration: wrong amount of intersections");
    }

    // Help functions
    int countIntersections(int nX, int nY, Geometry geometry, Camera camera) {
        int intersectionsCounter = 0;
        Ray ray;
        List<Point> lst;
        for (int j = 0; j < nX; ++j) {
            for (int i = 0; i < nY; ++i) {
                ray = camera.constructRay(nX, nY, j, i);
                lst = geometry.findIntersections(ray);
                if (lst != null)
                    intersectionsCounter += lst.size();
            }
        }
        return intersectionsCounter;
    }
}
