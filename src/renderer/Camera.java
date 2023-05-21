package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * This class will represent the camera, the tool we will use to construct rays in order
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

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    // Setters
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Constructor to initialize the camera with its location point and two direction vectors.
     *
     * @param p  location point.
     * @param vu height direction vector.
     * @param vt width direction vector.
     **/
    public Camera(Point p, Vector vt, Vector vu) {
        p0 = p;
        if (!isZero(vu.dotProduct(vt))) {
            throw new IllegalArgumentException("vUp isn't orthogonal to vTo");
        }
        vTo = vt.normalize();
        vUp = vu.normalize();
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
        if (isZero(distance))
            throw new IllegalArgumentException("distance can't be zero");
        this.distance = distance;
        return this;
    }

    /**
     * A method to create a ray that starts at the camera and goes through a specific pixel.
     *
     * @param nX the size of the VP's columns.
     * @param nY the size of the VP's rows.
     * @param j  the pixel's column.
     * @param i  the pixel's row.
     **/
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pCenter = p0.add(vTo.scale(distance));
        double Ry = height / nY;
        double Rx = width / nX;
        double yI = -(i - ((nY - 1) / 2.0)) * Ry;
        double xJ = (j - ((nX - 1) / 2.0)) * Rx;

        Point pIJ = pCenter;
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));

        return new Ray(p0, pIJ.subtract(p0));
    }

    /**
     * Getter to receive the view plane's height.
     **/
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

    /**
     * A method to generate a color for every pixel in th view plane.
     **/
    public ImageWriter renderImage() {
        if (p0 == null || vUp == null || vTo == null || vRight == null || height == 0 || width == 0 || imageWriter == null || rayTracer == null)
            throw new MissingResourceException("A resource is missing", "", "");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int j = 0; j < nX; ++j)
            for (int i = 0; i < nY; ++i) {
                imageWriter.writePixel(j, i, castRay(j, i));
            }
        return imageWriter;
    }

    /**
     * A method to create the background grid to the picture.
     *
     * @param interval the size of each hex in the grid.
     * @param color    the color of the line of the grid.
     **/
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("ImageWriter missing", "ImageWriter", "imageWriter");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int j = 0; j < nX; ++j)
            for (int i = 0; i < nY; ++i)
                if (j % interval == 0 || i % interval == 0)
                    imageWriter.writePixel(j, i, color);

        imageWriter.writeToImage();
    }

    /**
     * A method that will activate the method WriteToImage from "imageWriter.
     **/
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("ImageWriter missing", "ImageWriter", "imageWriter");

        imageWriter.writeToImage();
    }

    /**
     * A method to generate a color for each pixel.
     **/
    private Color castRay(int xIndex, int yIndex) {
        Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), xIndex, yIndex);
        return rayTracer.traceRay(ray);
    }
}
