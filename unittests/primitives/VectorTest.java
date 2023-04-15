package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 *
 * @author Natan Weis
 */
class VectorTest {
    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     */
    @Test
    void testNumbersConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test that constructing a regular vector works
        try {
            new Vector(1, 2, 3);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct (numbers) vector");
        }

        // =============== Boundary Values Tests ==================
        // TC11: test that constructing a zero vector throws an exception
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "Constructed a (numbers) zero vector");
    }

    /**
     * Test method for {@link primitives.Vector#Vector(Double3)}.
     */
    @Test
    void testDouble3Constructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test that constructing a regular vector works
        try {
            new Vector(new Double3(1, 2, 3));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct (Double3) vector");
        }

        // =============== Boundary Values Tests ==================
        // TC11: test that constructing a zero vector throws an exception
        assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO), "Constructed a (Double3) zero vector");
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        // TC01: addition of sharp angled vectors works properly
        Vector v2 = new Vector(1, 1, 1);
        Vector vr = new Vector(2, 3, 4);
        assertEquals(vr, v1.add(v2), "sharp angled vectors add() is wrong");

        // TC02: addition of obtuse angled vectors works properly
        v2 = new Vector(0, -1, -8);
        vr = new Vector(1, 1, -5);
        assertEquals(vr, v1.add(v2), "obtuse angled vectors add() is wrong");

        // =============== Boundary Values Tests ==================
        // TC11: test that adding a vector to its opposite gives a zero vector
        Vector minusV1 = new Vector(-1, -2, -3);
        assertThrows(IllegalArgumentException.class, () -> v1.add(minusV1), "Vector + -itself does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        // TC01: subtraction of sharp angled vectors works properly
        Vector v2 = new Vector(1, 1, 1);
        Vector vr = new Vector(0, 1, 2);
        assertEquals(vr, v1.subtract(v2), "sharp angled vectors subtract() is wrong");

        // TC02: addition of obtuse angled vectors works properly
        v2 = new Vector(0, -1, -8);
        vr = new Vector(1, 3, 11);
        assertEquals(vr, v1.subtract(v2), "obtuse angled vectors subtract() is wrong");

        // =============== Boundary Values Tests ==================
        // TC11: test that subtracting a vector from itself gives a zero vector
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "Vector - itself does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.scale(2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple vector scaling works properly
        Vector vr = new Vector(2, 4, 6);
        assertEquals(vr, u, "scale() wrong result");

        // TC02: check that the scaled vector is parallel to the original vector
        assertThrows(IllegalArgumentException.class, () -> u.crossProduct(v), "scaled vector isn't parallel to the original vector");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        // TC01: dot-product of sharp angled vectors works properly
        Vector v2 = new Vector(1, 1, 1);
        assertEquals(6, v1.dotProduct(v2), 0.00000001, "sharp angled vectors dotProduct() is wrong");

        // TC02: dot-product of obtuse angled vectors works properly
        v2 = new Vector(0, -1, -8);
        assertEquals(-26, v1.dotProduct(v2), "obtuse angled vectors dotProduct() is wrong");

        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(5, 0, -2);
        // TC11: dot-product of orthogonal vectors is zero
        Vector v4 = new Vector(0, -3, 0);
        assertTrue(isZero(v3.dotProduct(v4)), "orthogonal vectors dotProduct() is not zero");

        // TC11: dot-product of two vectors where one is a unit vector
        v4 = new Vector(1, 0, 0);
        assertEquals(5, v3.dotProduct(v4), 0.00000001, "unit vector dotProduct() is wrong");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00000001, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple vector length squared works properly
        assertEquals(14, new Vector(1, 2, 3).lengthSquared(), 0.00000001, "lengthSquared of a vector is incorrect");
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple vector length works properly
        assertEquals(5, new Vector(0, 4, 3).length(), 0.00000001, "length of a vector is incorrect");
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple vector normalization works properly
        // check that |u| = 1
        assertEquals(1, u.length(), 0.00000001, "normalized vector's length isn't 1");
        // check that u is parallel to the original vector v
        assertThrows(IllegalArgumentException.class, () -> u.crossProduct(v), "normalized vector isn't parallel to the original vector");
    }
}