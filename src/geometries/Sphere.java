package geometries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import primitives.*;

/**
 * Sphere class represents a sphere using it's center and radius
 */

public class Sphere extends RadialGeometry {
	Point3D _center;

	// ***************** Constructors ********************** //
	/**
	 * Ctor , receives the radius and the center of the sphere
	 * 
	 * @param radius
	 * @param center   the center of the sphere
	 * @param em       emission color
	 * @param material the material of the geometry
	 */
	public Sphere(double radius, Point3D center, Color em, Material material) {
		super(radius, em, material);
		this._center = center;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * center of the sphere getter
	 * 
	 * @return the point of the center
	 */
	public Point3D get_center() {
		return _center;
	}

	// ***************** Operations ******************** //
	/**
	 * returns the vector of the normal to the sphere in a certain point
	 * 
	 * @param intersection the intersection between the normal and the Sphere
	 * @return the normal
	 */
	public Vector getNormal(Point3D intersection) {
		// n = normalize(P - O)
		return intersection.subtract(_center).normalization();
	}

	/**
	 * finds all of the intersections a ray has with the sphere. if it does not
	 * intersect with the sphere it returns null * @param rl the ray
	 * 
	 * @return the list of the intersections
	 */
	public Map<Geometry, List<Point3D>> findIntersections(Ray rl) {
		double tm, th, d, t1, t2 = 0;
		List<Point3D> li = new ArrayList<Point3D>();
		Map<Geometry, List<Point3D>> map = new HashMap<>();
		if (rl.get_p0().equals(_center)) {
			li.add(rl.get_p0().add(rl.get_direction().scale(_radius)));
			map.put(this, li);
			return map;
		}
		Vector u = _center.subtract(rl.get_p0());
		tm = u.dotProduct(rl.get_direction());
		d = Math.sqrt(u.lengthsqr() - tm * tm);
		if (d == _radius) {
			if (tm < 0)
				return null;
			if (Util.isZero(tm))
				li.add(rl.get_p0());
			if (tm > 0)
				li.add(rl.get_p0().add(rl.get_direction().scale(tm)));
			map.put(this, li);
			return map;
		}
		if (d > _radius)
			return null;
		th = Math.sqrt(_radius * _radius - d * d);
		t1 = tm + th;
		t2 = tm - th;
		if (t1 < 0 && t2 < 0)
			return null;
		if (t1 > 0)
			li.add(rl.get_p0().add(rl.get_direction().scale(t1)));
		if (t2 > 0)
			li.add(rl.get_p0().add(rl.get_direction().scale(t2)));
		if (t1 == 0 || t2 == 0)
			li.add(rl.get_p0());
		map.put(this, li);
		return map;
	}
}
