package elements;

import primitives.*;
import static primitives.Util.*;

/**
 * Camera class. Contains the location of the camera and the relative direction
 */
public class Camera {
	Point3D _p0;
	Vector _vTo;
	Vector _vRight;
	Vector _vUp;

	// ***************** Constructors ********************** //
	/**
	 * Ctor , receives the following parameters for the camera
	 * 
	 * @param _p0  location
	 * @param _vTo Forward direction
	 * @param _vUp Up direction
	 */
	public Camera(Point3D _location, Vector _Vto, Vector _Vup) {
		_p0 = new Point3D(_location);
		_vUp = new Vector(_Vup).normalization();
		_vTo = new Vector(_Vto).normalization();
		if (!isZero(_vUp.dotProduct(_vTo)))
			throw new IllegalArgumentException("vector up and to are not orthogonal");
		_vRight = _vTo.crossproduct(_vUp).normalization();
	}

	/**
	 * Ctor, copies
	 */
	public Camera(Camera camera) {
		this(camera._p0, camera._vTo, camera._vUp);
	}

	// ***************** Getters/Setters ********************** //
	/**
	 * location of the camera point getter
	 * 
	 * @return location
	 */
	public Point3D get_p0() {
		return _p0;
	}

	/**
	 * Camera forward direction vector getter
	 * 
	 * @return _vTo
	 */
	public Vector get_vTo() {
		return _vTo;
	}

	/**
	 * Camera right direction vector getter
	 * 
	 * @return _vRight
	 */
	public Vector get_vRight() {
		return _vRight;
	}

	/**
	 * Camera upwards direction vector getter
	 * 
	 * @return upwards vector
	 */
	public Vector get_vUp() {
		return _vUp;
	}

	// ***************** Operations ******************** //
	/**
	 * constructs a ray that starts from the camera and continues through a
	 * specified pixel
	 * 
	 * @param widthPixels    number of pixels width
	 * @param heightPixels   number of pixels height
	 * @param i              width pixel
	 * @param j              height pixel
	 * @param screenDistance distance to the screen
	 * @param screenWidth    width of the screen
	 * @param screenHeight   height of the screen
	 * @return The ray from the camera to point (through pixel)
	 */
	public Ray constructRayThroughPixel(double widthPixels, double heightPixels, int i, int j, double screenDistance,
			double screenWidth, double screenHeight) {
		Point3D viewPlaneCenter;
		Vector Vij;
		Point3D pixelCenter;
		viewPlaneCenter = _p0.add(_vTo.scale(screenDistance));
		double Rx = screenWidth / widthPixels;
		double Ry = screenHeight / heightPixels;
		double Xi = (i - widthPixels / 2) * Rx - Rx / 2;
		double Yj = (j - heightPixels / 2) * Ry - Ry / 2;

		pixelCenter = viewPlaneCenter;
		if (Xi != 0)
			pixelCenter = pixelCenter.add(_vRight.scale(Xi));
		if (Yj != 0)
			pixelCenter = pixelCenter.add(_vUp.scale(-Yj));
		Vij = pixelCenter.subtract(_p0);
		return new Ray(_p0, Vij);
	}
}
