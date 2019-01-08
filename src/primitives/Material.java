package primitives;

public class Material {
	protected double _kd;
	protected double _ks;
	protected int nShininess;

	// ***************** Constructors ********************** //
	/**
	 * Ctor, receives all of the properties for the material
	 * 
	 * @param kd  diffusive coefficient
	 * @param ks  specular coefficient
	 * @param nSh number of shininess
	 */
	public Material(double kd, double ks, int nSh) {
		_kd = kd;
		_ks = ks;
		nShininess = nSh;
	}

	/**
	 * Copy Ctor, copies the properties of another material
	 * 
	 * @param other the copied material
	 */
	public Material(Material other) {
		this(other._kd, other._ks, other.nShininess);
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
		return _kd;
	}

	/**
	 * specular coefficient getter
	 * 
	 * @return specular coefficient of the material
	 */
	public double getKs() {
		return _ks;
	}
}
