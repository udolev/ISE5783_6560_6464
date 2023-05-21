package lighting;

import primitives.Color;

/**
 * This class will be used as a base to all lights.
 */
abstract class Light {
    // the light's intensity
    private Color intensity;

    /**
     * Constructor to initialize a Light object with its intensity.
     *
     * @param intensity the light's intensity.
     **/
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * A method to get the intensity value
     *
     * @return light's intensity.
     */
    public Color getIntensity() {
        return intensity;
    }

}
