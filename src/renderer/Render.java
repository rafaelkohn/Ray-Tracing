package renderer;

import scene.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import geometries.Geometry;
import elements.*;
import primitives.*;
import static primitives.Util.*;

/**
 * Render class, renders the image of the scene
 */
public class Render {
	Scene _scene;
	ImageWriter _imageWriter;
	private static final int MAX_CALC_COLOR_LEVEL = 5;

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
	private GeoPoint getClosestPoint(Map<Geometry, List<Point3D>> intersectionPoints, Point3D p0) {
		// in the intersectionPoints - find the point with minimal distance from the ray
		// begin point and return it
		double distance = Double.MAX_VALUE;
		GeoPoint geopoint = new GeoPoint();

		for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet())
			for (Point3D point : entry.getValue()) {
				if (p0.distance(point) < distance) {
					geopoint.point = new Point3D(point);
					geopoint.geometry = entry.getKey();
					distance = p0.distance(point);
				}
			}
		return geopoint;
	}

	/**
	 * renders the image into image writer
	 * 
	 * @throws InterruptedException
	 */
	public void renderImage() throws InterruptedException {
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());
		for (int i = 0; i < _imageWriter.getNx(); i++)
			for (int j = 0; j < _imageWriter.getNy(); j++) {
				final int i2 = i;
				final int j2 = j;
				executor.execute(() -> {
					Ray ray = _scene.getCamera().constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(),
							i2, j2, _scene.getScreenDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());
					GeoPoint closestPoint = getClosestIntersection(ray);
					_imageWriter.writePixel(i2, j2, closestPoint == null ? _scene.getBackground().getColor()
							: calcColor(closestPoint, ray).getColor());
				});
			}
		executor.shutdown();
		executor.awaitTermination(120, TimeUnit.MINUTES);
	}

	/**
	 * recursive call for calcColor
	 * 
	 * @param geopoint point on geometry where we want to calculate the color
	 * @param ray      the ray that goes to the point from the camera
	 * @return the color and intensity at the point
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, 1.0);
	}

	/**
	 * recursive function that calculates the ambient, emission, difusive, specular,
	 * refraction and reflection factors of the color and adds them
	 * 
	 * @param geopoint point on geometry where we want to calculate the color
	 * @param inRay    the ray that goes to the point from the camera
	 * @param level    the level of the recursion
	 * @param k        the transparency/reflection coefficient
	 * @return the color and intensity
	 */
	private Color calcColor(GeoPoint geopoint, Ray inRay, int level, double k) {
		if (level == 0 || Util.isZero(k))
			return Color.BLACK;

		Color color = _scene.getAmbientLight().getIntensity();
		color = color.add(geopoint.geometry.getEmission());
		Vector v = inRay.get_direction(); // geopoint.point.subtract(_scene.getCamera().get_p0()).normalization();
		Vector n = geopoint.geometry.getNormal(geopoint.point);
		int nShininess = geopoint.geometry.getMaterial().getShininess();
		double kd = geopoint.geometry.getMaterial().getKd();
		double ks = geopoint.geometry.getMaterial().getKs();

		if (_scene.getLights() != null)
			for (LightSource lightSource : _scene.getLights()) {
				Vector l = lightSource.getL(geopoint.point);
				if (n.dotProduct(l) * n.dotProduct(v) > 0) {
					double o = unshaded(l, geopoint);
					if (!Util.isZero(o * k)) {
						Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(o);
						color = color.add(calcDiffusive(kd, l, n, lightIntensity),
								calcSpecular(ks, l, n, v, nShininess, lightIntensity));
					}
				}
			}

		double kg = geopoint.geometry.getMaterial().getKg();
		double kr = geopoint.geometry.getMaterial().getKr();
		if (!isZero(kr)) {
			// Recursive call for a reflected ray
			Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
			GeoPoint reflectedPoint = getClosestIntersection(reflectedRay);
			if (reflectedPoint != null)
				if (isZero(kg))
					color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, k * kr).scale(kr));
				else {
					List<Color> GlossList = new ArrayList<>();
					for (Ray ray : distributeRays(n, reflectedRay, kg)) {
						GlossList.add(calcColor(reflectedPoint, ray, level - 1, k * kr).scale(kr));
					}
					color = color.add(calcAvgColor(GlossList));
				}
		}

		double kt = geopoint.geometry.getMaterial().getKt();
		if (!isZero(kt)) {
			// Recursive call for a refracted ray
			Ray refractedRay = constructRefractedRay(n, geopoint.point, inRay);
			GeoPoint refractedPoint = getClosestIntersection(refractedRay);
			if (refractedPoint != null)
				if (isZero(kg))
					color = color.add(calcColor(refractedPoint, refractedRay, level - 1, k * kt).scale(kt));
				else {
					List<Color> MattList = new ArrayList<>();
					for (Ray ray : distributeRays(n, refractedRay, kg)) {
						MattList.add(calcColor(refractedPoint, ray, level - 1, k * kt).scale(kt));
					}
					color = color.add(calcAvgColor(MattList));
				}
		}

		return color;
	}

	/**
	 * finds the closest intersection point in the ray
	 * 
	 * @param ray
	 * @return the closest intersection point and the geometry it intersects with
	 *         (as a geoPoint)
	 */
	private GeoPoint getClosestIntersection(Ray ray) {
		Map<Geometry, List<Point3D>> map = _scene.getGeometries().findIntersections(ray);
		return map.isEmpty() ? null : getClosestPoint(map, ray.get_p0());
	}

	/**
	 * Construct Reflected Ray
	 * 
	 * @param n         normal to the geopoint
	 * @param point
	 * @param direction of the ray
	 * @return the Reflected Ray
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
		Vector v = inRay.get_direction();
		Vector r = new Vector(v.subtract(n.scale(2 * n.dotProduct(v))));
		return new Ray(movePoint(point, r, n), r);
	}

	/**
	 * Construct Refracted Ray
	 * 
	 * @param n         normal to the geopoint
	 * @param point
	 * @param direction of the ray
	 * @return the Refracted Ray
	 */
	private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
		Vector v = inRay.get_direction();
		return new Ray(movePoint(point, v, n), v);
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
		return (scalar > 0) ? lightIntensity.scale(scalar) : Color.BLACK;
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
	private double unshaded(Vector l, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Point3D geometryPoint = movePoint(geopoint.point, lightDirection, geopoint.geometry.getNormal(geopoint.point));
		Ray lightRay = new Ray(geometryPoint, lightDirection);
		Map<Geometry, List<Point3D>> intersectionPoints = _scene.getGeometries().findIntersections(lightRay);
		double shadowK = 1;
		for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet()) {
			double kt = entry.getKey().getMaterial().getKt();
			for (@SuppressWarnings("unused")
			Point3D p : entry.getValue())
				shadowK *= kt;
		}
		return shadowK;
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

	/**
	 * adds epsilon to the location of a point and moves it a bit ahead or backwards
	 * depending the direction it gets
	 * 
	 * @param p the point
	 * @param v the direction, the axis of the point's movement
	 * @param n normal to the direction
	 * @return the moved point
	 */
	private Point3D movePoint(Point3D p, Vector v, Vector n) {
		Vector epsVector = n.scale((n.dotProduct(v) > 0) ? 2 : -2);
		return p.add(epsVector);
	}

	/**
	 * calculates the average color in a list
	 * 
	 * @param list of colors
	 * @return the average color of the list
	 */
	private Color calcAvgColor(List<Color> list) {
		if (list.isEmpty() || list == null)
			return Color.BLACK;
		Color sum = new Color(0, 0, 0);
		for (Color color : list) {
			sum = sum.add(color);
		}
		return sum.reduce(list.size());
	}

	/**
	 * makes a distribution of 20 rays around the received ray (included twice). the
	 * rays are made within a cone around he ray.
	 * 
	 * @param r          received ray
	 * @param distFactor the number of the root for random number ( in the calculation of theta)
	 * @return a list of rays
	 */
	public List<Ray> distributeRays(Vector n, Ray r, double distFactor) {
		List<Ray> rays = new ArrayList<>();
		rays.add(r);
		for (int i = 0; i < 20; i++) {
			Ray testRay;
			double a = Math.random();
			double b = Math.random();
			double theta = Math.acos(Math.pow(a, 1 / (1 + distFactor))); // angle of deviation of the ray to the
																			// horizontal part of the cone
			double phi = Math.PI * b; // angle of deviation of the ray in the vertical part of the cone
			double x = Math.sin(theta) * Math.cos(phi); // Combination of the two angles in the x direction
			double y = Math.sin(phi) * Math.sin(theta); // Combination of the two angles in the y direction
			double z = Math.cos(theta); // deviation only in theta because the z direction will not change depending on
										// the phi angle
			Vector u = new Vector(r.get_direction().crossproduct(n));
			Vector v = r.get_direction().crossproduct(u);
			Vector w = u.crossproduct(v);
			Vector rR = new Vector(u.scale(x).add(v.scale(y)).add(w.scale(z)).add(r.get_direction())).normalization();
			testRay = new Ray(r.get_p0(), rR);
			rays.add(testRay);
		}
		return rays;
	}
}
