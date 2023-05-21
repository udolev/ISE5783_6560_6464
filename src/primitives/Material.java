package primitives;

/**
 * This class represents a material and is refractive and reflective attributes
 */
public class Material {
    public Double3 kD = Double3.ZERO, kS = Double3.ZERO;
    // the material shininess
    public int nShininess = 0;

    /**
     * A method to sett the kD value with a Double3 parameter.
     *
     * @param kD the specular factor
     * @return the PointLight object.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * A method to sett the kS value with a Double3 parameter.
     *
     * @param kS the specular factor
     * @return the PointLight object.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * A method to sett the kD value with a double parameter.
     *
     * @param kD the specular factor
     * @return the PointLight object.
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * A method to sett the kS value with a double parameter.
     *
     * @param kS the specular factor
     * @return the PointLight object.
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * A method to sett the shininess value.
     *
     * @param nShininess the shininess factor
     * @return the PointLight object.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
