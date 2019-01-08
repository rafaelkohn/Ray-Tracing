package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import geometries.*;
import primitives.*;


public class PlaneTest {

	@Test
	public void testNormal() {
		 Plane p = new Plane(new Point3D(1,1,1),new Vector(0,0,-1),Color.WHITE,new Material(1,1,1));
			assertEquals(
					  new Vector(0, 0, -1),
					  p.getNormal(Point3D.ZERO),
					  "Bad GetNormal");
	}
	@Test
	public void testFindIntersections() {
		Plane p = new Plane( Point3D.ZERO , new Vector(0,0,1),Color.WHITE,new Material(1,1,1));
		//EP-1 -Ray intersects the plane
		Ray interay = new Ray(new Point3D(1,1,1) , new Vector(1,1,-1));
		List<Point3D> li = new ArrayList<Point3D>();
		Map<Geometry,List<Point3D>> map = new HashMap<>();
		li.add(new Point3D(2,2,0));
		map.put(p, li);
		assertEquals(map, p.findIntersections(interay),"Bad FindIntersections");
		map.clear();
		li.clear();
		//2 -Ray does not intersect the plane
		interay = new Ray(new Point3D(5,5,5),new Vector(5,5,5));
		assertEquals(null,p.findIntersections(interay),"Bad FindIntersections");
		//VBA-3 -Ray is parallel to the plane(not included)
		interay = new Ray(new Point3D(0,0,1),new Vector(1,1,0));
		assertEquals(null,p.findIntersections(interay),"Bad FindIntersections when Ray is parallel to the plane (not included)");
		//4 - Ray is parallel to the plane(included)
		interay =new Ray(Point3D.ZERO, new Vector(1,1,0));
		assertEquals(null,p.findIntersections(interay),"Bad FindIntersections when Ray is parallel to the plane(included)");
		//5 - Ray is orthogonal to the plane - before the plane
		interay =new Ray(new Point3D(1,1,1), new Vector(0,0,-1));
		li.add(new Point3D(1,1,0));
		map.put(p,li);
		assertEquals(map,p.findIntersections(interay),"Bad FindIntersections when Ray is orthogonal to the plane - before the plane");
		map.clear();
		li.clear();
		//6 -Ray is orthogonal to the plane - in the plane
		interay =new Ray(new Point3D(1,1,0), new Vector(0,0,-1));
		li.add(new Point3D(1,1,0));
		map.put(p, li);
		assertEquals(map,p.findIntersections(interay),"Bad FindIntersections when Ray is orthogonal to the plane - in the plane");
		map.clear();
		li.clear();
		//7 - Ray is orthogonal to the plane - after the plane
		interay =new Ray(new Point3D(1,1,-1), new Vector(0,0,-1));
		assertEquals(null,p.findIntersections(interay),"Bad FindIntersections when Ray is orthogonal to the plane - after the plane");
		//8 - Ray begins at the plane
		interay = new Ray(Point3D.ZERO, new Vector(1,1,1));
		li.add(Point3D.ZERO);
		map.put(p, li);
		assertEquals(map,p.findIntersections(interay),"Bad FindIntersections when Ray begins at the plane");
	}
}