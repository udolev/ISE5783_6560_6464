package renderer;

import geometries.Plane;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static java.lang.Math.*;
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
    // view plane's center point
    private Point pCenter;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    // Depth Of Field features
    private double apertureSize = 0;
    // the amount of rays in a single line or row that will be cast from the aperture
    private int numOfRaysInLine = 5;
    private double focalDistance = 100; // from view plane
    private Plane focalPlane;

    // Setters
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public Camera setNumOfRaysInLine(int numOfRaysInLine) {
        this.numOfRaysInLine = numOfRaysInLine;
        return this;
    }

    /**
     * Constructor to initialize the camera with its location point and two direction vectors, as well as a rotation angle.
     *
     * @param AngleX the angle to which we will need to rotate the camera to on x-axis
     * @param AngleY the angle to which we will need to rotate the camera to on y-axis
     * @param AngleZ the angle to which we will need to rotate the camera to on z-axis
     * @param p      location point.
     * @param vu     height direction vector.
     * @param vt     width direction vector.
     **/
    public Camera(Point p, Vector vt, Vector vu, double AngleX, double AngleY, double AngleZ) {
        p0 = p;
        if (!isZero(vu.dotProduct(vt))) {
            throw new IllegalArgumentException("vUp isn't orthogonal to vTo");
        }
        vTo = vt;
        vUp = vu;
        this.rotateCamera(AngleX, AngleY, AngleZ);
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
        pCenter = p0.add(vTo.scale(distance));
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
        Point pIJ = constructPixelPoint(nX, nY, j, i);
        return new Ray(p0, pIJ.subtract(p0));
    }

    /**
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    private Point constructPixelPoint(int nX, int nY, int j, int i) {
        double Ry = height / nY;
        double Rx = width / nX;
        double yI = -(i - ((nY - 1) / 2.0)) * Ry;
        double xJ = (j - ((nX - 1) / 2.0)) * Rx;

        Point pIJ = pCenter;
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));

        return pIJ;
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
     * A method that will activate the method WriteToImage from "imageWriter
     **/
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("ImageWriter missing", "ImageWriter", "imageWriter");

        imageWriter.writeToImage();
    }

    /**
     * A method to generate a color for each pixel. if the depth of field feature is used,
     * will also create the ray-beam to the focal point and calculate the average color to determinate the pixel color.
     **/
    private Color castRay(int xIndex, int yIndex) {
        Ray headRay = constructRay(imageWriter.getNx(), imageWriter.getNy(), xIndex, yIndex);
        Color pixelColor = rayTracer.traceRay(headRay);
        if (isZero(apertureSize))
            return pixelColor;

        Point pixelPoint = constructPixelPoint(imageWriter.getNx(), imageWriter.getNy(), xIndex, yIndex);
        List<Point> aperture = generateAperture(pixelPoint);
        Point focalPoint = focalPlane.findIntersections(headRay).get(0);
        List<Ray> rayBeam = generateRayBeamToPoint(aperture, focalPoint);

        for (Ray currentRay : rayBeam)
            pixelColor = pixelColor.add(rayTracer.traceRay(currentRay));
        pixelColor = pixelColor.scale(1.0 / (numOfRaysInLine * numOfRaysInLine + 1));

        return pixelColor;
    }

    /**
     * A method to rotate the camera by angles on X,Y,Z axis.
     *
     * @param angleX the angle on X axis.
     * @param angleY the angle on Y axis.
     * @param angleZ the angle on Z axis.
     **/
    public void rotateCamera(double angleX, double angleY, double angleZ) {
        double radiansAngleX = toRadians(angleX);
        double radiansAngleY = toRadians(angleY);
        double radiansAngleZ = toRadians(angleZ);
        vTo = rotateOnXAxis(vTo, radiansAngleX);
        vUp = rotateOnXAxis(vUp, radiansAngleX);
        vTo = rotateOnYAxis(vTo, radiansAngleY);
        vUp = rotateOnYAxis(vUp, radiansAngleY);
        vTo = rotateOnZAxis(vTo, radiansAngleZ);
        vUp = rotateOnZAxis(vUp, radiansAngleZ);
        vTo = vTo.normalize();
        vUp = vUp.normalize();
        vRight = (vTo.crossProduct(vUp)).normalize();
    }

    Vector rotateOnXAxis(Vector V, double Angle) {
        double x = V.getX(), y = V.getY(), z = V.getZ();
        return new Vector(x, y * cos(Angle) - z * sin(Angle), y * sin(Angle) + z * cos(Angle));
    }

    Vector rotateOnYAxis(Vector V, double Angle) {
        double x = V.getX(), y = V.getY(), z = V.getZ();
        return new Vector(x * cos(Angle) + z * sin(Angle), y, -x * sin(Angle) + z * cos(Angle));
    }

    Vector rotateOnZAxis(Vector V, double Angle) {
        double x = V.getX(), y = V.getY(), z = V.getZ();
        return new Vector(x * cos(Angle) - y * sin(Angle), x * sin(Angle) + y * cos(Angle), z);
    }

    /**
     * A method to generate the aperture for a pixel as a list of points.
     *
     * @param pixel the given pixel.
     **/
    private List<Point> generateAperture(Point pixel) {
        if (isZero(apertureSize)) return List.of(pixel);
        List<Point> targetArea = new LinkedList<>();
        Point leftCorner = pixel.add(vUp.scale(apertureSize / 2)).add(vRight.scale(-apertureSize / 2));
        Point current = leftCorner;
        for (int i = 0; i < numOfRaysInLine; ++i) {
            if (i != 0)
                current = leftCorner.add(vUp.scale(-apertureSize * i / numOfRaysInLine));
            for (int j = 0; j < numOfRaysInLine; ++j) {
                targetArea.add(current);
                current = current.add(vRight.scale(apertureSize / numOfRaysInLine));
            }
        }
        return targetArea;
    }

    /**
     * Setter to initialize/set the size of the aperture.
     *
     * @param apertureSize the aperture size
     **/
    public Camera setApertureSize(double apertureSize) {
        this.apertureSize = apertureSize;
        return this;
    }

    /**
     * Setter to initialize/set the distance between the view plane and the focal plane.
     *
     * @param focalDistance the give distance
     **/
    public Camera setFocalPlaneDistance(double focalDistance) {
        if (isZero(focalDistance))
            throw new IllegalArgumentException("focal distance can't be zero");
        this.focalDistance = focalDistance;
        focalPlane = new Plane(pCenter.add(vTo.scale(focalDistance)), vTo);
        return this;
    }

    /**
     * a method to generate a beam of rays from every point in a given list to one target point.
     *
     * @param points the list of points
     * @param target the target point
     * @return a list of rays from each point to the target point
     */
    public List<Ray> generateRayBeamToPoint(List<Point> points, Point target) {
        List rayBeam = new LinkedList<Ray>();
        Vector direction;
        for (Point point : points) {
            direction = target.subtract(point);
            rayBeam.add(new Ray(point, direction));
        }
        return rayBeam;
    }
}
