package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}