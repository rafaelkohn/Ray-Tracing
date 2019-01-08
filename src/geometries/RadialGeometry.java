package geometries;

import primitives.Color;
import primitives.Material;

/**
 * A class for all of the radial geometries such as a sphere or a tube
 */

public abstract class RadialGeometry extends Geometry {
	double _radius;

	// ***************** Constructors ********************** //
	/**
	 * Ctor, receives the raidus for the geometry
	 * 
	 * @param radius
	 * @param em       emission color
	 * @param material the material of the geometry
	 */
	RadialGeometry(double radius, Color em, Material material) {
		super(em, material);
		this._radius = radius;
	}

	// ***************** Getters/Setters ********************** //
	public double getRadius() {
		return _radius;
	}
}
