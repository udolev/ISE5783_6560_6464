package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class will represent a point light source.
 */
public class PointLight extends Light implements LightSource {
    // the light source position
    private Point position;
    // the attenuation coefficients
    private double kC = 1, kL = 0, kQ = 0;

    /**
     * Constructor to initialize a point Light source with its intensity and position.
     *
     * @param position  the light's position.
     * @param intensity the Light's intensity.
     **/
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * A method to sett the kC value.
     *
     * @param kC constant attenuation factor.
     * @return the PointLight object.
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * A method to sett the kL value.
     *
     * @param kL the linear attenuation factor.
     * @return the PointLight object.
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * A method to sett the kQ value.
     *
     * @param kQ the quadratic attenuation factor.
     * @return the PointLight object.
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return getIntensity().scale(1 / (kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point p) {
        return (p.subtract(position)).normalize();
    }
}