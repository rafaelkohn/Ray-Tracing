package elements;
import primitives.Color;

/**
 * abstract class for all the types of light
 */
public abstract class Light {
	Color _color;
	// ***************** Constructors ********************** //
	// ***************** Getters/Setters ********************** //
	
	/**
	 * color getter
	 * @return the color
	 */
	public Color getColor() {
		return new Color(_color);
	}
	
	// ***************** Operations ******************** // 
	
	public abstract Color getIntensity();
}
