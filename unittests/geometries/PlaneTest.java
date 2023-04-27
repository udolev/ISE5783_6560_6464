package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        Point p = new Point(1, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p, p, p1), "plane construction failed due to two of the points being the same");
        // TC12: all of the points are on the same straight line
        Point p4 = new Point(0, 0, 2);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p3, p4), "plane construction failed due to all of the points being on the same line");
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
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane (1 point)
        Point p1 = new Point(1.416666666666667, 0, 1.291666666666667);
        Plane plane = new Plane(new Point(0, 1, 0), new Point(1, 0, 1), new Point(4, 7, 1));
        List<Point> result = plane.findIntersections(new Ray(new Point(4, 0, 0), new Vector(-2, 0, 1)));
        assertEquals(List.of(p1), result, "Ray crosses plane");
        // TC02: Ray does not intersect with the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(4, 0, 0), new Vector(2, 0, 1))), "Ray does not cross the plane");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC11: ray is not included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(4, 0, 0), new Vector(1, -1, 1))), "Ray is parallel to the plane");
        // TC12: ray is included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(4, 7, 1), new Vector(1, -1, 1))), "Ray is included in the plane");
        // **** Group: Ray is orthogonal to the plane
        // TC13: p0 is on the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(4, 7, 1), new Vector(-7, 3, 10))), "Ray is orthogonal to the plane and starts at it");
        // TC14: the ray stars after the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(-10, 2, 0), new Vector(-7, 3, 10))), "Ray is orthogonal to the plane and starts after it");
        // TC15: the ray starts before thr plane (1 point)
        p1 = new Point(7.0316455696202524, 3.2721518987341773, 4.240506329113924);
        result = plane.findIntersections(new Ray(new Point(10, 2, 0), new Vector(-7, 3, 10)));
        assertEquals(List.of(p1), result, "Ray is orthogonal to the plane and starts before it");
        // TC16: the ray is not orthogonal nor parallel to plane and stars at it (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(4, 7, 1), new Vector(7, 3, 3))), "ray is not orthogonal nor parallel to plane and stars at it ");
        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in
        // the same point which appears as reference point in the plane(p0) (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 0), new Vector(7, 3, 3))), "ray is not orthogonal nor parallel to plane and stars at its reference point(p0) ");
    }
}