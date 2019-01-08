package geometries;

import java.util.List;
import java.util.Map;

import primitives.*;

/**
 * circle class on a plane represented by the center and by the radius
 */
public class Circle extends Plane {
	double _radius;
	// ***************** Constructors ********************** //

	/**
	 * Circle Ctor, receives the parameters directy for the circle
	 * 
	 * @param r      the radius
	 * @param center the center of the circle
	 * @param normal the normal to the plane
	 * @param em     emission color
	 * @param mat    the material of the geometry
	 */
	public Circle(double r, Point3D center, Vector normal, Color em, Material mat) {
		super(center, normal, em, mat);
		_radius = r;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * radius getter
	 * 
	 * @return the radius of the circle
	 */
	public double getRadius() {
		return _radius;
	}

	// ***************** Operations ********************** //
	/**
	 * finds the intersection a ray has with the rectangle. if it does not intersect
	 * with the rectangle it returns null
	 * 
	 * @param rl the ray
	 * @return the list of the intersections
	 */
	@Override
	public Map<Geometry, List<Point3D>> findIntersections(Ray rl) {
		Map<Geometry, List<Point3D>> map = super.findIntersections(rl);
		double dis = map.get(this).get(0).distance(_p);
		if (dis >= 0 && dis < _radius)
			return map;
		return null;
	}
}