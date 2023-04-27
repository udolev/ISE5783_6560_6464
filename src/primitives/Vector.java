package primitives;

import static primitives.Util.alignZero;

/**
 * This will represent a Vector in a 3D world.
 * The class will be used for basic operations related to vectors, such as: add, subtract etc.
 * The vector will be represented in a three coordinates form.
 */
public class Vector extends Point {

    /**
     * Constructor to initialize Vector object with its three coordinate values
     *
     * @param x first coordinate value.
     * @param y second coordinate value.
     * @param z third coordinate value.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (x == 0 && y == 0 && z == 0)
            throw new IllegalArgumentException("zero vector");
    }

    /**
     * Constructor to initialize Vector object using a Double3 object
     *
     * @param vector Double3 object which represents the vector's three coordinates values.
     */
    Vector(Double3 vector) {
        super(vector);
        if (vector.equals(Double3.ZERO))
            throw new IllegalArgumentException("zero vector");
    }

    /**
     * Add two 3 coordinated vectors into a new vector where each couple of
     * coordinates is added
     *
     * @param other right handle side operand for subtraction
     * @return result of add (a Vector)
     */
    public Vector add(Vector other) {
        return new Vector(xyz.add(other.xyz));
    }

    /**
     * Scale (multiply) three coordinated vector by a number into a new vector where
     * each coordinate is multiplied by the number
     *
     * @param rhs right handle side operand for scaling
     * @return result of scale
     */
    public Vector scale(double rhs) {
        return new Vector(xyz.scale(rhs));
    }

    /**
     * Dot product two vectors using the algebraic formula
     *
     * @param other the other vector which we dot product
     * @return result of dot product (a number)
     */
    public double dotProduct(Vector other) {
        return alignZero(xyz.d1 * other.xyz.d1 + xyz.d2 * other.xyz.d2 + xyz.d3 * other.xyz.d3);
    }

    /**
     * Cross product two vectors into a new vector using the algebraic formula
     *
     * @param other the other vector which we cross product
     * @return result of cross product (a Vector)
     */
    public Vector crossProduct(Vector other) {
        return new Vector(alignZero(xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2), alignZero(xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3), alignZero(xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1));
    }

    /**
     * Calculate the vector length squared using the dot product between the vector to itself
     *
     * @return vector's length squared
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Calculate the vector length
     *
     * @return vector's length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a new vector with the same direction and a length of 1
     *
     * @return normalized vector
     */
    public Vector normalize() {
        return new Vector(xyz.scale(1 / length()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Vector other)
            return super.equals(other);
        return false;
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return "‐>" + super.toString();
    }
}
