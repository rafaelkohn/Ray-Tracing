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

class DirectionalLightTest {

	@Test
	public void DirectionalLightingRendering() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Material material = new Material(0.9,0.3,0,0,2);
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new DirectionalLight(Color.WHITE,new Vector(0,1,1)));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), /*new Color(200,150,180)*/ Color.BLACK,material));
		
		ImageWriter imageWriter = new ImageWriter("Directional Light Test",500,500,500,500);
		Render render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
	}
}
