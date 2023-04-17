package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Plane class
 *
 * @author Uriel Dolev
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}.
     */
    @Test
    void testPointsConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test that constructing a regular plane works
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 0, 0);
        try {
            new Plane(p1, p2, p3);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct (Double3) vector");
        }

        // =============== Boundary Values Tests ==================
        // TC11: two of the points are the same point
        Point p = new Point(1,1,1);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p,p,p1), "plane construction failed due to two of the points being the same");
        // TC12: all of the points are on the same straight line
        Point p4 = new Point(0,0,2);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1,p3,p4), "plane construction failed due to all of the points being on the same line");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here for a plane
        Plane plane = new Plane(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(p1), "");
        // generate the test result
        Vector result = plane.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        assertTrue(isZero(result.dotProduct(p1.subtract(p2))), "Plane's normal is not orthogonal to one of the edges");
        assertTrue(isZero(result.dotProduct(p1.subtract(p3))), "Plane's normal is not orthogonal to one of the edges");
        assertTrue(isZero(result.dotProduct(p2.subtract(p3))), "Plane's normal is not orthogonal to one of the edges");
    }
    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections(Ray ray) {
    }
}