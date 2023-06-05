package geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/** Testing Polygons
 * @author Dan */
public class PolygonTest {

   /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
   @Test
   public void testConstructor() {
      // ============ Equivalence Partitions Tests ==============

      // TC01: Correct concave quadrangular with vertices in correct order
      try {
         new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
      } catch (IllegalArgumentException e) {
         fail("Failed constructing a correct polygon");
      }

      // TC02: Wrong vertices order
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                   "Constructed a polygon with wrong order of vertices");

      // TC03: Not in the same plane
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                   "Constructed a polygon with vertices that are not in the same plane");

      // TC04: Concave quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0.5, 0.25, 0.5)), //
                   "Constructed a concave polygon");

      // =============== Boundary Values Tests ==================

      // TC10: Vertex on a side of a quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0, 0.5, 0.5)),
                   "Constructed a polygon with vertix on a side");

      // TC11: Last point = first point
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                   "Constructed a polygon with vertice on a side");

      // TC12: Co-located points
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                   "Constructed a polygon with vertice on a side");

   }

   /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
   @Test
   public void testGetNormal() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: There is a simple single test here - using a quad
      Point[] pts =
         { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
      Polygon pol = new Polygon(pts);
      // ensure there are no exceptions
      assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
      // generate the test result
      Vector result = pol.getNormal(new Point(0, 0, 1));
      // ensure |result| = 1
      assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
      // ensure the result is orthogonal to all the edges
      for (int i = 0; i < 3; ++i)
         assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
   }
   /**
    * Test method for {@link geometries.Polygon#findIntersections(Ray)}.
    */
   @Test
   void testFindIntersections() {
      Point[] pts = {new Point(1,-1,2),new Point(-1,-1,2),new Point(-1,1,2),new Point(1,1,2)};
      Polygon polygon = new Polygon(pts);
      // ============ Equivalence Partitions Tests ==============
      // TC01: intersection point is inside the Polygon (1 point)
      Point p = new Point(0,0.5,2);
      List<Point> result = polygon.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0, 0, 1)));
      assertEquals(1, result.size(), "Wrong number of points");
      assertEquals(List.of(p), result, "Ray crosses Polygon inside of it");
      // TC02: intersection point is outside against vertex (0 points)
      assertNull(polygon.findIntersections(new Ray(new Point(2, 2, 0), new Vector(0, 0, 1))), "Ray crosses the polygon's plane against a vertex");
      // TC03: intersection point is outside against edge (0 points)
      assertNull(polygon.findIntersections(new Ray(new Point(0, 2, 0), new Vector(0, 0, 1))), "Ray crosses the polygon's plane against an edge");
      // =============== Boundary Values Tests ==================
      // TC11: intersection point is on a polygon's vertex (0 points)
      assertNull(polygon.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 0, 1))),  "Ray crosses the polygon at a vertex");
      // TC12: intersection point is on a polygon's edge (0 points)
      assertNull(polygon.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 0, 1))), "Ray crosses a polygon's edge");
      // TC13: intersection point is on a polygon's edge line (0 points)
      assertNull(polygon.findIntersections(new Ray(new Point(2, 1, 0), new Vector(0, 0, 1))), "Ray crosses the polygon's plane at a edge's line");
   }
   /**
    * Test method for {@link geometries.Polygon#findGeoIntersections(Ray, double)}.
    */
   @Test
   void testFindGeoIntersections() {
      Point[] pts = {new Point(1,-1,2),new Point(-1,-1,2),new Point(-1,1,2),new Point(1,1,2)};
      Polygon polygon = new Polygon(pts);
      // ============ Equivalence Partitions Tests ==============
      // TC01: intersection point is inside the Polygon and inside max distance(1 point)
      Point p = new Point(0,0.5,2);
      List<Intersectable.GeoPoint> result = polygon.findGeoIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0, 0, 1)),100);
      assertEquals(List.of(new Intersectable.GeoPoint(polygon, p)), result, "Ray crosses polygon and intersection point is in range");
      // TC02: intersection point is outside the Polygon(0 point)
      assertNull(polygon.findGeoIntersections(new Ray(new Point(1000, 1000, 1000), new Vector(0, 0, 1)),100), "Ray does not cross the polygon");
      // TC03: intersection point is inside the Polygon and outside max distance(0 point)
      result = polygon.findGeoIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0, 0, 1)),1);
      assertNull(result, "ray crosses polygon and intersection point is out of range");
      // =============== Boundary Values Tests ==================
      // TC11: Ray intersects the plane and intersection point is on range (1 point)
      assertEquals(List.of(new Intersectable.GeoPoint(polygon,new Point(0, 0, 2))),polygon.findGeoIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)),2), "Ray crosses polygon and intersection point is on range");
   }
}