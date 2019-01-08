package elements;

import primitives.*;

/**
 * Directional light, only has direction
 */
public class DirectionalLight extends Light implements LightSource {
	Vector _direction;
	// ***************** Constructors ********************** //

	/**
	 * Ctor, assigns the direction and color of the light
	 * 
	 * @param color the color of the light
	 * @param dir   the direction of the light
	 */
	public DirectionalLight(Color color, Vector dir) {
		_color = new Color(color);
		_direction = new Vector(dir).normalization();
	}

	// ***************** Operations ******************** //

	/**
	 * calculates the color and intensity of the light
	 * 
	 * @return fixed color of the directional light
	 */
	public Color getIntensity() {
		return new Color(_color);
	}

	/**
	 * calculates the color and intensity of the light at a certain point
	 * 
	 * @param point the point
	 * @return fixed color of the directional light at the received point
	 */
	public Color getIntensity(Point3D point) {
		return _color;
	}

	/**
	 * The direction from the light source to a certain point
	 * 
	 * @return the direction vector
	 */
	public Vector getL(Point3D point) {
		return new Vector(_direction);
	}

	/**
	 * The direction of the light source at a certain point
	 * 
	 * @return the direction vector
	 */
	public Vector getD(Point3D point) {
		return new Vector(_direction);
	}
}
