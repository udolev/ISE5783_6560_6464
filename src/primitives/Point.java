package primitives;

import java.lang.Math;
import java.util.Objects;

public class Point {
    final protected Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    Point(Double3 point) {
        xyz = point;
    }

    public Point substruct(Point other) {
        return new Point(this.xyz.subtract(other.xyz));
    }

    public Point add(Point other) {
        return new Point(this.xyz.add(other.xyz));
    }

    public double distanceSquared(Point other) {
        return (this.xyz.d1 - other.xyz.d1) * (this.xyz.d1 - other.xyz.d1) + (this.xyz.d2 - other.xyz.d2) * (this.xyz.d2 - other.xyz.d2) + (this.xyz.d3 - other.xyz.d3) * (this.xyz.d3 - other.xyz.d3);
    }

    public double distance(Point other) {
        return Math.sqrt(this.distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point)
            return xyz.equals(((Point) obj).xyz);
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
