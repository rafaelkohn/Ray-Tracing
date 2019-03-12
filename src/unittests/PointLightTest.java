package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import elements.*;
import geometries.*;
import geometries.Sphere;
import primitives.*;
import renderer.*;
import scene.Scene;

class PointLightTest {

	@Test
	public void PointLightingRendering() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Material material = new Material(0.6, 0.4, 0, 0, 120);
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new PointLight(Color.WHITE, new Point3D(-30.7, -5, 29), 0.3, 0.001, 0.00001));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), /* new Color(200,150,180) */ Color.BLACK, material));

		ImageWriter imageWriter = new ImageWriter("Point Light Sphere test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void PointLight2Triangles() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Material material = new Material(0.5, 0.5, 0, 0, 120);
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new PointLight(new Color(0,0,255), new Point3D(-75, -75, -20), 1, 0.005, 0.0000025));
		lights.add(new PointLight(new Color(255,255,255), new Point3D(0, 0, -20), 1, 0.005, 0.0000025));
		lights.add(new PointLight(new Color(0,0,255), new Point3D(75, 75, -20), 1, 0.005, 0.0000025));
		lights.add(new PointLight(new Color(0,255,0), new Point3D(-75, 75, -20), 1, 0.005, 0.0000025));
		lights.add(new PointLight(new Color(255,0,0), new Point3D(75, -75, -20), 1, 0.005, 0.0000025));
		scene.setLights(lights);
		scene.addGeometry(new Triangle(new Point3D(150,150,0),new Point3D(150,-150,-20),new Point3D(-150,-150,0),new Color(0,150,0),material));
		scene.addGeometry(new Triangle(new Point3D(150,150,0),new Point3D(-150,150,50),new Point3D(-150,-150,0),new Color(150,0,0),material));
		ImageWriter imageWriter = new ImageWriter("Point Light Trinagles test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

}
