package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    final private Point center;

    public Sphere(double r, Point p) {
        super(r);
        center = p;
    }

    // Getter
    public Point getCenter() {
        return center;
    }

    public Vector getNormal(Point p) {
        return null;
    }
}
