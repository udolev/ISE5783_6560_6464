package geometries;
import primitives.Point;
import primitives.Vector;
/** This class will represent a plane in a 3D world.
 * It will be used to represent all flat objects in a 3D world. */
public class Plane implements Geometry {
    final private Point q0;
    final private Vector normal;
    /** Constructor to initialize Plane based object with three Point.
     * @param p1 first Point value
     * @param p2 second Point value
     * @param p3 third Point value */
    public Plane(Point p1, Point p2, Point p3) {
        normal = null;
        q0 = p1;
    }
    /** Constructor to initialize Plane based object with a Vector and a Point.
     * @param v the planes vector
     * @param p the planes Point */
    public Plane(Point p, Vector v) {
        q0 = p;
        normal = v.normalize();
    }

    /** Getters */
    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    public Vector getNormal(Point p) {
        return getNormal();
    }
}
