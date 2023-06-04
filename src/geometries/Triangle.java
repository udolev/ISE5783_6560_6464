package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * This class will represent a triangle in a 3D world.
 */
public class Triangle extends Polygon {
    /**
     * Constructor to initialize a triangle using 3 points.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}