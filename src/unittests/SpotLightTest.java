package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import elements.*;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

class SpotLightTest {

	@Test
	public void SpotLightingRendering() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Material material = new Material(0.5, 0.5, 0, 0, 122);
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-20, 0, 10), 0.4, 0.001, 0.0001,
				new Vector(0.2, 0.5, 1)));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48), material));

		ImageWriter imageWriter = new ImageWriter("Spot Light Sphere Test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void SpottLight2Triangles() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(0, 0, 255), new Point3D(-50, -50, -10), 1, 0.0015, 0.000002,
				new Vector(0, 0, 1)));
		lights.add(new SpotLight(new Color(255, 255, 255), new Point3D(0, 0, -10), 1, 0.0015, 0.000002,
				new Vector(0, 0, 1)));
		lights.add(new SpotLight(new Color(0, 0, 255), new Point3D(50, 50, -10), 1, 0.0015, 0.000002,
				new Vector(0, 0, 1)));
		lights.add(new SpotLight(new Color(0, 255, 0), new Point3D(-50, 50, -10), 1, 0.0015, 0.000002,new Vector(0,1,1)));
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(50, -50, -10), 1, 0.0015, 0.000002, new Vector(0,-1,1)));
		scene.setLights(lights);
		scene.addGeometry(new Triangle(new Point3D(150, 150, 0), new Point3D(150, -150, 50), new Point3D(-150, -150, 0),
				new Color(100, 100, 100), new Material(0.5, 0.5, 0, 0, 120)));
		scene.addGeometry(new Triangle(new Point3D(150, 150, 0), new Point3D(-150, 150, -20), new Point3D(-150, -150, 0),
				new Color(10, 10, 10), new Material(0.4, 0.6, 0, 0, 120)));
		ImageWriter imageWriter = new ImageWriter("Spot Light Trinagles test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

}
