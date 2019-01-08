package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import geometries.Geometry;
import geometries.Sphere;
import primitives.*;

public class SphereTests {

	@Test
	public void testNormal() {
		Sphere s = new Sphere(5, Point3D.ZERO,Color.WHITE,new Material(1,1,1));
		assertEquals(new Vector(new Point3D(0, 0, 1)), s.getNormal(new Point3D(0, 0, 5)), "Bad GetNormal");
	}

	@Test
	public void testFindIntersections() {
		Sphere s = new Sphere(1, Point3D.ZERO,Color.WHITE,new Material(1,1,1));
		Map<Geometry,List<Point3D>> map = new HashMap<>();
		List<Point3D> li = new ArrayList<Point3D>();
		// EP 1 -regular 2 intersections
		Ray interay = new Ray(new Point3D(-1, 0.5, 0), new Vector(1, 0, 0));
		li.add(new Point3D(Math.sqrt(0.75), 0.5, 0));
		li.add(new Point3D(-Math.sqrt(0.75), 0.5, 0));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay), "Bad FindIntersections with regular 2 intersections");
		li.clear();
		map.clear();
		// EP 2 - regular no intersections
		interay = new Ray(new Point3D(7, 1, 1), new Vector(1, 1, 0));
		assertEquals(null, s.findIntersections(interay), "Bad FindIntersections with regular no intersections");
		// EP 3 - opposite of regular 2 intersections
		interay = new Ray(new Point3D(-1, 0.5, 0), new Vector(-1, 0, 0));
		assertEquals(null, s.findIntersections(interay), "Bad FindIntersections in opposite of 2 intersections");
		// EP 4 - regular 1 intersections
		interay = new Ray(new Point3D(0, 0.5, 0), new Vector(1, 0, 0));
		li.add(new Point3D(Math.sqrt(0.75), 0.5, 0));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay), "Bad FindIntersections with 1 regular intersections");
		li.clear();
		map.clear();
		// BVA 5 - direction outside that starts in the edge
		interay = new Ray(new Point3D(Math.sqrt(0.75), 0.5, 0), new Vector(1, 0, 0));
		li.add(new Point3D(Math.sqrt(0.75), 0.5, 0));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay),
				"Bad FindIntersections in direction outside that starts in the edge");
		map.clear();
		li.clear();
		// BVA 6 - 2 intersections when one starts in the edge
		interay = new Ray(new Point3D(-Math.sqrt(0.75), 0.5, 0), new Vector(1, 0, 0));
		li.add(new Point3D(Math.sqrt(0.75), 0.5, 0));
		li.add(new Point3D(-Math.sqrt(0.75), 0.5, 0));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay),
				"Bad FindIntersections 2 intersections when one starts in the edge");
		map.clear();
		li.clear();
		// BVA 7 - ray touches the sphere in the continue of the ray
		interay = new Ray(new Point3D(-7, 1, 0), new Vector(1, 0, 0));
		li.add(new Point3D(0, 1, 0));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay), "Bad FindIntersections when ray touches the sphere");
		li.clear();
		map.clear();
		// BVA 8 - the ray touches the sphere at p0
		interay = new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0));
		li.add(new Point3D(0, 1, 0));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay), "Bad FindIntersections when the ray touches the sphere at p0");
		li.clear();
		map.clear();
		// BVA 9 - the ray touches the sphere before it starts
		interay = new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0));
		assertEquals(null, s.findIntersections(interay),
				"Bad FindIntersections when the ray touches the sphere before it starts");
		// BVA 10 - the ray direction goes through the diameter and starts at the exit
		interay = new Ray(new Point3D(-1, 0, 0), new Vector(-1, 0, 0));
		li.add(new Point3D(-1, 0, 0));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay),
				"Bad FindIntersections the ray direction goes through the diameter and starts at the exit");
		li.clear();
		map.clear();
		// BVA 11 - The ray starts from the center
		interay = new Ray(Point3D.ZERO, new Vector(1, 1, 1));
		li.add(new Point3D(Math.sqrt(1.0 / 3), Math.sqrt(1.0 / 3), Math.sqrt(1.0 / 3)));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay), "Bad FindIntersections when the ray starts from the center");
		li.clear();
		map.clear();
		// BVA 12 - ray starts at the edge and goes through the diameter
		interay = new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 0));
		li.add(new Point3D(-1, 0, 0));
		li.add(new Point3D(1, 0, 0));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay),
				"Bad FindIntersections ray starts at the edge and goes through the diameter");
		li.clear();
		map.clear();
		// BVA 13 - the ray direction goes through the diameter and starts after the
		// exit
		interay = new Ray(new Point3D(-2, 0, 0), new Vector(-1, 0, 0));
		assertEquals(null, s.findIntersections(interay),
				"Bad FindIntersections the ray direction goes through the diameter and starts after the exit");
		// BVA 14 - the ray direction goes through the diameter and starts before the
		// exit
		interay = new Ray(new Point3D(3, 0, 0), new Vector(-1, 0, 0));
		li.add(new Point3D(-1, 0, 0));
		li.add(new Point3D(1, 0, 0));
		map.put(s, li);
		assertEquals(map, s.findIntersections(interay),
				"Bad FindIntersections the ray direction goes through the diameter and starts before the exit");
		// BVA 15 - outside orthogonal to the vector from p0 to center
		interay = new Ray(new Point3D(-2, 0, 0), new Vector(0, 1, 0));
		assertEquals(null, s.findIntersections(interay),
				"Bad FindIntersections when outside orthogonal to the vector from p0 to center");
	}

}
