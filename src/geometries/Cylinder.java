package geometries;
import primitives.*;

/**
 * Cylinder class.
 * tube with finite height (and bases)
 */

public class Cylinder extends Tube{
	double _height;
	// ***************** Constructors ********************** // 
	
	/**
	 * Ctor , receives the radius, axis point and direction for the tube
	 * @param radius
	 * @param axisPt the axis point
	 * @param axisDir the direction
	 * @param height
	 */
	public Cylinder(double radius , Point3D axisPt , Ray axisDir,Color em, Material material,double height){
		super(radius , axisPt , axisDir,em,material);
		_height = height;
	}
	// ***************** Getters/Setters ********************** //
	public double get_height() {
		return _height;
	}
	// ***************** Operations ******************** // 
	
	/**
	 * returns the vector of the normal to the sphere in a certain point
	 */
	@Override
	public Vector getNormal(Point3D intersection) {
		Point3D pt = this._axisDirection.get_p0().add(this._axisDirection.get_direction().scale(_height));
		if(intersection.subtract(this._axisDirection.get_p0()).dotProduct(this._axisDirection.get_direction())==0)
			return this._axisDirection.get_direction();
		if(intersection.subtract(pt).dotProduct(this._axisDirection.get_direction())==0)
			return this._axisDirection.get_direction().scale(-1);
		return super.getNormal(intersection);
	}
}