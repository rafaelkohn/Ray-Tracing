package geometries;

import java.util.List;
import java.util.Map;

import primitives.*;

/**
 * Rectangle class, implemented using the two vector
 */
public class Rectangle extends Plane {
	Vector _vUp;
	Vector _vUpNormalized;
	Vector _vRight;
	Vector _vRightNormalized;

	// ***************** Constructors ********************** //

	/**
	 * Rectangle ctor, receives the vectors for the rectangle
	 * 
	 * @param point
	 * @param up       vertical vector
	 * @param right    horizontal vector
	 * @param em       emission color
	 * @param material the material of the geometry
	 */
	public Rectangle(Point3D point, Vector up, Vector right, Color em, Material material) {
		super(point, up.crossproduct(right), em, material);
		_vUp = new Vector(up);
		_vUpNormalized = new Vector(_vUp).normalization();
		_vRight = new Vector(right);
		_vRightNormalized = new Vector(_vRight).normalization();
		if (_vUp.dotProduct(_vRight) != 0)
			throw new IllegalArgumentException("The angles of the rectangle are not equal to 90 degrees");
	}

	/**
	 * Rectangle ctor, receives the vertexes of the rectangle
	 * 
	 * @param _a       upper right vertex
	 * @param _b       lower right vertex
	 * @param _c       lower left vertex
	 * @param em       emission color
	 * @param material the material of the geometry
	 */
	public Rectangle(Point3D _a, Point3D _b, Point3D _c, Color em, Material material) {
		this(_b, _a.subtract(_b), _c.subtract(_b), em, material);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * up vector getter
	 * 
	 * @return up vector
	 */
	public Vector get_vUp() {
		return new Vector(_vUp);
	}

	/**
	 * normalized up vector getter
	 * 
	 * @return up direction
	 */
	public Vector get_vUpNormalized() {
		return new Vector(_vUpNormalized);
	}

	/**
	 * right vector getter
	 * 
	 * @return right vector
	 */
	public Vector get_vRight() {
		return new Vector(_vRight);
	}

	/**
	 * normalized right vector getter
	 * 
	 * @return right direction
	 */
	public Vector get_vRightNormalized() {
		return new Vector(_vRightNormalized);
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
		if (map == null)
			return null;
		Point3D head = new Point3D(map.get(this).get(0));
		if (_p.equals(head))
			return null;
		Vector vDiagonal = head.subtract(_p);
		double check = vDiagonal.dotProduct(_vRightNormalized);
		if (check < 0 || check > _vRight.length())
			return null;
		check = vDiagonal.dotProduct(_vUpNormalized);
		if (check < 0 || check > _vUp.length())
			return null;
		return map;
	}

}
