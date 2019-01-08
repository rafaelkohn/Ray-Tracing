package elements;
import primitives.*;

/**
 * represents the color of the ambient light using color and lighting constant
 */
public class AmbientLight extends Light{
	double Ka;
	/********** Constructors ***********/
	/**
	 * Ctor, receives the color and the lighting constant
	 */
	public AmbientLight(Color color ,double ka) {
		_color = new Color(color);
		Ka = ka;
	}
	
	/**
	 * copy Ctor, copies the received object into this one
	 * @param ambientLight
	 */
	public AmbientLight(AmbientLight ambientLight) {
		this(ambientLight._color, ambientLight.Ka);
	}
	
	/********** Operations ***********/
	/**
	 * calculates the color and intensity of the light
	 * @return fixed color of the ambient light
	 */
	public Color getIntensity() {
		return _color.scale(Ka);
	}
}
