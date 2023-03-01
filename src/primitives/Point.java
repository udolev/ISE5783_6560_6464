package primitives;

import java.lang.Math;

/** This will represent a point in a 3D world.
 * The class will be used for basic operations related to points, such as: add, subtract etc.
 */
public class Point {
    /** Coordinates of the point */
    final protected Double3 xyz;

    /** Constructor to initialize Point based object with its three coordinate values
     * @param x first coordinate value.
     * @param y second coordinate value.
     * @param z third coordinate value.
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /** Constructor to initialize Point based object using a Double3 object
     * @param point Double3 object which represents the point's three coordinates values.
     */
    Point(Double3 point) {
        xyz = point;
    }

    /** Subtract two 3 coordinated points into a vector where each couple of
     * coordinates is subtracted
     * @param  other right handle side operand for subtraction
     * @return     result of subtraction (a Vector) */
    public Vector subtract(Point other) {
        return new Vector(xyz.subtract(other.xyz));
    }

    /** Add two 3 coordinated points into a new point where each couple of
     * coordinates is added
     * @param  other right handle side operand for subtraction
     * @return     result of add (a Point) */
    public Point add(Point other) {
        return new Point(xyz.add(other.xyz));
    }

    /** Calculate the squared distance between two points using the algebraic formula
     * @param other the point to which the squared distance is calculated
     * @return the squared distance between two points */
    public double distanceSquared(Point other) {
        return (xyz.d1 - other.xyz.d1) * (xyz.d1 - other.xyz.d1) + (xyz.d2 - other.xyz.d2) * (xyz.d2 - other.xyz.d2) + (xyz.d3 - other.xyz.d3) * (xyz.d3 - other.xyz.d3);
    }

    /** Calculate the distance between two points
     * @param other the point to which the squared distance is calculated
     * @return the distance between two points */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point other)
            return xyz.equals(other.xyz);
        return false;
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return "" + xyz;
    }
}
