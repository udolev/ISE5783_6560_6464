package primitives;

/** This will represent a ray in a 3D world.
 * A ray will be represented by a head point and a direction vector.
 */
public class Ray {
    final private Point p0;
    final private Vector dir;

    /** Constructor to initialize Ray object with its head point and direction vector
     * @param p head of the ray
     * @param v direction of the ray
     */
    public Ray(Point p, Vector v) {
        p0 = p;
        dir = v.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return p0.equals(other.p0) && dir.equals(other.dir);
        return false;
    }

    @Override
    public String toString() {
        return "p0=" + p0 + ", dir=" + dir;
    }
}
