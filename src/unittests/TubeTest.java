package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import geometries.Tube;
import geometries.Geometry;
import primitives.*;

class TubeTest {

	@Test
	public void testNormal() {
		Vector v = new Vector(0, 0, 1);
		Ray ray = new Ray(Point3D.ZERO, v);
		Tube tu = new Tube(7, Point3D.ZERO, ray, Color.WHITE, new Material(1, 1, 0, 0, 1));
		// point inside the tube
		assertEquals(new Vector(3 / Math.sqrt(10.0), 1 / Math.sqrt(10.0), 0), tu.getNormal(new Point3D(3, 1, 2)),
				" bad getNormal at the tube");
		// parallel to the tube
		assertEquals(new Vector(1, 0, 0), tu.getNormal(new Point3D(7, 0, 0)), "bad getNormal with orthogonal vector");
	}

	@Test
	public void testFindIntersections() {
		Vector v = new Vector(0, 0, 1);
		Ray ray = new Ray(Point3D.ZERO, v);
		Tube tu = new Tube(1, Point3D.ZERO, ray, Color.WHITE, new Material(2, 2, 0, 0, 2));
		List<Point3D> li = new ArrayList<>();
		Map<Geometry, List<Point3D>> map = new HashMap<>();
		// intersection ray is parallel to the tube from inside
		assertEquals(null, tu.findIntersections(ray),
				"bad findIntersections when the ray is parallel to the tube from inside");
		// intersection ray is parallel to the tube and inside the edge
		ray = new Ray(new Point3D(0, 1, 0), v);
		assertEquals(null, tu.findIntersections(ray),
				"bad findIntersections when the ray is inside the edge and is parallel to the tube");
		// intersection ray is parallel to the tube and outside the tube
		ray = new Ray(new Point3D(0, 2, 0), v);
		assertEquals(null, tu.findIntersections(ray),
				"bad findIntersections when the ray is outside the tube and is parallel to the tube");
		// intersection ray is orthogonal to the tube and goes through the axis starts
		// before it
		ray = new Ray(new Point3D(1, 1, 0), new Vector(-1, -1, 0));
		li.add(new Point3D(Math.sqrt(0.5), Math.sqrt(0.5), 0));
		li.add(new Point3D(-Math.sqrt(0.5), -Math.sqrt(0.5), 0));
		map.put(tu, li);
		assertEquals(map, tu.findIntersections(ray),
				"bad findIntersections when the ray is orthogonal to the tube and goes through the axis and starts before it");
		map.clear();
		li.clear();
		// intersection ray is orthogonal to the tube and starts at the axis
		ray = new Ray(new Point3D(0, 0, 0), new Vector(-1, -1, 0));
		li.add(new Point3D(-Math.sqrt(0.5), -Math.sqrt(0.5), 0));
		map.put(tu, li);
		assertEquals(map, tu.findIntersections(ray),
				"bad findIntersections when the ray is orthogonal to the tube and goes through the axis and starts at it");
		map.clear();
		li.clear();
		// intersection ray is orthogonal to the tube and starts after the axis
		ray = new Ray(new Point3D(-1, -1, 0), new Vector(-1, -1, 0));
		assertEquals(null, tu.findIntersections(ray),
				"bad findIntersections when the ray is orthogonal to the tube and goes through the axis and after it");
		// intersection ray is orthogonal to the tube and starts at the first edge
		ray = new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 0));
		li.add(new Point3D(1, 0, 0));
		li.add(new Point3D(-1, 0, 0));
		map.put(tu, li);
		assertEquals(map, tu.findIntersections(ray),
				"bad findIntersections when the ray is orthogonal to the tube, goes through the axis and starts at the first edge");
		li.clear();
		map.clear();
		// intersection ray is orthogonal to the tube and starts at the second edge
		ray = new Ray(new Point3D(-1, 0, 0), new Vector(-1, 0, 0));
		li.add(new Point3D(-1, 0, 0));
		map.put(tu, li);
		assertEquals(map, tu.findIntersections(ray),
				"bad findIntersections when the ray is orthogonal to the tube, goes through the axis and starts at the second edge");
		li.clear();
		map.clear();
		// intersection ray is orthogonal to the tube and touches it before it starts
		ray = new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0));
		assertEquals(null, tu.findIntersections(ray),
				"bad findIntersections when the ray is orthogonal to the tube and touches it before it starts");
		// intersection ray is orthogonal to the tube and touches it when it starts
		ray = new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0));
		li.add(new Point3D(0, 1, 0));
		map.put(tu, li);
		assertEquals(map, tu.findIntersections(ray),
				"bad findIntersections when the ray is orthogonal to the tube and touches it when it starts");
		li.clear();
		map.clear();
		// intersection ray is orthogonal to the tube and touches it after it starts
		ray = new Ray(new Point3D(-1, 1, 0), new Vector(1, 0, 0));
		li.add(new Point3D(0, 1, 0));
		map.put(tu, li);
		assertEquals(map, tu.findIntersections(ray),
				"bad findIntersections when the ray is orthogonal to the tube and touches it after it starts");
		li.clear();
		map.clear();
		/*// intersection ray starts before the tube and crosses it
		ray = new Ray(new Point3D(3, 2, 1.37), new Vector(-5, -2, -1.37));
		li.add(new Point3D(0.3457847, 9383139, 0.642745));
		li.add(new Point3D(-0.89751, 0.441, 0.30196));
		map.put(tu, li);
		assertEquals(map, tu.findIntersections(ray),
				"bad findIntersections when the ray starts before the tube and crosses it");
		li.clear();
		map.clear();*/
	}
}
