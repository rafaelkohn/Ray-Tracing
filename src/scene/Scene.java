package scene;

import java.util.List;
import elements.*;
import primitives.*;
import geometries.*;

/**
 * Scene class, contains the camera, the light, name of the scene, background
 * color, distance of the camera to the screen and a list of all of the
 * geometries in the scene
 */
public class Scene {
	Camera _camera;
	AmbientLight _ambientLight;
	String _sceneName;
	Color _background;
	List<LightSource> _lights;
	double _screenDistance;
	Geometries _geometries;

	// ***************** Constructors ********************** //


	/**
	 * Ctor, receives a string to be the name of the scene
	 * 
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		_sceneName = name;
		_geometries = new Geometries();
	}

	/**
	 * Ctor, receives all of the variables for this scene
	 * 
	 * @param camera
	 * @param ambientLight
	 * @param sceneName
	 * @param color
	 * @param screenDistance distance of the camera to the scene
	 * @param geometries     list of geometries
	 */
	public Scene(Camera camera, AmbientLight ambientLight, String sceneName, Color color, double screenDistance,
			List<LightSource> lights, Geometries geometries) {
		_camera = new Camera(camera);
		_ambientLight = new AmbientLight(ambientLight);
		_sceneName = sceneName;
		_background = new Color(color);
		_screenDistance = screenDistance;
		_lights = lights;
		_geometries = new Geometries(geometries);
	}

	/**
	 * copy Ctor, copies the received scene into this one
	 * 
	 * @param scene the copied scene
	 */
	public Scene(Scene scene) {
		this(scene._camera, scene._ambientLight, scene._sceneName, scene._background, scene._screenDistance,
				scene._lights, scene._geometries);
	}

	// ***************** Getters/Setters ********************** //

	public void setDistance(double screenDistance) {
		_screenDistance = screenDistance;
	}

	public void setBackground(Color background) {
		_background = new Color(background);
	}

	public void setGeomtries(Geometries geometries) {
		_geometries = geometries;
	}

	public void setCamera(Camera camera) {
		_camera = new Camera(camera);
	}

	public void setAmbientLight(AmbientLight ambientLight) {
		_ambientLight = new AmbientLight(ambientLight);
	}
	
	public void setLights(List<LightSource> list) {
		_lights = list;
	}

	public String getName() {
		return _sceneName;
	}
	
	public Camera getCamera() {
		return _camera;
	}

	public Geometries getGeometries() {
		return _geometries;
	}

	public Color getBackground() {
		return _background;
	}

	public double getScreenDistance() {
		return _screenDistance;
	}
	
	public AmbientLight getAmbientLight() {
		return _ambientLight;
	}

	public List<LightSource> getLights() {
		return _lights;
	}

	// ***************** Operations ******************** //
	/**
	 * receives a geometry to add in the list of the geometries in our scene
	 * 
	 * @param geometry the geometry you want to add
	 */
	public void addGeometry(Geometry geometry) {
		_geometries.add(geometry);
	}
}
