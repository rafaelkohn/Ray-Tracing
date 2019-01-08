package geometries;
import primitives.*;

/**
 * Abstract class for all of the geometries
 */

public abstract class Geometry implements Intersectable{
	
	Color _emission;
	Material _material;
	
	// ***************** Constructors ********************** // 
	
	/**
	 * default Ctor, sets black as the default color for the geometry
	 */
	Geometry(){
		_emission = Color.BLACK;
	}
	
	/**
	 * Ctor for the geometry, receives the color of the geometry
	 * @param color the desired color for the geometry
	 */
	Geometry(Color color){
		_emission = color;
	}
	
	/**
	 * Ctor for the geometry, receives the color and the material of the geometry
	 * @param color the desired color for the geometry
	 * @param material the desired material for the geometry
	 */
	Geometry(Color color,Material material){
		_emission = color;
		_material = material;
	}
	
	// ***************** Getters/Setters ********************** //
	
	/**
	 * Emission color getter
	 * @return the emission color
	 */
	public Color getEmission() {
		return new Color(_emission);
	}
	
	/**
	 * The material's getter
	 * @return the material
	 */
	public Material getMaterial() {
		return new Material(_material);
	}
	
	// ***************** Operations ******************** // 
	/** 
	 * Abstract method for the getNormal method
	 * @param intersection 
	 * @return the normal to the geometry at the intersection
	 */
	public abstract Vector getNormal(Point3D intersection);
}
