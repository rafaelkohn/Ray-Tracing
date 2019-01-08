package primitives;

/**
 * Vector class
 * goes from origin to its head point
 */

public class Vector {
	Point3D _head;

	// ***************** Constructors ********************** //
	
	/**
	 * Ctor, receives a three dimensional point for the head of the vector
	 * @param head
	 */
	public Vector(Point3D head) {
		if (Point3D.ZERO.equals(head))
			throw new IllegalArgumentException("Zero vector is not allowed");
		this._head = new Point3D(head);

	}

	/**
	 * Ctor, receives the three coordinates for the head of the vector
	 * @param xC x coordinate for the head
	 * @param yC y coordinate for the head
	 * @param zC z coordinate for the head
	 */
	public Vector(double xC, double yC, double zC) {
		if (xC == 0 && yC == 0 && zC == 0)
			throw new IllegalArgumentException("Zero vector is not allowed");
		this._head = new Point3D(xC, yC, zC);
	}

	/**
	 * receives another vector and copies it into this one
	 * @param copy the copied vector
	 */
	public Vector(Vector copy) {
		this._head = new Point3D(copy._head);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * vector's head getter
	 * @return the point of the vector's head
	 */
	public Point3D getHead() {
		return this._head;
	}

	// ***************** Administration ******************** //

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return (this._head.equals(other._head));
	}

	@Override
	public String toString() {
		return _head.toString();
	}

	// ***************** Operations ******************** //
	/**
	 * vector addition
	 */
	public Vector add(Vector vec) {
		return new Vector(this._head.add(vec));
	}

	/**
	 * vector subtraction
	 */
	public Vector subtract(Vector vec) {
		return this._head.subtract(vec._head);
	}

	/**
	 * vector multiplied by a scalar
	 * @param the scalar
	 * @return the scaled vector
	 */
	public Vector scale(double scalar) {
		if (scalar == 0)
			throw new IllegalArgumentException("scaling vector to zero gives a zero vector");
		return new Vector(_head._x._coord * scalar, _head._y._coord * scalar, _head._z._coord * scalar);
	}

	/**
	 * dot product between this and the received vector
	 * @param the other vector
	 * @return the result of the dot operation
	 */
	public double dotProduct(Vector vec) {
		double Xproduct = this._head._x._coord * vec._head._x._coord;
		double Yproduct = this._head._y._coord * vec._head._y._coord;
		double Zproduct = this._head._z._coord * vec._head._z._coord;
		return Xproduct + Yproduct + Zproduct;
	}

	/**
	 * cross product between this and the received vector returns an orthogonal
	 * vector
	 * @param the other vector
	 * @return the result vector
	 */
	public Vector crossproduct(Vector vec) {
		if (vec.equals(this))
			throw new IllegalArgumentException("Vector cross product with itself gives a zero vector");
		return new Vector(this._head._y._coord * vec._head._z._coord - vec._head._y._coord * this._head._z._coord,
				this._head._z._coord * vec._head._x._coord - vec._head._z._coord * this._head._x._coord,
				this._head._x._coord * vec._head._y._coord - vec._head._x._coord * this._head._y._coord);
	}

	/**
	 * calculates the length of the vector and returns it
	 * @return the length of this vector
	 */
	public double length() {
		return _head.distance(Point3D.ZERO);
	}
	
	/**
	 * calculates the length of the vector squared for convenience 
	 * @return the length of this vector squared
	 */
	public double lengthsqr() {
		return _head.distancesqr(Point3D.ZERO);
	}
	
	/**
	 * normalizes the vector and returns an identical vector
	 * @return the normalized vector
	 */
	public Vector normalization() {
		this._head = this.scale(1 / this.length())._head;
		return this;
	}
}
