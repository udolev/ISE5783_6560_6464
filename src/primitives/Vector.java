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
}
