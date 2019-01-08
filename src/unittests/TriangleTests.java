package unittests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Triangle;
import geometries.Geometry;
import java.util.Map;
import java.util.HashMap;

public class TriangleTests {

	@Test
	public void testNormal() {
		Triangle tr = new Triangle(new Point3D(1,1,1),new Point3D(1,2,1),new Point3D(2,4,1),Color.WHITE,new Material(1,1,1));
		assertEquals(
				  new Vector(0, 0, -1),
				  tr.getNormal(Point3D.ZERO),
				  "Bad GetNormal");
	}
	@Test
	public void testFindIntersections() {
		Triangle tr = new Triangle(new Point3D(1,1,0),new Point3D(4,1,0),new Point3D(4,4,0),Color.WHITE,new Material(1,1,1));
		List<Point3D> li = new ArrayList<>();
		Map<Geometry,List<Point3D>> map = new HashMap<>();
		
		//1 - outside the triangle in the continue of the edge
		Ray interay =new Ray(new Point3D(0.5,0.5,1),new Vector(0,0,-1));
		assertEquals(null, tr.findIntersections(interay),"Bad FindIntersections outside the triangle in the continue of the edge");
		//2 - outside the triangle in the trapped part
		interay =new Ray(new Point3D(2,0.5,1),new Vector(0,0,-1));
		assertEquals(null, tr.findIntersections(interay),"Bad FindIntersections outside the triangle in the trapped part");
		//3 - at the edge
		interay =new Ray(new Point3D(2,1,1),new Vector(0,0,-1));
		assertEquals(null, tr.findIntersections(interay),"Bad FindIntersections at the edge");
		//4 - inside the triangle
		interay =new Ray(new Point3D(2,1.5,1),new Vector(0,0,-1));
		li.add(new Point3D(2,1.5,0));
		map.put(tr, li);
		assertEquals(map, tr.findIntersections(interay),"Bad FindIntersections inside the triangle");
		//5 - at the vertex
		interay =new Ray(new Point3D(1,1,1),new Vector(0,0,-1));
		assertEquals(null, tr.findIntersections(interay),"Bad FindIntersections at the vertex");
		//6 - outside the triangle at the trapper part
		interay =new Ray(new Point3D(0.5,1,1),new Vector(0,0,-1));
		assertEquals(null, tr.findIntersections(interay),"Bad FindIntersections outside the triangle at the trapper part");
	}
}
