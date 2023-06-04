package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;

/**
 * This class will represent a spotlight source.
 */
public class SpotLight extends PointLight {
    // the light's direction
    private Vector direction;
    private double narrowBeamFactor;

    /**
     * Constructor to initialize a point Light source with its intensity and position.
     *
     * @param direction the light's direction.
     * @param position  the light's position.
     * @param intensity the Light's intensity.
     **/
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        narrowBeamFactor = 1;
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return (super.getIntensity(p)).scale(Math.pow(max(0, getL(p).dotProduct(direction)), narrowBeamFactor));
    }

    public SpotLight setNarrowBeam(double narrow) {
        narrowBeamFactor = narrow;
        return this;
    }
}
