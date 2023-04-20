package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Triangle class
 *
 * @author Uriel Dolev
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here for a triangle
        Triangle tri = new Triangle(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tri.getNormal(p1), "");
        // generate the test result
        Vector result = tri.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        assertTrue(isZero(result.dotProduct(p1.subtract(p2))), "Triangle's normal is not orthogonal to one of the edges");
        assertTrue(isZero(result.dotProduct(p1.subtract(p3))), "Triangle's normal is not orthogonal to one of the edges");
        assertTrue(isZero(result.dotProduct(p2.subtract(p3))), "Triangle's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: intersection point is inside the triangle (1 point)
        Point p = new Point(0.142857142857143, 0.142857142857143, 0.714285714285714);
        List<Point> result = triangle.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(1, 1, 1.5)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p), result, "Ray crosses triangle inside of it");
        // TC02: intersection point is outside against vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(1, 1, 3))),
                "Ray crosses the triangle's plane against a vertex");
        // TC03: intersection point is outside against edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(3, 1, 3))),
                "Ray crosses the triangle's plane against an edge");
        // =============== Boundary Values Tests ==================
        // TC11: intersection point is on a triangle's vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(2, 1, 1))),
                "Ray crosses the triangle at a vertex");
        // TC12: intersection point is on a triangle's edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(1.5, 1, 1.5))),
                "Ray crosses a triangle's edge");
        // TC13: intersection point is on a triangle's edge line (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(-1, -1, -1), new Vector(-1, 1, 4))),
                "Ray crosses the triangle's plane at a edge's line");
    }
}