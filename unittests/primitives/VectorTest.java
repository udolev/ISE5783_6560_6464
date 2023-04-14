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


    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
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
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
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
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void testLength() {
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    void testNormalize() {
    }
}