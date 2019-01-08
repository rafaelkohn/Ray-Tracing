package renderer;

import scene.*;
import java.util.List;
import java.util.Map;
import geometries.Geometry;
import elements.*;
import primitives.*;

/**
 * Render class, renders the image of the scene
 */
public class Render {
	Scene _scene;
	ImageWriter _imageWriter;
	private static final int MAX_CALC_COLOR_LEVEL = 10;

	private static class GeoPoint {
		public Geometry geometry;
		public Point3D point;
	}

	/********** Constructors ***********/

	/**
	 * Ctor, receives the image writer for the
	 * 
	 * @param imageWriter
	 * @param scene       the scene for the image
	 */
	public Render(ImageWriter imageWriter, Scene scene) {
		_scene = new Scene(scene);
		_imageWriter = new ImageWriter(imageWriter);
	}

	/********** Operations ***********/
	/**
	 * calculates what is the closest point to the camera out of a map with all of
	 * the intersection points and returns it
	 * 
	 * @param intersectionPoints
	 * @return the closest point to the camera
	 */
	private GeoPoint getClosestPoint(Map<Geometry, List<Point3D>> intersectionPoints) {
		// in the intersectionPoints - find the point with minimal distance from the ray
		// begin point and return it
		double distance = Double.MAX_VALUE;
		GeoPoint geopoint = new GeoPoint();
		Point3D P0 = _scene.getCamera().get_p0();

		for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet())
			for (Point3D point : entry.getValue()) {
				if (P0.distance(point) < distance) {
					geopoint.point = new Point3D(point);
					geopoint.geometry = entry.getKey();
					distance = P0.distance(point);
				}
			}
		return geopoint;
	}

	/**
	 * renders the image into image writer
	 */
	public void renderImage() {
		for (int i = 0; i < _imageWriter.getNx(); i++)
			for (int j = 0; j < _imageWriter.getNy(); j++) {
				Ray ray = _scene.getCamera().constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(), i, j,
						_scene.getScreenDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());
				Map<Geometry, List<Point3D>> intersectionPoints = _scene.getGeometries().findIntersections(ray);
				if (intersectionPoints.isEmpty())
					_imageWriter.writePixel(i, j, _scene.getBackground().getColor());
				else {
					GeoPoint closestPoint = getClosestPoint(intersectionPoints);
					_imageWriter.writePixel(i, j, calcColor(closestPoint).getColor());
				}
			}
	}

	/**
	 * calculates the right color for every given point
	 * 
	 * @param point a point that is part of a geometry
	 * @return the color and intensity of the point
	 */
	private Color calcColor(GeoPoint geopoint) {
		Color color = _scene.getAmbientLight().getIntensity();
		color = color.add(geopoint.geometry.getEmission());
		System.out.println(color.getColor());

		Vector v = geopoint.point.subtract(_scene.getCamera().get_p0()).normalization();
		Vector n = geopoint.geometry.getNormal(geopoint.point);
		int nShininess = geopoint.geometry.getMaterial().getShininess();
		double kd = geopoint.geometry.getMaterial().getKd();
		double ks = geopoint.geometry.getMaterial().getKs();
		if (_scene.getLights() != null)
			for (LightSource lightSource : _scene.getLights()) {
				Vector l = lightSource.getL(geopoint.point);
				if (n.dotProduct(l) * n.dotProduct(v) > 0) {
					if (!occluded(l, geopoint)) {
						Color lightIntensity = lightSource.getIntensity(geopoint.point);
						color = color.add(calcDiffusive(kd, l, n, lightIntensity),
								calcSpecular(ks, l, n, v, nShininess, lightIntensity));
					}
				}
			}
		System.out.println(color.getColor() + "\n");
		return color;
	}

	/**
	 * calculates the specular color for a material in a certain point
	 * 
	 * @param ks             specular coefficient
	 * @param l              direction from light source to point
	 * @param n              normal to point
	 * @param v              direction from camera to point
	 * @param nShininess
	 * @param lightIntensity
	 * @return the specular color
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		double length = 2 * l.dotProduct(n);
		Vector r = l.subtract(n.scale(length)).normalization();
		double scalar = ks * Math.pow(r.dotProduct(v.scale(-1)), nShininess);
		return lightIntensity.scale(scalar);
	}

	/**
	 * calculates the diffusive color for a material in a certain point
	 * 
	 * @param kd             diffusive coefficient
	 * @param l              direction from light source to point
	 * @param n              normal to point
	 * @param lightIntensity
	 * @return the diffusive color
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		double scalar = Math.abs(l.dotProduct(n)) * kd;
		return lightIntensity.scale(scalar);
	}

	/**
	 * checks if the received point is occluded by another geometry
	 * 
	 * @param l        the direction opposite to the light
	 * @param geopoint the geopoint ergo, the point and the geometry it is on
	 * @return true if the point is occluded
	 */
	private boolean occluded(Vector l, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector normal = geopoint.geometry.getNormal(geopoint.point);
		Vector epsVector = normal.scale((normal.dotProduct(lightDirection) > 0) ? 2 : -2);
		Point3D geometryPoint = geopoint.point.add(epsVector);
		Ray lightRay = new Ray(geometryPoint, lightDirection);
		Map<Geometry, List<Point3D>> intersectionPoints = _scene.getGeometries().findIntersections(lightRay);
		return !intersectionPoints.isEmpty();
	}

	/**
	 * prints a grid
	 * 
	 * @param interval the size of each square in the grid
	 */
	public void printGrid(int interval) {
		for (int i = 0; i < _imageWriter.getNx(); i += interval)
			for (int j = 0; j < _imageWriter.getNy(); j++) {
				_imageWriter.writePixel(i, j, 255, 255, 255);
				_imageWriter.writePixel(j, i, 255, 255, 255);
			}
	}

	/**
	 * makes a jpg of the image and takes it to the workspace folder
	 */
	public void writeToImage() {
		_imageWriter.writeToimage();
	}

}
