package geometries;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import primitives.*;

/**
 * Cylinder class. tube with finite height (and bases)
 */

public class Cylinder extends Tube {
	double _height;
	Circle _bottomCap;
	Circle _upperCap;
	// ***************** Constructors ********************** //

	/**
	 * Ctor , receives the radius, axis point and direction for the Cylinder
	 * 
	 * @param radius
	 * @param axisPt  the axis point
	 * @param axisDir the direction
	 * @param height
	 */
	public Cylinder(double radius, Point3D axisPt, Ray axisDir, Color em, Material material, double height) {
		super(radius, axisPt, axisDir, em, material);
		_height = height;
		_bottomCap = new Circle(_radius, _axisPoint, _axisDirection.get_direction(), em, material);
		_upperCap = new Circle(_radius, _axisPoint.add(_axisDirection.get_direction().scale(_height)),
				_axisDirection.get_direction(), em, material);
	}
	
	/**
	 * Ctor , receives the radius, axis point and direction for the Cylinder
	 * @param radius
	 * @param axisPt the axis point
	 * @param axisDir the axis direction
	 * @param em emission color
	 * @param material
	 * @param height
	 */
	public Cylinder(double radius, Point3D axisPt, Vector axisDir, Color em, Material material, double height) {
		super(radius, axisPt, new Ray(axisPt,axisDir), em, material);
		_height = height;
		_bottomCap = new Circle(_radius, _axisPoint, _axisDirection.get_direction(), em, material);
		_upperCap = new Circle(_radius, _axisPoint.add(_axisDirection.get_direction().scale(_height)),
				_axisDirection.get_direction(), em, material);
	}

	// ***************** Getters/Setters ********************** //
	public double get_height() {
		return _height;
	}
	// ***************** Operations ******************** //

	/**
	 * returns the vector of the normal to the sphere in a certain point
	 */
	@Override
	public Vector getNormal(Point3D intersection) {
		Point3D pt = this._axisDirection.get_p0().add(this._axisDirection.get_direction().scale(_height));
		if (intersection.subtract(this._axisDirection.get_p0()).dotProduct(this._axisDirection.get_direction()) == 0)
			return this._axisDirection.get_direction();
		if (intersection.subtract(pt).dotProduct(this._axisDirection.get_direction()) == 0)
			return this._axisDirection.get_direction().scale(-1);
		return super.getNormal(intersection);
	}

	/**
	 * finds intersections with ray. Still incomplete
	 */
	@Override
	public Map<Geometry, List<Point3D>> findIntersections(Ray rl) {
		Map<Geometry, List<Point3D>> map = super.findIntersections(rl);
		Map<Geometry, List<Point3D>> upperCapIntersections = _bottomCap.findIntersections(rl);
		Map<Geometry, List<Point3D>> bottomCapIntersections = _upperCap.findIntersections(rl);
		Map<Geometry, List<Point3D>> result = new HashMap<>();
		List<Point3D> li = new ArrayList<>();
		if (map != null) {
			double dis1, dis2, disToCir1, disToCir2;
			double radiusSqr = _radius * _radius;
			for (Point3D p : map.get(this)) {
				dis1 = p.distancesqr(_axisDirection.get_p0());
				dis2 = p.distancesqr(_upperCap.getCenter());
				disToCir1 = Math.sqrt(dis1 - radiusSqr);
				disToCir2 = Math.sqrt(dis2 - radiusSqr);
				if (disToCir2 < _height && disToCir1 < _height) {
					li.add(p);
				}
			}
		}
		if (upperCapIntersections != null)
			li.add(upperCapIntersections.get(_bottomCap).get(0));
		if (bottomCapIntersections != null)
			li.add(bottomCapIntersections.get(_upperCap).get(0));
		if (li.isEmpty())
			return null;
		result.put(this, li);
		return result;
	}
}