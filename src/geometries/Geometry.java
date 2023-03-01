package geometries;

import primitives.Point;
import primitives.Vector;

/** This interface will be a basic form for all geometries. */
public interface Geometry {
    /**
     * The function will return the normal to a geometric object in  some point p.
     * @param p the point we will find the normal to.
     */
    public Vector getNormal(Point p);
}
