package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 *
 * @author Uriel Dolev
 */
class RayTest {
    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void findClosestPoint() {
        Ray ray = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: closest point is in the middle of the list
        List<Point> points = List.of(new Point(3, 0, 0), new Point(2, 0, 0), new Point(4, 0, 0));
        assertEquals(new Point(2, 0, 0), ray.findClosestPoint(points), "Wrong closest point");

        // =============== Boundary Values Tests ==================
        // TC11: empty list
        assertNull(ray.findClosestPoint(null), "findClosestPoint(null) returns something");
        // TC12: closest point is the first point of the list
        points = List.of(new Point(2, 0, 0), new Point(3, 0, 0), new Point(4, 0, 0));
        assertEquals(new Point(2, 0, 0), ray.findClosestPoint(points), "Wrong closest point");
        // TC13: closest point is the last point of the list
        points = List.of(new Point(4, 0, 0), new Point(3, 0, 0), new Point(2, 0, 0));
        assertEquals(new Point(2, 0, 0), ray.findClosestPoint(points), "Wrong closest point");
    }
}