package elements;

import primitives.*;

/**
 * Spot Light class, shines in a direction but loses its intensity with the distance
 */
public class SpotLight extends PointLight {
	Vector _direction;
	
	// ***************** Constructors ********************** //

	/**
	 * Ctor , receives the properties of the light source
	 * 
	 * @param color the color of the light
	 * @param pos   the position of the source
	 * @param kc    first of the three factors of attenuation with distance
	 * @param kl    second of the three factors of attenuation with distance
	 * @param kq    third of the three factors of attenuation with distance
	 * @param dir   vector of the direction of the light
	 */
	public SpotLight(Color color, Point3D pos, double kc, double kl, double kq, Vector dir) {
		super(color, pos, kc, kl, kq);
		_direction = new Vector(dir).normalization();
	}

	// ***************** Operations ******************** //

	@Override
	public Color getIntensity(Point3D point) {

			return super.getIntensity(point).scale(_direction.dotProduct(getL(point)));
	}

	@Override
	public Vector getD(Point3D point) {
		return new Vector(_direction);
	}

}
