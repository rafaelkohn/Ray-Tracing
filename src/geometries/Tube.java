package geometries;

import primitives.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tube class. Represents the tube with axis starting point and direction
 */

public class Tube extends RadialGeometry {
	Point3D _axisPoint;
	Ray _axisDirection;
	// ***************** Constructors ********************** //

	/**
	 * Ctor , receives the radius, axis point and direction for the tube
	 * 
	 * @param radius
	 * @param axisPt  the axis point
	 * @param axisDir the direction
	 */
	public Tube(double radius, Point3D axisPt, Ray axisDir, Color em, Material material) {
		super(radius, em, material);
		_axisPoint = new Point3D(axisPt);
		_axisDirection = new Ray(axisDir);
	}
	// ***************** Getters/Setters ********************** //

	public Point3D get_axisPoint() {
		return _axisPoint;
	}

	public Ray get_axisDirection() {
		return _axisDirection;
	}
	// ***************** Operations ******************** //

	/**
	 * returns the vector of the normal to the sphere in a certain point
	 * 
	 * @param intersection the intersection between the normal and the Tube
	 * @return the normal
	 */
	public Vector getNormal(Point3D intersection) {
		double t = _axisDirection.get_direction().dotProduct(intersection.subtract(_axisDirection.get_p0()));
		if (t == 0)
			return new Vector(intersection.subtract(_axisDirection.get_p0())).normalization();
		Point3D O = new Point3D(_axisDirection.get_p0().add(_axisDirection.get_direction().scale(t)));
		return new Vector(intersection.subtract(O)).normalization();
	}

	/**
	 * finds intersections with ray. Still incomplete
	 */
	public Map<Geometry, List<Point3D>> findIntersections(Ray rl) {

		Map<Geometry, List<Point3D>> map = new HashMap<>();
		List<Point3D> li = new ArrayList<>();

		Vector v = rl.get_direction();
		Vector va = _axisDirection.get_direction();
		Vector tmp1, tmp2 , deltaP;
		double delta, t1,t2;
		double A, B, C;
		double vDotVa = v.dotProduct(va);
		double Rsqr = _radius*_radius;
		
		if (v.equals(va))
			return null;
		if (Util.isZero(vDotVa))
			A = v.lengthsqr();
		else {
			if (v.equals(va.scale(vDotVa)))
				A = 0;
			else {
				A = v.subtract(va.scale(vDotVa)).lengthsqr();
			}
		}
		if (rl.get_p0().equals(_axisDirection.get_p0())) {
			B = 0;
			C = -Rsqr;
		} else {
			deltaP = rl.get_p0().subtract(_axisDirection.get_p0());
			double dpDotVa = deltaP.dotProduct(va);
			if (!Util.isZero(dpDotVa) && deltaP.equals(va.scale(dpDotVa))) {
				B = 0;
				C = -Rsqr;
			} else {
				if (Util.isZero(dpDotVa))
					tmp1 = deltaP;
				else
					tmp1 = deltaP.subtract(va.scale(dpDotVa));
				C = tmp1.lengthsqr() - Rsqr;
				if (Util.isZero(vDotVa))
					B = 2 * v.dotProduct(tmp1);
				else {
					if (v.equals(va.scale(vDotVa)))
						B = 0;
					else {
						tmp2 = v.subtract(va.scale(vDotVa));
						B = 2 * tmp2.dotProduct(tmp1);
					}
				}
			}
		}
		try
		{
			delta = Math.sqrt((B * B) - (4.0 * A * C));
		}
		catch(ArithmeticException e) {
			return null;
		}
		
		t1 = (-B - delta) / (2*A);
		t2 = (-B + delta) / (2*A);

		if(Util.isZero(t1)|| Util.isZero(t2))
			li.add(rl.get_p0());
		if (t1 > 0) {
			li.add(rl.get_p0().add(rl.get_direction().scale(t1)));
		}
		if (t1 != t2 && t2 > 0) {
			li.add(rl.get_p0().add(rl.get_direction().scale(t2)));
		}
		map.put(this, li);
		if (li.isEmpty())
			return null;
		return map;
	}
}