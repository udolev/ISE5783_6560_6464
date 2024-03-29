package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * This class will represent the background color.
 */
public class AmbientLight extends Light {

    // the default background light
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructor to initialize the ambient light with the light intensity by RGB and the attenuation coefficient.
     *
     * @param Ia location point.
     * @param Ka height direction vector.
     **/
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Constructor to initialize the ambient light with the light intensity by RGB and the attenuation coefficient.
     *
     * @param Ia location point.
     * @param Ka height direction vector.
     **/
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }
}
