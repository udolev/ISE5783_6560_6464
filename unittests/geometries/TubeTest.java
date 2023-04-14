package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Tube class
 *
 * @author Uriel Dolev
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Vector dir = new Vector(0,0,1);
        Ray ray = new Ray(new Point(0,0,0), dir);
        double radius = 3;

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here for a tube
        Tube t = new Tube(radius, ray);
        // ensure there are no exceptions
        Point p1 = new Point(3,0,1);
        assertDoesNotThrow(() -> t.getNormal(p1), "");
        // generate the test result
        Vector result = t.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Tube's normal is not a unit vector");
        // ensure the result is orthogonal to the ray's direction vector
        assertTrue(isZero(result.dotProduct(dir)), "Tube's normal is not orthogonal to its ray's direction vector");

        // =============== Boundary Values Tests ==================
        // TC11: The point is on the bottom of the tube so (p-p0) is orthogonal to dir
        // ensure there are no exceptions
        Point p2 = new Point(3,0,0);
        assertDoesNotThrow(() -> t.getNormal(p2), "");
        // generate the test result
        Vector vr = t.getNormal(p2);
        // ensure |result| = 1
        assertEquals(1, vr.length(), 0.00000001, "Tube's normal is not a unit vector");
        // ensure that the result is orthogonal to the tube's direction vector
        assertTrue(isZero(vr.dotProduct(dir)), "Tube's normal to a bottom point incorrect");
    }
}