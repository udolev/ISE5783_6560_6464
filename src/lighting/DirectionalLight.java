package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class will represent a directional light source.
 */
public class DirectionalLight extends Light implements LightSource {
    // the lights direction
    private Vector direction;

    /**
     * Constructor to initialize a directional Light source with its intensity and direction.
     *
     * @param direction the light's direction.
     * @param intensity the Light's intensity.
     **/
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
