package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Geometries class
 *
 * @author Uriel Dolev
 */

class GeometriesTest {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}.
     */
    @Test
    void findIntersections() {
        Sphere sphere = new Sphere(3, new Point(1, 0, 0));
        Triangle triangle = new Triangle(new Point(-5, 0, -5), new Point(-5, -5, 2), new Point(-5, 5, 2));
        Plane plane = new Plane(new Point(-10, 0, 0), new Vector(1, 0, 0));
        Geometries geometries = new Geometries(sphere, triangle, plane);
        // ============ Equivalence Partitions Tests ==============
        // TC01: of a collection, some shapes are intersected but not all
        List<Point> result = geometries.findIntersections(new Ray(new Point(5, 0, 0), new Vector(-1, -0.5, 0.5)));
        // ensure that |result| = 3 (sphere - 2, triangle - 0, plane - 1)
        assertEquals(3, result.size(), "Wrong number of intersections");
        // =============== Boundary Values Tests ==================
        // TC11: intersection with an empty collection
        assertNull(new Geometries().findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 2, 3))),
                "empty collection gives intersection points");
        // TC12: no shape is intersected
        assertNull(geometries.findIntersections(new Ray(new Point(5, 0, 0), new Vector(1, -0.5, 0.5))),
                "Wrong number of intersections");
        // TC13: only one shape is intersected
        result = geometries.findIntersections(new Ray(new Point(5, 0, 0), new Vector(-1, -20, 0.5)));
        // ensure that |result| = 1 (sphere - 0, triangle - 0, plane - 1)
        assertEquals(1, result.size(), "Wrong number of intersections");
        // TC14: all of the shapes are intersected
        result = geometries.findIntersections(new Ray(new Point(5, 0, 0), new Vector(-2, -0.5, 0)));
        // ensure that |result| = 4 (sphere - 2, triangle - 1, plane - 1)
        assertEquals(4, result.size(), "Wrong number of intersections");
    }
}