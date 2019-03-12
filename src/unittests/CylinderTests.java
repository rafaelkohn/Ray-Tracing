package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Cylinder;
import geometries.Geometry;

public class CylinderTests {

	@Test
	public void testNormal() {
		Vector v = new Vector(0, 0, 1);
		Ray ray = new Ray(Point3D.ZERO, v);
		Cylinder cy = new Cylinder(7, Point3D.ZERO, ray, Color.WHITE, new Material(1, 1, 1, 1, 1), 7);
		// lower base of the cylinder
		assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point3D(5, 0, 0)), "bad getNormal at the lower base");
		// upper base of the cylinder
		assertEquals(new Vector(0, 0, -1), cy.getNormal(new Point3D(5, 0, 7)), "baf getNormal at the upper base");
	}

	@Test
	public void testFindIntersections() {
		Vector v = new Vector(0, 0, 1);
		Ray ray = new Ray(Point3D.ZERO, v);
		Cylinder cy = new Cylinder(1, Point3D.ZERO, ray, Color.WHITE, new Material(1, 1, 1, 1, 1), 1);
		List<Point3D> li = new ArrayList<>();
		Map<Geometry, List<Point3D>> map = new HashMap<>();
		// intersection ray is parallel to the cylinder from inside starts at the bottom
		// cap
		li.add(Point3D.ZERO);
		li.add(new Point3D(0, 0, 1));
		map.put(cy, li);
		assertEquals(map, cy.findIntersections(ray),
				"bad findIntersections when ray is parallel to the cylinder from inside starts at the bottom cap");
		// intersection ray is parallel to the cylinder and inside the edge
		ray = new Ray(new Point3D(0, 1, 0), v);
		assertEquals(null, cy.findIntersections(ray),
				"bad findIntersections when the ray is inside the edge and is parallel to the cylinder");
		// intersection ray is parallel to the tube and outside the cylinder
		ray = new Ray(new Point3D(0, 2, 0), v);
		assertEquals(null, cy.findIntersections(ray),
				"bad findIntersections when the ray is outside the cylinder and is parallel to the cylinder");
		// intersection ray is orthogonal to the cylinder and crosses it after it ends and
		ray = new Ray(new Point3D(0, 0, 400), new Vector(1, 0, 0));
		assertEquals(null, cy.findIntersections(ray),
				"bad findIntersections when the ray is orthogonal to the cylinder and crosses it after it ends and");
		//intersection ray is orthogonal to the cylinder and crosses it's bottom cap
		
		//intersection ray is orthogonal to the cylinder 
	}
}
