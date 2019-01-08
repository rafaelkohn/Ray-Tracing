package geometries;
import primitives.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tube class.
 * Represents the tube with axis starting point and direction
 */

public class Tube extends RadialGeometry{
	Point3D _axisPoint;
	Ray _axisDirection;
	// ***************** Constructors ********************** // 
	
	/**
	 * Ctor , receives the radius, axis point and direction for the tube
	 * @param radius
	 * @param axisPt the axis point
	 * @param axisDir the direction
	 */
	public Tube(double radius , Point3D axisPt , Ray axisDir, Color em, Material material){
		super(radius,em,material);
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
	 * @param intersection the intersection between the normal and the Tube
	 * @return the normal
	 */
	public Vector getNormal(Point3D intersection) {
		double t = _axisDirection.get_direction().dotProduct(intersection.subtract(_axisDirection.get_p0()));
		if(t==0)
			return new Vector(intersection.subtract(_axisDirection.get_p0())).normalization();
		Point3D O = new Point3D(_axisDirection.get_p0().add(_axisDirection.get_direction().scale(t)));
		return new Vector(intersection.subtract(O)).normalization();
	}
	
	/**
	 * finds intersections with ray. Still incomplete
	 */
	public Map<Geometry,List<Point3D>> findIntersections(Ray rl){
		double A,B,C, ts,te,t1,t2;
		List<Point3D> li = new ArrayList<>();
		Map<Geometry,List<Point3D>> map = new HashMap<>();
		Vector dP , v1 ,v2;
		v1 = new Vector(rl.get_direction());
		v2 = new Vector(_axisDirection.get_direction());
		if(_axisPoint.equals(rl.get_p0()))
			dP = new Vector(rl.get_p0().subtract(_axisPoint.add(v2)));
		else
			dP = new Vector(rl.get_p0().subtract(_axisPoint));
		if(Util.isZero(v1.dotProduct(v2))) {
			A = v1.lengthsqr();
			if(Util.isZero(dP.dotProduct(v2)))
				B = 2 * v1.dotProduct(dP);
			else
				B = 2 * v1.dotProduct(dP.subtract(v2.scale(dP.dotProduct(v2))));
		}
		else {
			A = v1.subtract(v2.scale(v1.dotProduct(v2))).lengthsqr();
			B = 2*v1.subtract(v2.scale(v1.dotProduct(v2))).dotProduct(dP.subtract(v2.scale(dP.dotProduct(v2))));
		}
		if(Util.isZero(dP.dotProduct(v2)))
			C = dP.lengthsqr() - _radius*_radius;
		else
			C = dP.subtract(v2.scale(dP.dotProduct(v2))).lengthsqr() - _radius*_radius;
		ts = -B/(2*A);
		te = Math.sqrt(B*B-4*A*C)/(2*A);
		if(Util.isZero(te)) {
			li.add(rl.get_p0().add(rl.get_direction().scale(ts)));
			map.put(this, li);
			return map;
		}
		t1 = ts + te;
		t2 = ts - te;
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