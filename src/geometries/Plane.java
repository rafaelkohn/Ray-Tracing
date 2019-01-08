package geometries;

import primitives.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Plane class represents a plane using a point and a normal vector
 */

public class Plane extends Geometry {
	protected Point3D _p;
	protected Vector _normal;

	// ***************** Constructors ********************** //

	/**
	 * Ctor, receives the point and normal for the plane
	 * 
	 * @param pt
	 * @param normal
	 * @param em emission color
	 * @param material the material of the geometry
	 */
	public Plane(Point3D pt, Vector normal, Color em, Material material) {
		super(em, material);
		_p = new Point3D(pt);
		_normal = new Vector(normal).normalization();
	}

	/**
	 * Ctor, receives the 3 vertexes for the triangle's ctor
	 * 
	 * @param vertexA
	 * @param vertexB
	 * @param vertexC
	 * @param em       emission color
	 * @param material the material of the geometry
	 */
	public Plane(Point3D vertexA, Point3D vertexB, Point3D vertexC, Color em, Material material) {
		super(em, material);
		_normal = vertexB.subtract(vertexA).crossproduct(vertexC.subtract(vertexA)).normalization();
		_p = new Point3D(vertexA);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * point on the plane getter
	 * 
	 * @return a point on the plane
	 */
	public Point3D get_p() {
		return _p;
	}

	/**
	 * normal to the vector getter
	 * 
	 * @return a vector that is orthogonal to the plane
	 */
	public Vector getNormal() {
		return new Vector(_normal);
	}

	// ***************** Operations ******************** //
	/**
	 * returns the vector of the normal to the plane in a certain point
	 * 
	 * @param intersection the intersection between the normal and the Plane
	 * @return the normal
	 */
	public Vector getNormal(Point3D intersection) {
		return new Vector(_normal);
	}

	/**
	 * finds the intersection a ray has with the plane. if it does not intersect
	 * with the plane it returns null
	 * 
	 * @param rl the ray
	 * @return the list of the intersections
	 */
	public Map<Geometry, List<Point3D>> findIntersections(Ray rl) {
		double denom = _normal.dotProduct(rl.get_direction());
		if (Util.isZero(denom))
			return null;
		Map<Geometry, List<Point3D>> map = new HashMap<>();
		;
		Vector u;
		try {
			u = _p.subtract(rl.get_p0());
		} catch (Exception e) {
			List<Point3D> li = new ArrayList<>();
			li.add(_p);
			map.put(this, li);
			return map;
		}
		List<Point3D> li = new ArrayList<>();
		double t = _normal.dotProduct(u) / denom;
		if (t < 0)
			return null;
		if (Util.isZero(t)) {
			li.add(rl.get_p0());
			map.put(this, li);
			return map;
		}
		li.add(rl.get_p0().add(rl.get_direction().scale(t)));
		map.put(this, li);
		return map;
	}
}
