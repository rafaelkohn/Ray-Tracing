package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import elements.*;
import geometries.Geometries;
import geometries.Sphere;
import primitives.*;
import renderer.*;
import scene.Scene;

class SpotLightTest {

	@Test
	public void SpotLightingRendering() {
		Scene scene = new Scene("Test scene");
		Material material = new Material(0.9,0.3,2);
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-20,0,10), 0.4, 0.001, 0.0001, new Vector(0.2,0.5,1)));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48),material));
		
		ImageWriter imageWriter = new ImageWriter("Spot Light Test",500,500,500,500);
		Render render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
	}

}
