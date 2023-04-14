package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 *
 * @author Natan Weis
 */
class PointTest {
    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // the classic case
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(-3, -6, 4);
        assertEquals(new Vector(4, 8, -1), p1.subtract(p2));
        // =============== Boundary Values Tests ==================
        //subtraction equals vector zero
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "Constructed a zero vector");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Point)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // the classic case
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(-3, -6, 4);
        assertEquals(new Vector(4, 8, -1), p1.subtract(p2));
        // =============== Boundary Values Tests ==================
        //result equals vector zero
        Point p3 = new Point(-1, -2, -3);
        assertThrows(IllegalArgumentException.class, () -> p3.subtract(p1), "Constructed a zero vector");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // the classic case
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(-3, -6, 4);
        assertEquals(81, p1.distanceSquared(p2),0.0000001,"distanceSquared wrong result");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // the classic case
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(-3, -6, 4);
        assertEquals(9, p1.distance(p2),0.0000001,"distance wrong result");
    }
}