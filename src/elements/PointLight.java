package elements;

import primitives.*;

/**
 * Point Light class, shines at all the directions, loses intensity with distance.
 */
public class PointLight extends Light implements LightSource {
	Point3D _position;
	double _kc;
	double _kl;
	double _kq;
	// ***************** Constructors ********************** //

	/**
	 * Ctor , receives the properties of the light source
	 * 
	 * @param color the color of the light
	 * @param pos   the position of the source
	 * @param kc    first of the three factors of attenuation with distance
	 * @param kl    second of the three factors of attenuation with distance
	 * @param kq    third of the three factors of attenuation with distance
	 */
	public PointLight(Color color, Point3D pos, double kc, double kl, double kq) {
		_color = new Color(color);
		_position = new Point3D(pos);
		_kc = kc;
		_kl = kl;
		_kq = kq;
	}

	// ***************** Operations ******************** //

	/**
	 * calculates the color and intensity of the light
	 * 
	 * @return fixed color of the Point light
	 */
	public Color getIntensity() {
		return new Color(_color);
	}

	/**
	 * calculates the color and intensity of the light at a certain point
	 * 
	 * @param point the point
	 * @return fixed color of the point light at the received point
	 */
	public Color getIntensity(Point3D point) {
		double dis = _position.distance(point);
		double denom = _kc + _kl * dis + _kq * dis * dis;
		return _color.reduce(denom);
	}

	/**
	 * The direction from the light source to a certain point
	 * 
	 * @return the direction vector
	 */
	public Vector getL(Point3D point) {
		return point.subtract(_position).normalization();
	}

	/**
	 * The direction of the light source at a certain point
	 * 
	 * @return the direction vector
	 */
	public Vector getD(Point3D point) {
		return getL(point);
	}
}