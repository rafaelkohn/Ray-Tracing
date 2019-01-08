package primitives;

/**
 * represents a point in a three dimensional Cartesian coordinate system 
 */

public class Point2D {
	protected Coordinate _x;
	protected Coordinate _y;

	// ***************** Constructors ********************** //
	/**
	 * Ctor , Receives the two coordinates for the point
	 * @param x 1st coordinate
	 * @param y 2nd coordinate
	 */
	Point2D(Coordinate x, Coordinate y) {
		_x = new Coordinate(x);
		_y = new Coordinate(y);
	}

	/**
	 * Ctor , Receives the two coordinates as double for the point
	 * @param x 1st coordinate
	 * @param y 2nd coordinate
	 */
	Point2D(double xC, double yC) {
		this._x = new Coordinate(xC);
		this._y = new Coordinate(yC);
	}

	/**
	 * Ctor , Receives another point and copies it to this one
	 * @param copy the copied point
	 */
	Point2D(Point2D copy) {
		this._x = new Coordinate(copy._x);
		this._y = new Coordinate(copy._y);
	}

	// ***************** Getters/Setters ********************** //
	public Coordinate get_x() {
		return _x;
	}

	public Coordinate get_y() {
		return _y;
	}

	// ***************** Administration ******************** //

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point2D))
			return false;
		Point2D other = (Point2D) obj;
		return (this._x.equals(other._x) && this._y.equals(other._y));
	}

	@Override
	public String toString() {
		return _x + "," + _y;
	}
}
