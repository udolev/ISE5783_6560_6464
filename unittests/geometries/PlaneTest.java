package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Plane class
 * @author Uriel Dolev
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Point p1 = new Point(0,0,1);
        Point p2 = new Point(1,0,0);
        Point p3 = new Point(0,0,0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here for a plane
        Plane plane = new Plane(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(p1), "");
        // generate the test result
        Vector result = plane.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the plane's direction vectors
        assertTrue(isZero(result.dotProduct(p1.subtract(p2))),"Plane's normal is not orthogonal to one of the direction vectors");
        assertTrue(isZero(result.dotProduct(p1.subtract(p3))),"Plane's normal is not orthogonal to one of the direction vectors");
        assertTrue(isZero(result.dotProduct(p2.subtract(p3))),"Plane's normal is not orthogonal to one of the direction vectors");
    }
}