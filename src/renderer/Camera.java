package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * this class weil represent the camera, the tool we will use to construct rays in order
 * to find intersection points with 3d objects
 */
public class Camera {
    // camera's location
    private Point p0;
    // direction vectors of the camera
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    // view plane's physical aspects
    private double height;
    private double width;
    // the distance between the camera and the view plane
    private double distance;

    /**
     * Constructor to initialize the camera with its location point and two direction vectors.
     *
     * @param p  location point.
     * @param vu height direction vector.
     * @param vt width direction vector.
     **/
    public Camera(Point p, Vector vu, Vector vt) {
        p0 = p;
        if (!isZero(vu.dotProduct(vt))) {
            throw new IllegalArgumentException("vUp isn't orthogonal to vTo");
        }
        vUp = vu.normalize();
        vTo = vt.normalize();
        vRight = (vTo.crossProduct(vUp)).normalize();
    }

    /**
     * Setter to initialize/set the height and width of the view plane.
     *
     * @param width  view plane's width.
     * @param height view plane's height.
     **/
    public Camera setVPSize(double width, double height) {
        this.height = height;
        this.width = width;
        return this;
    }

    /**
     * Setter to initialize/set the distance between the camera and the view plane.
     *
     * @param distance view plane's distance.
     **/
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * A method to create a ray that starts at the camera and goes through a specific pixel.
     *
     * @param nX the size of the VP's columns.
     * @param nY the size of the VP's rows.
     * @param j the pixel's column.
     * @param i the pixel's
     **/
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }

    /**
     * Getter to receive the view plane's height.
     **/
    // Getters
    public double getHeight() {
        return height;
    }

    /**
     * Getter to receive the view plane's width.
     **/
    public double getWidth() {
        return width;
    }

    /**
     * Getter to receive the distance between the camera and the view plane.
     **/
    public double getDistance() {
        return distance;
    }
}
