package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This interface will be used as a base to all lights sources.
 */
public interface LightSource {
    /**
     * A method to get the intensity on a given point.
     *
     * @param p the point.
     **/
    public Color getIntensity(Point p);

    /**
     * A method to get the lights direction vector to a given point.
     *
     * @param p the point.
     **/
    public Vector getL(Point p);
}
