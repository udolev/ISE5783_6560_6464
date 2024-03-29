package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Sphere class
 *
 * @author Uriel Dolev
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(0, 0, 1);
        double radius = 1;

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here for a sphere
        Sphere s = new Sphere(radius, p1);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> s.getNormal(p2), "");
        // generate the test result
        Vector result = s.getNormal(p2);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Sphere's normal is not a unit vector");
        // ensure the result is orthogonal to the tangent plane
        // we will check that the result vector is parallel to the vector 0 0 1 which we know is orthogonal to the tangent plane of p2
        assertThrows(IllegalArgumentException.class, () -> result.crossProduct(new Vector(0, 0, 1)), "Spheres normal is not orthogonal to the tangent plane");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(Ray ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        Point p3 = new Point(1.9114378277661477, 0.4114378277661476, 0.0);
        result = sphere.findIntersections(new Ray(new Point(1.5, 0, 0), new Vector(1, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p3), result, "Ray starts in sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(-1, 0, 0))),
                "Ray starts after the sphere");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        p3 = new Point(1, 1, 0);
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p3), result, "Ray starts at sphere and goes inside");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 8, 0))),
                "Ray starts at the sphere and goes outside");
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point(1, -1, 0);
        p2 = new Point(1, 1, 0);
        result = sphere.findIntersections(new Ray(new Point(1, 2, 0), new Vector(0, -1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray starts outside the sphere and goes through the center");
        // TC14: Ray starts at sphere and goes inside (1 points)
        p3 = new Point(1, -1, 0);
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, -1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p3), result, "Ray starts at the sphere and goes through the center");
        // TC15: Ray starts inside (1 points)
        p3 = new Point(1, 1, 0);
        result = sphere.findIntersections(new Ray(new Point(1, -0.5, 0), new Vector(0, 1, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p3), result, "Ray starts in the sphere and goes through the center");
        // TC16: Ray starts at the center (1 points)
        p3 = new Point(1.566138517072298, -0.226455406828919, 0.792593923901217);
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(5, -2, 7)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p3), result, "Ray starts at the center");
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Ray starts at the sphere and goes outside (and cutts center at the back)");
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                "Ray starts after the sphere and goes outside (and cutts center at the back)");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, -1), new Vector(0, 0, 1))),
                "Ray starts before the tangent point");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 0, 1))),
                "Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1))),
                "Ray starts at the tangent point");
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(1, 2, 0), new Vector(0, 0, 1))),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }

    /**
     * Test method for {@link geometries.Sphere#findGeoIntersections(Ray, double)}.
     */
    @Test
    void testFindGeoIntersections() {
        Sphere sphere = new Sphere(1d, new Point(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");
        // TC02: Ray intersects the sphere both intersection points is 'fully' in range (2 point)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Intersectable.GeoPoint> result = sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)), 100);
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).point.getX() > result.get(1).point.getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Intersectable.GeoPoint(sphere, p1), new Intersectable.GeoPoint(sphere, p2)), result, "Ray crosses sphere");
        // TC03: Ray intersects the sphere and one of the intersection points is 'fully' out of range (1 point)
        result = sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)), 2);
        assertEquals(List.of(new Intersectable.GeoPoint(sphere, p1)), result, "Ray crosses sphere and the distance limits it to one intersections");
        // TC04: Ray intersects the sphere and both intersection points is 'fully' out of range (0 point)
        assertNull(sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0)), 0.1), "Found intersections out of range");

        // =============== Boundary Values Tests ==================
        // TC11: Ray intersects the sphere and the first intersection point is on range (1 point)
        assertEquals(List.of(new Intersectable.GeoPoint(sphere, p1)), sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)),
                p1.distance(new Point(-1, 0, 0))), "Ray crosses plane and intersection point is on range");
    }
}