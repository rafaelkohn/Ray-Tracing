package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import elements.*;
import renderer.*;
import scene.Scene;

public class RenderTest {
	@Test
	public void basicRendering(){
		Scene scene = new Scene("Test scene");
		Material material = new Material(0.9,0.3,2);
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.7));
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(50, new Point3D(0, 0, 150), scene.getAmbientLight().getColor(),material));
		
		geometries.add(new Triangle(new Point3D( 100, 0, 150),
				 							new Point3D(  0, 100, 150),
				 							new Point3D( 100, 100, 150), new Color(java.awt.Color.GREEN),material));
		
		geometries.add(new Triangle(new Point3D( 100, 0, 150),
				 			 				new Point3D(  0, -100, 150),
				 			 				new Point3D( 100,-100, 150),scene.getAmbientLight().getColor(),material));
		
		geometries.add(new Triangle(new Point3D(-100, 0, 150),
				 							new Point3D(  0, 100, 150),
				 							new Point3D(-100, 100, 150),new Color(java.awt.Color.YELLOW),material));
		
		geometries.add(new Triangle(new Point3D(-100, 0, 150),
				 			 				new Point3D(  0,  -100, 150),
				 			 				new Point3D(-100, -100, 150),new Color(java.awt.Color.PINK),material));
		ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.printGrid(50);
		render.writeToImage();
	}
	
	@Test
	public void ShadowTests() {
		Scene scene = new Scene("Test scene 1");
		Material material = new Material(0.9,0.3,12);
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-50,10,10), 0.4, 0.001, 0.0001, new Vector(0.5,0.5,1)));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48),material));
		geometries.add(new Triangle(new Point3D(-34,20,30),new Point3D(-50,4,30),new Point3D(-50,20,30),new Color(0,0,200),material));
		ImageWriter imageWriter = new ImageWriter("shadow test 1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
		//second moving the triangle
		scene = new Scene("Test scene 2");
		geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48),material));
		geometries.add(new Triangle(new Point3D(-30,44,25),new Point3D(-54,20,25),new Point3D(-54,44,25),new Color(0,0,200),material));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		lights.clear();
		lights.add(new PointLight(new Color(177, 134, 206), new Point3D(-80,50,-40), 0.4, 0.001, 0.00001));
		scene.setLights(lights);
		
		imageWriter = new ImageWriter("shadow test 2", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
		
		//first moving the light
		scene = new Scene("Test scene 3");
		geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48),material));
		geometries.add(new Triangle(new Point3D(-26,48,35),new Point3D(-50,24,35),new Point3D(-50,48,36),new Color(0,0,200),material));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		lights.clear();
		lights.add(new PointLight(new Color(177, 134, 206), new Point3D(-65,50,10), 0.4, 0.001, 0.00001));
		scene.setLights(lights);
		
		imageWriter = new ImageWriter("shadow test 3", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
		
		//ball on rectangle
		scene = new Scene("Test scene 6");
		geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48),material));
		geometries.add(new Rectangle(new Point3D(-200,100,300),new Point3D(-200,100,30),new Point3D(200,100,30),new Color(0,0,200),material));
		scene.setCamera(new Camera(new Point3D(0, -300, 150), new Vector(0, 1, 0), new Vector(0, 0, 1)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		lights.clear();
		lights.add(new PointLight(new Color(177, 134, 206), new Point3D(65,-300,50), 0.4, 0.001, 0.00001));
		scene.setLights(lights);
		
		imageWriter = new ImageWriter("shadow test rec", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);
		
		render.renderImage();
		render.writeToImage();
	}
}