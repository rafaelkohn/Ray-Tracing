package primitives;

/**
 * Implements the material that the geometry is made of and all of it optical
 * properties
 *
 */
public class Material {
	protected double _Kd;
	protected double _Ks;
	protected double _Kr;
	protected double _Kt;
	protected double _Kg;
	protected int nShininess;

	// ***************** Constructors ********************** //

	/**
	 * Ctor, receives all of the properties for the material
	 * 
	 * @param kd  diffusive coefficient
	 * @param ks  specular coefficient
	 * @param kr  reflection coefficient
	 * @param kt  transparency coefficient
	 * @param kg  glossyness coefficient
	 * @param nSh number of shininess
	 */
	public Material(double kd, double ks, double kr, double kt, double kg, int nSh) {
		_Kd = kd;
		_Ks = ks;
		_Kr = kr;
		_Kt = kt;
		_Kg = kg;
		nShininess = nSh;
	}

	/**
	 * Ctor, receives all of the properties for the material
	 * 
	 * @param kd  diffusive coefficient
	 * @param ks  specular coefficient
	 * @param kr  reflection coefficient
	 * @param kt  transparency coefficient
	 * @param nSh number of shininess
	 */
	public Material(double kd, double ks, double kr, double kt, int nSh) {
		_Kd = kd;
		_Ks = ks;
		_Kr = kr;
		_Kt = kt;
		_Kg = 0;
		nShininess = nSh;
	}

	/**
	 * Copy Ctor, copies the properties of another material
	 * 
	 * @param other the copied material
	 */
	public Material(Material other) {
		this(other._Kd, other._Ks, other._Kr, other._Kt, other._Kg, other.nShininess);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Shininess getter
	 * 
	 * @return Shininess of the material
	 */
	public int getShininess() {
		return nShininess;
	}

	/**
	 * diffusive coefficient getter
	 * 
	 * @return diffusive coefficient
	 */
	public double getKd() {
		return _Kd;
	}

	/**
	 * specular coefficient getter
	 * 
	 * @return specular coefficient of the material
	 */
	public double getKs() {
		return _Ks;
	}

	/**
	 * reflective coefficient getter
	 * 
	 * @return reflective coefficient
	 */
	public double getKr() {
		return _Kr;
	}

	/**
	 * Transparency coefficient getter
	 * 
	 * @return Transparency coefficient of the material
	 */
	public double getKt() {
		return _Kt;
	}

	/**
	 * glossyness coefficient getter
	 * 
	 * @return glossyness coefficient of the material
	 */
	public double getKg() {
		return _Kg;
	}
}
