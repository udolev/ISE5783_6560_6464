package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Cylinder class
 * @author Uriel Dolev
 */

class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Vector dir = new Vector(0,0,1);
        Ray ray = new Ray(new Point(0,0,0), dir);
        double radius = 3, height = 10;

        Cylinder c = new Cylinder(radius, ray, height);

        // ============ Equivalence Partitions Tests ==============
        // TC01: The point is on the round surface of the cylinder
        // ensure there are no exceptions
        Point p1 = new Point(3,0,5);
        assertDoesNotThrow(() -> c.getNormal(p1), "");
        // generate the test result
        Vector result1 = c.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result1.length(), 0.00000001, "Cylinder's normal to a round surface is not a unit vector");
        // ensure the result is orthogonal to the ray's direction vector
        assertTrue(isZero(result1.dotProduct(dir)), "Cylinder's normal to a round surface point is not orthogonal to its ray's direction vector");

        // TC02: The point is on the bottom base of the cylinder
        // ensure there are no exceptions
        Point p2 = new Point(2,1,0);
        assertDoesNotThrow(() -> c.getNormal(p2), "");
        // generate the test result
        Vector result2 = c.getNormal(p2);
        // ensure |result| = 1
        assertEquals(1, result2.length(), 0.00000001, "Cylinder's normal to a bottom base point is not a unit vector");
        // ensure the result is parallel to the ray's direction vector
        assertThrows(IllegalArgumentException.class, () -> result2.crossProduct(dir), "Cylinder's normal to a bottom base point is not parallel to its ray's direction vector");

        // TC03: The point is on the upper base of the cylinder
        // ensure there are no exceptions
        Point p3 = new Point(2,1,10);
        assertDoesNotThrow(() -> c.getNormal(p3), "");
        // generate the test result
        Vector result3 = c.getNormal(p3);
        // ensure |result| = 1
        assertEquals(1, result3.length(), 0.00000001, "Cylinder's normal to an upper base point is not a unit vector");
        // ensure the result is parallel to the ray's direction vector
        assertThrows(IllegalArgumentException.class, () -> result3.crossProduct(dir), "Cylinder's normal to an upper base point is not parallel to its ray's direction vector");

        // =============== Boundary Values Tests ==================
        // TC11: The point is the center of the bottom base
        // ensure there are no exceptions
        Point centerP1 = new Point(0,0,0);
        assertDoesNotThrow(() -> c.getNormal(centerP1), "");
        // generate the test result
        Vector vr1 = c.getNormal(centerP1);
        // ensure |result| = 1
        assertEquals(1, vr1.length(), 0.00000001, "Cylinder's bottom center normal is not a unit vector");
        // ensure that the result is orthogonal to the tube's direction vector
        assertThrows(IllegalArgumentException.class, () -> vr1.crossProduct(dir), "Cylinder's normal to bottom base center point is not parallel to its ray's direction vector");

        // TC12: The point is the center of the upper base
        // ensure there are no exceptions
        Point centerP2 = new Point(0,0,10);
        assertDoesNotThrow(() -> c.getNormal(centerP2), "");
        // generate the test result
        Vector vr2 = c.getNormal(centerP2);
        // ensure |result| = 1
        assertEquals(1, vr2.length(), 0.00000001, "Cylinder's upper center normal is not a unit vector");
        // ensure that the result is orthogonal to the tube's direction vector
        assertThrows(IllegalArgumentException.class, () -> vr2.crossProduct(dir), "Cylinder's normal to upper base center point is not parallel to its ray's direction vector");
    }
}