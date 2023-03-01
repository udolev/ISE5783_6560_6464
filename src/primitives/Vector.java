package primitives;

public class Vector extends Point {

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (x == 0 && y == 0 && z == 0)
            throw new IllegalArgumentException("zero vector");
    }

    Vector(Double3 vector) {
        super(vector);
        if (vector.equals(Double3.ZERO))
            throw new IllegalArgumentException("zero vector");
    }

    public Vector add(Vector other) {
        return new Vector(xyz.add(other.xyz));
    }

    public Vector scale(double rhs) {
        return new Vector(xyz.scale(rhs));
    }

    public double dotProduct(Vector other) {
        return xyz.d1 * other.xyz.d1 + xyz.d2 * other.xyz.d2 + xyz.d3 * other.xyz.d3;
    }

    public Vector crossProduct(Vector other) {
        return new Vector(xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2, xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3, xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1);
    }

    public double lengthSquared() {
        return this.dotProduct(this);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

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
