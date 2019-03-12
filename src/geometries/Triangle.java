package geometries;

import java.util.List;
import java.util.Map;
import primitives.*;

/**
 * Triangle class represents a triangle on a plane using its 3 vertexes
 */

public class Triangle extends Plane {
	Point3D _p1;
	Point3D _p2;
	Point3D _p3;

	// ***************** Constructors ********************** //
	/**
	 * Ctor , receives three points and makes them the vertexes for the triangle
	 * 
	 * @param _a       vertex a
	 * @param _b       vertex b
	 * @param _c       vertex c
	 * @param em       emission color
	 * @param material the material of the geometry
	 */
	public Triangle(Point3D _a, Point3D _b, Point3D _c, Color em, Material material) {
		super(_a, _b, _c, em, material);
		this._p1 = new Point3D(_a);
		this._p2 = new Point3D(_b);
		this._p3 = new Point3D(_c);
	}

	// ***************** Getters/Setters ********************** //
	/**
	 * first vertex getter
	 * 
	 * @return a vertex
	 */
	public Point3D getVertexA() {
		return _p1;
	}

	/**
	 * second vertex getter
	 * 
	 * @return b vertex
	 */
	public Point3D getVertexB() {
		return  _p2;
	}

	/**
	 * third vertex getter
	 * 
	 * @return c vertex
	 */
	public Point3D getVertexc() {
		return _p3;
	}

	// ***************** Operations ******************** //
	/**
	 * finds the intersection a ray has with the triangle. if it does not intersect
	 * with the triangle it returns null
	 * 
	 * @param rl the ray
	 * @return the list of the intersections
	 */
	@Override
	public Map<Geometry, List<Point3D>> findIntersections(Ray rl) {
		Map<Geometry, List<Point3D>> map = super.findIntersections(rl);
		if (map == null)
			return null;
		Point3D Pt = map.get(this).get(0);
		double d1, d2, d3;
		try {
			Vector v1 = new Vector(_p1.subtract(rl.get_p0()));
			Vector v2 = new Vector(_p2.subtract(rl.get_p0()));
			Vector v3 = new Vector(_p3.subtract(rl.get_p0()));
			Vector N1 = v1.crossproduct(v2).normalization();
			Vector N2 = v2.crossproduct(v3);
			N2.normalization();
			Vector N3 = v3.crossproduct(v1);
			N3.normalization();
			d1 = Pt.subtract(rl.get_p0()).dotProduct(N1);
			d2 = Pt.subtract(rl.get_p0()).dotProduct(N2);
			d3 = Pt.subtract(rl.get_p0()).dotProduct(N3);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
		if (d1 > 0.0 && d2 > 0.0 && d3 > 0.0)
			return map;
		if (d1 < 0.0 && d2 < 0.0 && d3 < 0.0)
			return map;
		return null;
	}
}
