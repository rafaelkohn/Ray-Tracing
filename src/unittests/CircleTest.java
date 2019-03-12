package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

class CircleTest {

	@Test
	public void testNormal() {
		Circle cir = new Circle(1,new Point3D(1,1,1),new Vector(0,0,-1),Color.WHITE,new Material(1,1,1,1,1));
		assertEquals(
				  new Vector(0, 0, -1),
				  cir.getNormal(Point3D.ZERO),
				  "Bad GetNormal");
	}
	
	@Test
	public void testFindIntersections() {
		Circle cir = new Circle(1,Point3D.ZERO,new Vector(0,0,-1),Color.WHITE,new Material(1,1,1,1,1));
		//VBA 1 - ray intersects with the circle at the center
		Map<Geometry,List<Point3D>> map = new HashMap<>();
		List<Point3D> li = new ArrayList<Point3D>();
		Ray interay =new Ray(new Point3D(0,0,1), new Vector(0,0,-1));
		li.add(Point3D.ZERO);
		map.put(cir, li);
		assertEquals(map, cir.findIntersections(interay), "Bad FindIntersections at the center of the circle");
		li.clear();
		map.clear();
		//VBA 2 -ray intersects at the edge
		interay = new Ray(new Point3D(Math.sqrt(0.5),Math.sqrt(0.5),1),new Vector(0,0,-1));
		assertEquals(null, cir.findIntersections(interay), "Bad FindIntersections at the edge");
		//EP 1 - ray intersects inside the circle
		interay = new Ray(new Point3D(0.5,0.5,1),new Vector(0,0,-1));
		li.add(new Point3D(0.5,0.5,0));
		map.put(cir, li);
		assertEquals(map, cir.findIntersections(interay), "Bad FindIntersections in a regular intersection");
		//EP 2 - ray does not intersect with the circle (but does with the plane)
		interay = new Ray(new Point3D(-1,-1,1), new Vector(0,0,-1));
		assertEquals(null, cir.findIntersections(interay), "Bad FindIntersections outside of the circle");
	}
}
