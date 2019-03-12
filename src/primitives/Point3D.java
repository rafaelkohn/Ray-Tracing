package primitives;

/**
 * represents a point in a three dimensional Cartesian coordinate system 
 */

public class Point3D extends Point2D {
	Coordinate _z;

	public static Point3D ZERO = new Point3D(Coordinate.ZERO, Coordinate.ZERO, Coordinate.ZERO);

	// ***************** Constructors ********************** //
	/**
	 * Ctor , Receives another point and copies it to this one
	 * @param copy the copied point
	 */
	public Point3D(Point3D copy) {
		super(copy);
		this._z = new Coordinate(copy._z);
	}

	 /** Ctor, Receives the three coordinates for the point
	 * @param x 1st coordinate
	 * @param y 2nd coordinate
	 * @param z 3rd coordinate
	 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		super(x, y);
		this._z = new Coordinate(z);
	}

	 /** Ctor, Receives the three coordinates as double for the point
	 * @param x 1st coordinate
	 * @param y 2nd coordinate
	 * @param z 3rd coordinate
	 */
	public Point3D(double xC, double yC, double zC) {
		super(xC, yC);
		this._z = new Coordinate(zC);
	}

	// ***************** Getters/Setters ********************** //
	
	/**
	 * Z coordinate getter
	 * @return the Z coordinate
	 */
	public Coordinate get_z() {
		return this._z;
	}
	

	// ***************** Administration ******************** //

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D other = (Point3D) obj;
		return (super.equals(other) && this._z.equals(other._z));
	}

	@Override
	public String toString() {
		return "(" + super.toString() + "," + _z + ")";
	}

	// ***************** Operations ******************** //
	/**
	 * subtracts one point from this one
	 * @param pt2 the second point
	 * @return vector between from the other point to this one
	 */
	public Vector subtract(Point3D pt2) {
		Coordinate Xcoord = this._x.subtract(pt2._x);
		Coordinate Ycoord = this._y.subtract(pt2._y);
		Coordinate Zcoord = this._z.subtract(pt2._z);
		return new Vector(new Point3D(Xcoord, Ycoord, Zcoord));
	}

	/**
	 * adds a vector to this point
	 * @param vec the vector you are adding to the point
	 * @return the sum of this point and the vector's head
	 */
	public Point3D add(Vector vec) {
		Coordinate Xcoord = this._x.add(vec._head._x);
		Coordinate Ycoord = this._y.add(vec._head._y);
		Coordinate Zcoord = this._z.add(vec._head._z);
		return new Point3D(Xcoord, Ycoord, Zcoord);
	}

	/**
	 * calculates the distance between this point and the received one
	 * @param pt the second point
	 * @return distance between two points
	 */
	public double distance(Point3D pt) {
		double xdis = (this._x._coord - pt._x._coord);
		double ydis = (this._y._coord - pt._y._coord);
		double zdis = (this._z._coord - pt._z._coord);
		return Math.sqrt(xdis*xdis + ydis*ydis + zdis*zdis);
	}
	
	
	/**
	 * the distance between this point and another squared
	 * @param pt the second point
	 * @return distance between two points squared
	 */
	public double distancesqr(Point3D pt) {
		double xdis = (this._x._coord - pt._x._coord);
		double ydis = (this._y._coord - pt._y._coord);
		double zdis = (this._z._coord - pt._z._coord);
		return xdis*xdis + ydis*ydis + zdis*zdis;
	}
}
