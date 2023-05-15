package geometries;

import primitives.Point;

/**
 * The abstract class that will be the base for all radius based geometries.
 */
public abstract class RadialGeometry extends Geometry {
    /**
     * The radius
     */
    final protected double radius;

    /**
     * Constructor to initialize the object with its radius.
     */
    public RadialGeometry(double r) {
        radius = r;
    }

    /**
     * Getter
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof RadialGeometry other)
            return radius == other.radius;
        return false;
    }
}
