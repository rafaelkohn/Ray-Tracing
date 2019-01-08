package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Cylinder;

public class CylinderTests {

	@Test
	public void testNormal() {
		Vector v = new Vector(0, 0, 1);
		Ray ray = new Ray(Point3D.ZERO, v);
		Cylinder cy = new Cylinder(7, Point3D.ZERO, ray, Color.WHITE, new Material(1, 1, 1), 7);
		// lower base of the cylinder
		assertEquals(new Vector(0, 0, 1), cy.getNormal(new Point3D(5, 0, 0)), "bad getNormal at the lower base");
		// upper base of the cylinder
		assertEquals(new Vector(0, 0, -1), cy.getNormal(new Point3D(5, 0, 7)), "baf getNormal at the upper base");
	}

}
