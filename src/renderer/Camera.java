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

    private int numOfRaysInLine = 9;

    // Depth Of Field features
    private double apertureSize = 0;
    // the amount of rays in a single line or row that will be cast from the aperture
    private double focalDistance = 100; // from view plane
    private Plane focalPlane;

    // Antialiasing features
    boolean antialiasing = false;
    double pixelWidth;
    double pixelLength;

    // Multi Threads
    int threadsCount = 4;

    // Adaptive Super Sampling
    boolean adaptiveSuperSampling = false;
    private PixelManager pixelManager;
    double printInterval = 0.1;

    // Setters
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        pixelWidth = imageWriter.getNx() / width;
        pixelLength = imageWriter.getNy() / height;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
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

    public Camera setNumOfRaysInLine(int numOfRaysInLine) {
        this.numOfRaysInLine = numOfRaysInLine;
        return this;
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

    public Camera setMultiThreading(int numOfThreads) {
        threadsCount = numOfThreads;
        return this;
    }

    public Camera setDebugPrint(double printInterval) {
        this.printInterval = printInterval;
        return this;
    }

    // Getters

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
     * A method to generate a color for every pixel in th view plane.
     **/
    public Camera renderImage() {
        if (p0 == null || vUp == null || vTo == null || vRight == null || height == 0 || width == 0 || imageWriter == null || rayTracer == null)
            throw new MissingResourceException("A resource is missing", "", "");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        pixelManager = new PixelManager(nY, nX, printInterval);
        if (threadsCount == 0) {
            for (int j = 0; j < nX; ++j)
                for (int i = 0; i < nY; ++i) {
                    imageWriter.writePixel(j, i, castRay(j, i));
                }
        } else {
            var threads = new LinkedList<Thread>(); // list of threads
            while (threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)
                    // allocate pixel(row,col) in loop until there are no more pixels
                    while ((pixel = pixelManager.nextPixel()) != null)
                        // cast ray through pixel (and color it – inside castRay)
                        calcPixel(pixel.col(), pixel.row());
                }));
            // start all the threads
            for (var thread : threads) thread.start();
            // wait until all the threads have finished
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {
            }
        }
        return this;
    }

    /**
     * A method to create the background grid to the picture.
     *
     * @param interval the size of each hex in the grid.
     * @param color    the color of the line of the grid.
     **/
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("ImageWriter missing", "ImageWriter", "imageWriter");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int j = 0; j < nX; ++j)
            for (int i = 0; i < nY; ++i)
                if (j % interval == 0 || i % interval == 0)
                    imageWriter.writePixel(j, i, color);

        imageWriter.writeToImage();
        return this;
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
        Color pixelColor = new Color(java.awt.Color.BLACK);
        Point pixelPoint = constructPixelPoint(imageWriter.getNx(), imageWriter.getNy(), xIndex, yIndex);

        if (adaptiveSuperSampling) {
            // Add depth of field
            if (!isZero(apertureSize)) {
                Point focalPoint = focalPlane.findIntersections(headRay).get(0);
                pixelColor = calcAdaptiveSuperSampling(focalPoint, pixelPoint, apertureSize, apertureSize);
            }

            // Add antialiasing
            if (antialiasing) {
                pixelColor = pixelColor.add(calcAdaptiveSuperSampling(pixelPoint, p0, pixelWidth, pixelLength));
            }

            if (!isZero(apertureSize) && antialiasing)
                pixelColor = pixelColor.reduce(2);
            else if (isZero(apertureSize) && !antialiasing)
                pixelColor = rayTracer.traceRay(headRay);

        } else {
            // Add depth of field
            if (!isZero(apertureSize)) {
                List<Point> aperture = generateAperture(pixelPoint);
                Point focalPoint = focalPlane.findIntersections(headRay).get(0);
                List<Ray> rayBeam = Ray.generateRayBeamToPoint(aperture, focalPoint);

                for (Ray currentRay : rayBeam)
                    pixelColor = pixelColor.add(rayTracer.traceRay(currentRay));
            }

            // Add antialiasing
            if (antialiasing) {
                List<Point> targetArea = generateTargetArea(pixelPoint, pixelWidth, pixelLength);
                List<Ray> rayBeam = Ray.generateRayBeamFromPoint(targetArea, p0);
                for (Ray currentRay : rayBeam)
                    pixelColor = pixelColor.add(rayTracer.traceRay(currentRay));
            }

            if (!isZero(apertureSize) && antialiasing)
                pixelColor = pixelColor.reduce(2 * numOfRaysInLine * numOfRaysInLine + 1);
            else if (!isZero(apertureSize) || antialiasing)
                pixelColor = pixelColor.reduce(numOfRaysInLine * numOfRaysInLine + 1);
            else
                pixelColor = rayTracer.traceRay(headRay);
        }

        return pixelColor;
    }

    /**
     * Cast ray from camera and color a pixel
     *
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void calcPixel(int col, int row) {
        imageWriter.writePixel(col, row, castRay(col, row));
        pixelManager.pixelDone();
    }

    Color calcAdaptiveSuperSampling(Point targetPoint, Point headPoint, double sizeX, double sizeY) {
        Point leftUp = headPoint.add(vUp.scale(sizeY / 2)).add(vRight.scale(-sizeX / 2));
        Point leftDown = headPoint.add(vUp.scale(-sizeY / 2)).add(vRight.scale(-sizeX / 2));
        Point rightUp = headPoint.add(vUp.scale(sizeY / 2)).add(vRight.scale(sizeX / 2));
        Point rightDown = headPoint.add(vUp.scale(-sizeY / 2)).add(vRight.scale(sizeX / 2));

        Color leftUpColor = rayTracer.traceRay(new Ray(leftUp, targetPoint.subtract(leftUp)));
        Color leftDownColor = rayTracer.traceRay(new Ray(leftDown, targetPoint.subtract(leftDown)));
        Color rightUpColor = rayTracer.traceRay(new Ray(rightUp, targetPoint.subtract(rightUp)));
        Color rightDownColor = rayTracer.traceRay(new Ray(rightDown, targetPoint.subtract(rightDown)));

        double minSizeX = sizeX / numOfRaysInLine;
        double minSizeY = sizeY / numOfRaysInLine;

        return calcAdaptiveSuperSamplingRec(leftUp, leftUpColor, leftDown, leftDownColor, rightUp, rightUpColor, rightDown, rightDownColor, sizeX, sizeY, minSizeX, minSizeY, targetPoint);
    }

    Color calcAdaptiveSuperSamplingRec(Point leftTop, Color leftTopColor, Point leftBottom, Color leftBottomColor, Point rightUp, Color rightUpColor, Point rightBottom, Color rightBottomColor, double sizeX, double sizeY, double minSizeX, double minSizeY, Point target) {
        Color average = (leftBottomColor.add(leftTopColor, rightBottomColor, rightUpColor)).reduce(4d);

        if (average.isAlmostEquals(leftBottomColor) || sizeX <= minSizeX || sizeY <= minSizeY) {
            return average;
        }

        // Generating a "plus" dividing the square into four squares
        Point top = leftTop.add(vRight.scale(sizeX / 2));
        Point bottom = leftBottom.add(vRight.scale(sizeX / 2));
        Point left = leftBottom.add(vUp.scale(sizeY / 2));
        Point right = rightBottom.add(vUp.scale(sizeY / 2));
        Point middle = leftBottom.add(vUp.scale(sizeY / 2)).add(vRight.scale(sizeX / 2));

        // Calculating colors of each point on the plus
        Color topColor = rayTracer.traceRay(new Ray(top, target.subtract(top)));
        Color bottomColor = rayTracer.traceRay(new Ray(bottom, target.subtract(bottom)));
        Color leftColor = rayTracer.traceRay(new Ray(left, target.subtract(left)));
        Color rightColor = rayTracer.traceRay(new Ray(right, target.subtract(right)));
        Color middleColor = rayTracer.traceRay(new Ray(middle, target.subtract(middle)));

        // Calculating squares in this order:  sq1 sq2
        //                                     sq3 sq4
        Color sq1 = calcAdaptiveSuperSamplingRec(leftTop, leftTopColor, left, leftColor, top, topColor, middle, middleColor, sizeX / 2, sizeY / 2, minSizeX, minSizeY, target);
        Color sq2 = calcAdaptiveSuperSamplingRec(top, topColor, middle, middleColor, rightUp, rightUpColor, right, rightColor, sizeX / 2, sizeY / 2, minSizeX, minSizeY, target);
        Color sq3 = calcAdaptiveSuperSamplingRec(left, leftColor, leftBottom, leftBottomColor, middle, middleColor, bottom, bottomColor, sizeX / 2, sizeY / 2, minSizeX, minSizeY, target);
        Color sq4 = calcAdaptiveSuperSamplingRec(middle, middleColor, bottom, bottomColor, right, rightColor, rightBottom, rightBottomColor, sizeX / 2, sizeY / 2, minSizeX, minSizeY, target);

        // Average of squares is the color of the big square
        return (sq1.add(sq2, sq3, sq4)).reduce(4);
    }


    /**
     * A method to generate the aperture for a pixel as a list of points.
     *
     * @param pixel the given pixel.
     **/
    private List<Point> generateAperture(Point pixel) {
        return generateTargetArea(pixel, apertureSize, apertureSize);
    }

    /**
     * A method to generate a grid target area around a point.
     *
     * @param pixel the given pixel.
     **/
    private List<Point> generateTargetArea(Point pixel, double sizeX, double sizeY) {
        if (isZero(sizeX) || isZero(sizeY)) return List.of(pixel);
        List<Point> targetArea = new LinkedList<>();
        Point leftCorner = pixel.add(vUp.scale(sizeY / 2)).add(vRight.scale(-sizeX / 2));
        Point current = leftCorner;
        for (int i = 0; i <= numOfRaysInLine; ++i) {
            if (i != 0)
                current = leftCorner.add(vUp.scale(-sizeY * i / numOfRaysInLine));
            for (int j = 0; j <= numOfRaysInLine; ++j) {
                targetArea.add(current);
                current = current.add(vRight.scale(sizeX / numOfRaysInLine));
            }
        }
        return targetArea;
    }

    public Camera enableAntialiasing() {
        antialiasing = true;
        return this;
    }

    public Camera enableAdaptiveSuperSampling() {
        adaptiveSuperSampling = true;
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
        return new Vector(x * cos(Angle) - y * sin(Angle), x * sin(Angle) + y * cos(Angle),z);
    }
}