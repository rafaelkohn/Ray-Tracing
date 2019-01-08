package primitives;

/**
 * Color class 
 */
public class Color {
	
	 double _r;
	 double _g;
	 double _b;

	public static Color WHITE = new Color(255,255,255);
	public static Color BLACK = new Color(0,0,0);
	
	// ***************** Constructors ********************** //
	/**
	 * default Ctor, assigns the color black
	 */
	public Color() {
		_r = 0;
		_g = 0;
		_b = 0;
	}
	
	/**
	 * Ctor , receives three integers for the colors
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public Color(int r, int g, int b) {
		if (r < 0 || g < 0 || b < 0)
			throw new IllegalArgumentException(" the colors cannot be negative");
		_r=r;
		_g =g;
		_b=b;
	}

	/**
	 * Ctor , receives three floating points for the colors
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public Color(double r, double g, double b) {
		if (r < 0 || g < 0 || b < 0)
		{
			System.out.println(r+""+g+""+b);
			throw new IllegalArgumentException(" the colors cannot be negative");
		}
		_r=r;
		_g=g;
		_b=b;
	}

	/**
	 * copy Ctor, copies the new color into this one
	 * @param color
	 */
	public Color(Color color) {
		this(color._r,color._g,color._b);
	}

	/**
	 * Ctor, copies the new java.awt color into this one
	 * @param color
	 */
	public Color(java.awt.Color color) {
		this(color.getRed(),color.getGreen(),color.getBlue());
	}
	// ***************** Getters/Setters ********************** //

	/**
	 * color setter that receives three numbers
	 * @param r red number
	 * @param g green number
	 * @param b blue number
	 * @return the new color
	 */
	public Color setColor(double r, double g, double b) {
		_r = r;
		_g = g;
		_b = b;
		return new Color(r,g,b);
	}

	/**
	 * color setter that receives three integers
	 * @param r red integer
	 * @param g green integer
	 * @param b blue integer
	 * @return the new color
	 */
	public Color setColor(int r, int g, int b) {
		_r =r;
		_g = g;
		_b = b;
		return new Color(r,g,b);
	}

	/**
	 * copy setter, deep copy of another color
	 * @param color the copied color
	 * @return the new color
	 */
	public Color setColor(Color color) {
		_r = color._r;
		_g = color._g;
		_b = color._b;
		return new Color(color);
	}

	/**
	 * color setter that receives a java color
	 * @param color the color
	 * @return the new color
	 */
	public Color setColor(java.awt.Color color) {
		_r = color.getRed();
		_g = color.getGreen();
		_b = color.getBlue();
		return new Color(color);
	}

	/**
	 * color getter
	 * @return our color as java color
	 */
	public java.awt.Color getColor() {
		double r = _r > 255 ? 255 : _r;
		double g = _g > 255 ? 255 : _g;
		double b = _b > 255 ? 255 : _b;
		return new java.awt.Color((int) r,(int) g,(int) b);
	}

	// ***************** Operations ******************** //
	/**
	 * adds a new color to this one
	 * @param the added color
	 * @return the new color
	 */
	public Color add(Color color) {
		double r = _r + color._r;
		double g = _g + color._g;
		double b = _b + color._b;
		if(r>255)
			r=255;
		if(g>255)
			g=255;
		if(b>255)
			b=255;
		return new Color(r, g, b);
	}
	
	/**
	 * receives to colors and adds them one to another
	 * @param color1 the first color, as base
	 * @param color2 the second color added to the first
	 * @return the new color
	 */
	public Color add(Color color1 , Color color2) {
		return add(color1.add(color2));
	}

	/**
	 * scales the color by a received factor
	 * @param scalar the factor
	 * @return the new color
	 */
	public Color scale(double scalar) {
		if(scalar == 0)
			return Color.BLACK;
		double r = _r * scalar;
		double g = _g * scalar;
		double b = _b * scalar;
		if(r>255)
			r=255;
		if(g>255)
			g=255;
		if(b>255)
			b=255;
		return new Color(r, g, b);
	}

	/**
	 * reduces from your color (every of the rgb) the received number
	 * @param scalar
	 * @return the new color
	 */
	public Color reduce(double scalar) {
		return scale(1/scalar);
	}
}
