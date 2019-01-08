package primitives;

/**
 * Ray class
 * represented by a starting point and a direction vector
 */

public class Ray {
	Point3D _p0;
	Vector _direction;
	// ***************** Constructors ********************** // 
	
	/**
	 * Ctor, receives the starting point and direction for the ray
	 * @param _p starting point of the ray
	 * @param _d direction of the ray
	 */
	public Ray(Point3D _p, Vector _d){
		this._p0 = new Point3D(_p);
		this._direction= new Vector (_d).normalization();
	}
	
	/**
	 * Ctor, receives a ray and copies it into this ray
	 * @param copy
	 */
	public Ray(Ray copy){
		this._p0 = new Point3D(copy._p0);
		this._direction = new Vector(copy._direction).normalization();
	}
	// ***************** Getters/Setters ********************** //
	
	public Point3D get_p0() {
		return _p0;
	}
	
	public Vector get_direction() {
		return _direction;
	}
	// ***************** Administration  ******************** //

	@Override
	public boolean equals(Object obj) {
		if(this == obj)	return true;
		if (obj == null) return false;
		if (!(obj instanceof Ray)) return false;
		Ray other = (Ray)obj;
		return (this._p0.equals(other._p0)&&this._direction.equals(other._direction));
	}
	
	@Override
	public String toString() {
		return "p0=" + _p0 + ", v=" + _direction;
	}
}
