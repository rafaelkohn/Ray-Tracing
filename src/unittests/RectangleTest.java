package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import geometries.Geometry;
import geometries.Rectangle;
import primitives.*;

class RectangleTest {

	@Test
	public void testNormal() {
		Rectangle rec = new Rectangle(new Point3D(0, 1, 0), new Point3D(0, 0, 0), new Point3D(1, 0, 0), Color.WHITE,
				new Material(1, 1, 0, 0, 1));
		assertEquals(new Vector(0, 0, -1), rec.getNormal(Point3D.ZERO), "Bad GetNormal");
	}

	@Test
	public void testFindIntersections() {
		Rectangle rec = new Rectangle(new Point3D(0, 1, 0), new Point3D(0, 0, 0), new Point3D(1, 0, 0), Color.WHITE,
				new Material(1, 1, 0, 0, 1));
		List<Point3D> li = new ArrayList<>();
		Map<Geometry, List<Point3D>> map = new HashMap<>();

		// 1 - outside the rectangle in the continue of the edge
		Ray interay = new Ray(new Point3D(2, 0, 1), new Vector(0, 0, -1));
		assertEquals(null, rec.findIntersections(interay),
				"Bad FindIntersections outside the rectangle in the continue of the edge");
		// 2 - outside the rectangle in the trapped part
		interay = new Ray(new Point3D(-0.5, -0.5, 1), new Vector(0, 0, -1));
		assertEquals(null, rec.findIntersections(interay),
				"Bad FindIntersections outside the rectangle in the trapped part");
		// 3 - at the edge
		interay = new Ray(new Point3D(0.5, 0, 1), new Vector(0, 0, -1));
		li.add(new Point3D(0.5,0,0));
		map.put(rec, li);
		assertEquals(map, rec.findIntersections(interay), "Bad FindIntersections at the edge");
		li.clear();
		map.clear();
		// 4 - inside the rectangle
		interay = new Ray(new Point3D(0.5, 0.5, 1), new Vector(0, 0, -1));
		li.add(new Point3D(0.5, 0.5, 0));
		map.put(rec, li);
		assertEquals(map, rec.findIntersections(interay), "Bad FindIntersections inside the rectangle");
		// 5 - at the vertex
		interay = new Ray(new Point3D(0, 0, 1), new Vector(0, 0, -1));
		assertEquals(null, rec.findIntersections(interay), "Bad FindIntersections at the vertex");
		// 6 - outside the rectangle at the trapper part
		interay = new Ray(new Point3D(0.5, -0.5, 1), new Vector(0, 0, -1));
		assertEquals(null, rec.findIntersections(interay),
				"Bad FindIntersections outside the rectangle at the trapper part");
	}

}
