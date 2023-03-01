package primitives;

import java.util.Objects;

public class Ray {
    final private Point p0;
    final private Vector dir;

    public Ray(Point p, Vector v) {
        p0 = p;
        dir = v.normalize();
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
