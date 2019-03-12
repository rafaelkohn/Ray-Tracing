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
	public void basicRendering() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Material material = new Material(0.9, 0.3, 0, 0, 2);
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.7));
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(50, new Point3D(0, 0, 150), scene.getAmbientLight().getColor(), material));

		geometries.add(new Triangle(new Point3D(100, 0, 150), new Point3D(0, 100, 150), new Point3D(100, 100, 150),
				new Color(java.awt.Color.GREEN), material));

		geometries.add(new Triangle(new Point3D(100, 0, 150), new Point3D(0, -100, 150), new Point3D(100, -100, 150),
				scene.getAmbientLight().getColor(), material));

		geometries.add(new Triangle(new Point3D(-100, 0, 150), new Point3D(0, 100, 150), new Point3D(-100, 100, 150),
				new Color(java.awt.Color.YELLOW), material));

		geometries.add(new Triangle(new Point3D(-100, 0, 150), new Point3D(0, -100, 150), new Point3D(-100, -100, 150),
				new Color(java.awt.Color.PINK), material));
		ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		render.writeToImage();
	}

	@Test
	public void lightsTest() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Material material = new Material(0.7, 0.3, 0, 0, 120);
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(177, 134, 255), new Point3D(-150, 0, 150), 1, 0.002, 0.0000004,
				new Vector(1, 0, 0)));
		lights.add(new DirectionalLight(new Color(80, 0, 0), new Vector(-0.5, -0.5, 0)));
		lights.add(new PointLight(new Color(0, 150, 150), new Point3D(0, 0, 0), 1, 0.002, 0.0000004));
		scene.setLights(lights);
		scene.addGeometry(new Sphere(100, new Point3D(0, 0, 150), new Color(60, 60, 60), material));
		ImageWriter imageWriter = new ImageWriter("Sphere and multiple lights", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void shadowTests() throws InterruptedException {
		Scene scene = new Scene("Test scene 1");
		Material material = new Material(0.7, 0.3, 0, 0, 12);
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-50, 10, 10), 1, 0.001, 0.0001,
				new Vector(0.5, 0.5, 1)));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48), material));
		geometries.add(new Triangle(new Point3D(-34, 20, 30), new Point3D(-50, 4, 30), new Point3D(-50, 20, 30),
				new Color(0, 0, 200), material));
		ImageWriter imageWriter = new ImageWriter("shadow test 1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();

		// second moving the triangle
		scene = new Scene("Test scene 2");
		geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48), material));
		geometries.add(new Triangle(new Point3D(-30, 44, 25), new Point3D(-54, 20, 25), new Point3D(-54, 44, 25),
				new Color(0, 0, 200), material));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		lights.clear();
		lights.add(new PointLight(new Color(177, 134, 206), new Point3D(-80, 50, -40), 1, 0.001, 0.00001));
		scene.setLights(lights);

		imageWriter = new ImageWriter("shadow test 2", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();

		// first moving the light
		scene = new Scene("Test scene 3");
		geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48), material));
		geometries.add(new Triangle(new Point3D(-34, 20, 30), new Point3D(-50, 4, 30), new Point3D(-50, 20, 30),
				new Color(0, 0, 200), material));
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		lights.clear();
		lights.add(new PointLight(new Color(177, 134, 206), new Point3D(-65, 50, 10), 1, 0.004, 0.00001));
		scene.setLights(lights);

		imageWriter = new ImageWriter("shadow test 3", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();

		// ball on rectangle
		scene = new Scene("Test scene 6");
		geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(17, 11, 48), material));
		geometries.add(new Rectangle(new Point3D(-200, 100, 300), new Point3D(-200, 100, 30), new Point3D(200, 100, 30),
				new Color(0, 0, 200), material));
		scene.setCamera(new Camera(new Point3D(0, -5000, 150), new Vector(0, 1, 0), new Vector(0, 0, 1)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		lights.clear();
		lights.add(new PointLight(new Color(177, 134, 206), new Point3D(65, -300, 50), 1, 0.0004, 0.000001));
		scene.setLights(lights);

		imageWriter = new ImageWriter("shadow test rec", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();

		// rectangle and many spheres
		scene = new Scene("Test scene 7");
		geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(34, 22, 96), material));
		geometries.add(new Rectangle(new Point3D(-250, 250, 260), new Vector(0, -500, 0), new Vector(500, 0, 0),
				new Color(66, 124, 127), material));
		geometries.add(new Sphere(30, new Point3D(200, 200, 150), new Color(214, 211, 66), material));
		geometries.add(new Sphere(50, new Point3D(100, 0, 100), new Color(145, 123, 91), material));
		geometries.add(new Sphere(5, new Point3D(-58.5, 45, 51), new Color(145, 145, 145), material));
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		lights.clear();
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-65, 50, 40), 1, 0.001, 0.00001,
				new Vector(65, -50, 110)));
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(250, 250, 100), 1, 0.00036, 0.000000013,
				new Vector(-1, -1, 1)));
		lights.add(new PointLight(new Color(81, 31, 0), new Point3D(0, -150, 150), 1, 0.0001, 0.0000001));
		scene.setLights(lights);

		imageWriter = new ImageWriter("shadow test rec & spheres", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void transparencyTest() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(
				new SpotLight(Color.WHITE, new Point3D(-100, 80, -500), 1, 0.0001, 0.000001, new Vector(1, -0.5, 10)));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();
		geometries.add(
				new Sphere(100, new Point3D(0, 0, 150), new Color(0, 0, 100), new Material(0.3, 0.5, 0, 0.5, 100)));
		geometries.add(
				new Sphere(50, new Point3D(0, 0, 150), new Color(200, 0, 0), new Material(0.2, 0.3, 0.3, 0.2, 100)));
		ImageWriter imageWriter = new ImageWriter("transparency test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void reflectionTest() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -450), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(450);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		scene.addGeometry(new Sphere(300, new Point3D(-500, 500, 900), new Color(0, 0, 150),
				new Material(0.3, 0.2, 0.1, 0.3, 100)));
		scene.addGeometry(
				new Sphere(150, new Point3D(-500, 500, 900), new Color(250, 0, 0), new Material(0.3, 0.2, 0, 0, 100)));
		scene.addGeometry(new Triangle(new Point3D(-1400, 1400, 1400), new Point3D(-1400, -1400, 1400),
				new Point3D(1400, 1400, 1400), new Color(60, 60, 60), new Material(0.3, 0.2, 1, 0, 100)));
		scene.addGeometry(new Triangle(new Point3D(250, -250, 350), new Point3D(-1400, -1400, 1400),
				new Point3D(1400, 1400, 1400), new Color(60, 60, 60), new Material(0.3, 0.2, 1, 0, 100)));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(200, -200, 150), 1, 0.00001, 0.000001,
				new Vector(-1, 1, 2)));
		scene.setLights(lights);
		ImageWriter imageWriter = new ImageWriter("reflection test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void bigImageTest() throws InterruptedException {
		Scene scene = new Scene("Test scene");
		Material material = new Material(0.7, 0.3, 0, 0, 12);
		List<LightSource> lights = new ArrayList<>();
		scene.addGeometry(new Sphere(100, new Point3D(0, 0, 150), new Color(34, 22, 96), material));

		scene.addGeometry(new Rectangle(new Point3D(-250, 250, 260), new Vector(0, -500, 0), new Vector(500, 0, 0),
				new Color(66, 124, 127), material));

		scene.addGeometry(new Sphere(30, new Point3D(200, 200, 150), new Color(214, 211, 66), material));

		scene.addGeometry(new Sphere(50, new Point3D(100, 0, 100), new Color(145, 123, 91), material));

		scene.addGeometry(new Sphere(5, new Point3D(-58.5, 45, 51), new Color(145, 145, 145), material));

		scene.addGeometry(new Triangle(new Point3D(50, -250, 260), new Point3D(250, -50, 260),
				new Point3D(150, -150, 0), new Color(40, 40, 40), new Material(0, 0, 1, 0, 100)));

		scene.addGeometry(new Triangle(new Point3D(-50, 250, 260), new Point3D(-250, 50, 260),
				new Point3D(-150, 150, 0), new Color(40, 40, 40), new Material(0, 0, 1, 0, 100)));

		scene.addGeometry(new Triangle(new Point3D(100, -250, 250), new Point3D(250, -100, 250),
				new Point3D(250, -250, 250), Color.BLACK, new Material(0, 0, 0.5, 0.5, 100)));

		scene.addGeometry(new Triangle(new Point3D(-100, 250, 250), new Point3D(-250, 100, 250),
				new Point3D(-250, 250, 250), Color.BLACK, new Material(0, 0, 0.5, 0.5, 100)));

		scene.addGeometry(new Sphere(30, new Point3D(-200, 200, 150), new Color(0, 0, 150),
				new Material(0.3, 0.2, 0.1, 0.3, 100)));

		scene.addGeometry(
				new Sphere(15, new Point3D(-200, 200, 150), new Color(250, 0, 0), new Material(0.3, 0.2, 0, 0, 100)));

		scene.addGeometry(new Sphere(30, new Point3D(200, -200, 150), new Color(150, 0, 0),
				new Material(0.3, 0.2, 0.1, 0.3, 100)));

		scene.addGeometry(
				new Sphere(15, new Point3D(200, -200, 150), new Color(0, 0, 250), new Material(0.3, 0.2, 0, 0, 100)));

		scene.addGeometry(new Triangle(new Point3D(-250, 50, 260), new Point3D(50, -250, 260),
				new Point3D(150, -150, -50), Color.BLACK, new Material(0, 0, 0.5, 0.5, 100)));

		scene.addGeometry(new Triangle(new Point3D(-250, 50, 260), new Point3D(150, -150, -50),
				new Point3D(-150, 150, -50), Color.BLACK, new Material(0, 0, 0.5, 0.5, 100)));

		scene.addGeometry(
				new Tube(1, new Point3D(150, -150, -50), new Ray(new Point3D(150, -150, -50), new Vector(-1, 1, 0)),
						new Color(100, 100, 100), new Material(0.3, 0.2, 0.3, 0.2, 100)));

		scene.addGeometry(new Sphere(5, new Point3D(-250, -250, 150), new Color(0, 0, 150),
				new Material(0.3, 0.2, 0.1, 0.3, 100)));

		scene.addGeometry(new Sphere(7.5, new Point3D(-240, -240, 140), new Color(200, 200, 200),
				new Material(0.2, 0.2, 0, 0.6, 100)));
		lights.add(new PointLight(new Color(255, 255, 255), new Point3D(-240, -240, 140), 1, 0.002, 0.0000008));

		scene.addGeometry(new Sphere(5, new Point3D(-230, -230, 130), new Color(0, 0, 150),
				new Material(0.2, 0.2, 0.1, 0.3, 100)));

		scene.addGeometry(new Sphere(7.5, new Point3D(-220, -220, 120), new Color(200, 200, 200),
				new Material(0.3, 0.2, 0, 0.6, 100)));
		lights.add(new PointLight(new Color(255, 255, 255), new Point3D(-220, -220, 120), 1, 0.002, 0.0000008));

		scene.addGeometry(new Sphere(5, new Point3D(-210, -210, 110), new Color(0, 0, 150),
				new Material(0.3, 0.2, 0.1, 0.3, 100)));

		scene.addGeometry(new Rectangle(new Point3D(-210, -210, 160), new Vector(-40, -40, -40), new Vector(30, -30, 0),
				new Color(50, 50, 50), new Material(0.3, 0.2, 0.3, 0, 100)));

		scene.addGeometry(new Rectangle(new Point3D(-250, -300, 260), new Vector(200, 0, 0), new Vector(0, 0, -200),
				new Color(30, 30, 30), new Material(0.1, 0.2, 0.6, 0, 100)));

		scene.addGeometry(new Rectangle(new Point3D(-250, 300, 260), new Vector(200, 0, 0), new Vector(0, 0, -200),
				new Color(30, 30, 30), new Material(0.1, 0.2, 0.6, 0, 100)));

		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-65, 50, 40), 1, 0.001, 0.00001,
				new Vector(65, -50, 110)));
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(250, 250, 100), 1, 0.00036, 0.000000013,
				new Vector(-1, -1, 1)));
		lights.add(new PointLight(new Color(81, 31, 0), new Point3D(0, -150, 150), 1, 0.0001, 0.0000001));
		scene.setLights(lights);

		ImageWriter imageWriter = new ImageWriter("big test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void Planet1() throws InterruptedException {
		Scene scene = new Scene("Planet1");
		Material material = new Material(0.7, 0.3, 0, 0, 12);
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.blue), 0.1));

		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-50, 10, 10), 0.4, 0.001, 0.0001,
				new Vector(0.5, 0.5, 1)));

		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(50, 30, 10), 0.4, 0.001, 0.0001,
				new Vector(0.5, 0.5, 1)));
		lights.add(new DirectionalLight(new Color(200, 200, 200), new Vector(1, 1, 0)));

		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-600, 10, 10), 0.4, 0.001, 0.0001,
				new Vector(0.5, 0.5, 1)));
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-10, 10, 10), 0.4, 0.001, 0.0001,
				new Vector(0.5, 0.5, 1)));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 150), new Color(java.awt.Color.red),
				new Material(0.4, 0.5, 0, 1, 100)));

		geometries.add(new Sphere(8, new Point3D(3, -15, 50), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, -40, 60), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, -60, 70), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, -80, 80), new Color(java.awt.Color.orange), material));

		geometries.add(new Sphere(8, new Point3D(3, -60, 60), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, -110, 80), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, -130, 80), new Color(java.awt.Color.orange), material));

		geometries.add(new Sphere(8, new Point3D(3, 95, 70), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, 110, 80), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, 130, 80), new Color(java.awt.Color.orange), material));

		geometries.add(new Sphere(8, new Point3D(3, 15, 50), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, 35, 60), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, 55, 70), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, 75, 80), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(3, 95, 50), new Color(java.awt.Color.orange), material));

		geometries.add(new Sphere(8, new Point3D(20, 0, 60), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(50, 10, 70), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(80, 20, 80), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(110, 30, 90), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(140, 40, 100), new Color(java.awt.Color.orange), material));

		geometries.add(new Sphere(8, new Point3D(-20, -15, 60), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(-50, -30, 70), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(-80, -40, 80), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(-110, -50, 90), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(-140, -60, 100), new Color(java.awt.Color.orange), material));

		geometries.add(new Sphere(8, new Point3D(50, -20, 70), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(80, -30, 80), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(110, -40, 90), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(140, -50, 100), new Color(java.awt.Color.orange), material));

		geometries.add(new Sphere(8, new Point3D(-20, 10, 60), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(-50, 20, 70), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(-80, 30, 80), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(-110, 40, 90), new Color(java.awt.Color.orange), material));
		geometries.add(new Sphere(8, new Point3D(-140, 50, 100), new Color(java.awt.Color.orange), material));

		ImageWriter imageWriter = new ImageWriter("Planet1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		render.writeToImage();
	}

	@Test
	public void Clown1() throws InterruptedException {
		Scene scene = new Scene("Clown");
		Material material = new Material(0.7, 0.3, 0, 0, 122);
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(new Color(20, 30, 50));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(0, 200, 0), new Point3D(0, -50, -100), 1, 0.0001, 0.000001,
				new Vector(1, -0.5, 10)));
		lights.add(
				new SpotLight(Color.WHITE, new Point3D(-100, 80, -100), 1, 0.0001, 0.000001, new Vector(1, -0.5, 10)));
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-20, 0, 10), 1, 0.001, 0.0001,
				new Vector(0.2, 0.5, 1)));
		scene.setLights(lights);
		scene.addGeometry(new Sphere(7.5, new Point3D(25, 0, -50), new Color(200, 200, 200),
				new Material(0.1, 0.2, 0, 0.7, 100)));
		scene.addGeometry(new Sphere(7.5, new Point3D(0, -20, -50), new Color(200, 200, 200),
				new Material(0.1, 0.2, 0, 0.7, 100)));
		scene.addGeometry(
				new Sphere(7.5, new Point3D(0, 0, -50), new Color(255, 0, 0), new Material(0.1, 0.2, 0, 0, 100)));
		scene.addGeometry(new Triangle(new Point3D(10, -40, 0), new Point3D(25, -25, -25), new Point3D(50, -50, 0),
				new Color(200, 200, 0), material));
		scene.addGeometry(new Triangle(new Point3D(40, -10, 0), new Point3D(25, -25, -25), new Point3D(50, -50, 0),
				new Color(200, 0, 0), material));
		scene.addGeometry(
				new Sphere(7.5, new Point3D(50, -50, -0), new Color(255, 0, 0), new Material(0.1, 0.2, 0, 0, 100)));

		scene.addGeometry(new Sphere(4, new Point3D(25, 0, -55), Color.BLACK, new Material(0.2, 0.6, 0, 0, 100)));
		scene.addGeometry(new Sphere(4, new Point3D(0, -20, -55), Color.BLACK, new Material(0.2, 0.6, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(45, new Point3D(0, 0, 0), new Color(0, 0, 255), new Material(0.1, 0.2, 0, 0.7, 0, 100)));

		scene.addGeometry(new Sphere(7.5, new Point3D(-60, 60, 0), new Color(145, 123, 91), material));
		scene.addGeometry(new Sphere(7.5, new Point3D(-70, 80, 0), new Color(145, 123, 91), material));
		scene.addGeometry(new Sphere(7.5, new Point3D(-75, 100, 0), new Color(145, 123, 91), material));
		scene.addGeometry(new Sphere(7.5, new Point3D(-50.5, 40, 0), new Color(145, 123, 91), material));
		scene.addGeometry(new Sphere(7.5, new Point3D(-30, 30, 0), new Color(255, 255, 255), material));
		scene.addGeometry(
				new Sphere(5, new Point3D(-55, 55, 0), new Color(20, 20, 20), new Material(0.1, 0.2, 0.7, 0, 100)));
		scene.addGeometry(
				new Sphere(5, new Point3D(-65, 70, 0), new Color(20, 20, 20), new Material(0.1, 0.2, 0.7, 0, 100)));
		scene.addGeometry(
				new Sphere(5, new Point3D(-40, 35, 0), new Color(20, 20, 20), new Material(0.1, 0.2, 0.7, 0, 100)));
		scene.addGeometry(
				new Sphere(5, new Point3D(-72.5, 90, 0), new Color(20, 20, 20), new Material(0.1, 0.2, 0.7, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-150, 250, -75), new Vector(0, -150, 0), new Vector(150, 0, 0),
				new Color(java.awt.Color.orange), new Material(0.4, 0.5, 0, 0, 100)));
		scene.addGeometry(
				new Circle(75, new Point3D(125, -120, 120), new Vector(0, 0, 1), new Color(200, 200, 200), material));
		scene.addGeometry(
				new Circle(75, new Point3D(-75, 175, -76), new Vector(0, 0, 1), new Color(250, 0, 0), material));
		scene.addGeometry(new Circle(55, new Point3D(-75, 175, -76.1), new Vector(0, 0, 1),
				new Color(java.awt.Color.orange), material));
		scene.addGeometry(
				new Circle(35, new Point3D(-75, 175, -76.2), new Vector(0, 0, 1), new Color(250, 0, 0), material));
		scene.addGeometry(new Circle(15, new Point3D(-75, 175, -76.3), new Vector(0, 0, 1),
				new Color(java.awt.Color.orange), material));
		scene.addGeometry(
				new Circle(5, new Point3D(-75, 175, -76.4), new Vector(0, 0, 1), new Color(250, 0, 0), material));

		scene.addGeometry(new Triangle(new Point3D(-250, 250, 450), new Point3D(-250, -250, 450),
				new Point3D(250, 250, 450), new Color(20, 20, 20), new Material(0.1, 0.1, 0.6, 0, 100)));

		scene.addGeometry(new Rectangle(new Point3D(-150, 250, -75), new Vector(0, -150, 0), new Vector(150, 0, 0),
				new Color(java.awt.Color.orange), new Material(0.4, 0.5, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-150, 250, 75), new Vector(0, -150, 0), new Vector(150, 0, 0),
				new Color(java.awt.Color.orange), new Material(0.4, 0.5, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-150, 250, 75), new Point3D(-150, 250, -75),
				new Point3D(-150, 100, -75), new Color(java.awt.Color.orange), new Material(0.4, 0.5, 0, 0, 100)));

		scene.addGeometry(new Rectangle(new Point3D(-250, 250, 0), new Vector(500, 0, 0), new Vector(0, 0, 750),
				new Color(0, 100, 100), new Material(0.2, 0.2, 0.5, 0, 100)));

		scene.addGeometry(new Sphere(2, new Point3D(-25, 0, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-23, 10, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-27, 5, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-3, 20, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-8, 20, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-10, 18, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-15, 15, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));

		ImageWriter imageWriter = new ImageWriter("Clown1", 1000, 1000, 1000, 1000);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void Clown2() throws InterruptedException {
		Scene scene = new Scene("Clown");
		Material material = new Material(0.7, 0.3, 0, 0, 122);
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(new Color(20, 30, 50));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(0, 200, 0), new Point3D(0, -50, -100), 1, 0.0001, 0.000001,
				new Vector(1, -0.5, 10)));
		lights.add(
				new SpotLight(Color.WHITE, new Point3D(-100, 80, -100), 1, 0.0001, 0.000001, new Vector(1, -0.5, 10)));
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(-20, 0, 10), 1, 0.001, 0.0001,
				new Vector(0.2, 0.5, 1)));
		scene.setLights(lights);

		// geometries.add(new Triangle(new Point3D(0, -800, 3), new Point3D(200, -400,
		// 3), new Point3D(-500, -200, 3),
		// new Color(0, 100, 200), material));
		// geometries.add(new Sphere(50, new Point3D(-55, 40, -270), new Color(250,0,0),
		// material));
		scene.addGeometry(new Sphere(7.5, new Point3D(25, 0, -50), new Color(200, 200, 200),
				new Material(0.1, 0.2, 0, 0.7, 100)));
		scene.addGeometry(new Sphere(7.5, new Point3D(0, -20, -50), new Color(200, 200, 200),
				new Material(0.1, 0.2, 0, 0.7, 100)));
		scene.addGeometry(
				new Sphere(7.5, new Point3D(0, 0, -50), new Color(255, 0, 0), new Material(0.1, 0.2, 0, 0, 100)));
		scene.addGeometry(new Triangle(new Point3D(10, -40, 0), new Point3D(25, -25, -25), new Point3D(50, -50, 0),
				new Color(200, 200, 0), material));
		scene.addGeometry(new Triangle(new Point3D(40, -10, 0), new Point3D(25, -25, -25), new Point3D(50, -50, 0),
				new Color(200, 0, 0), material));
		scene.addGeometry(
				new Sphere(7.5, new Point3D(50, -50, -0), new Color(255, 0, 0), new Material(0.1, 0.2, 0, 0, 100)));

		scene.addGeometry(new Sphere(4, new Point3D(25, 0, -55), Color.BLACK, new Material(0.2, 0.6, 0, 0, 100)));
		scene.addGeometry(new Sphere(4, new Point3D(0, -20, -55), Color.BLACK, new Material(0.2, 0.6, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(45, new Point3D(0, 0, 0), new Color(0, 0, 255), new Material(0.1, 0.2, 0, 0.7, 100, 100)));

		scene.addGeometry(new Sphere(7.5, new Point3D(-60, 60, 0), new Color(145, 123, 91), material));
		scene.addGeometry(new Sphere(7.5, new Point3D(-70, 80, 0), new Color(145, 123, 91), material));
		scene.addGeometry(new Sphere(7.5, new Point3D(-75, 100, 0), new Color(145, 123, 91), material));
		scene.addGeometry(new Sphere(7.5, new Point3D(-50.5, 40, 0), new Color(145, 123, 91), material));
		scene.addGeometry(new Sphere(7.5, new Point3D(-30, 30, 0), new Color(255, 255, 255), material));
		scene.addGeometry(
				new Sphere(5, new Point3D(-55, 55, 0), new Color(20, 20, 20), new Material(0.1, 0.2, 0.7, 0, 100)));
		scene.addGeometry(
				new Sphere(5, new Point3D(-65, 70, 0), new Color(20, 20, 20), new Material(0.1, 0.2, 0.7, 0, 100)));
		scene.addGeometry(
				new Sphere(5, new Point3D(-40, 35, 0), new Color(20, 20, 20), new Material(0.1, 0.2, 0.7, 0, 100)));
		scene.addGeometry(
				new Sphere(5, new Point3D(-72.5, 90, 0), new Color(20, 20, 20), new Material(0.1, 0.2, 0.7, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-150, 250, -75), new Vector(0, -150, 0), new Vector(150, 0, 0),
				new Color(java.awt.Color.orange), new Material(0.4, 0.5, 0, 0, 100)));
		scene.addGeometry(
				new Circle(75, new Point3D(125, -120, 120), new Vector(0, 0, 1), new Color(200, 200, 200), material));
		scene.addGeometry(
				new Circle(75, new Point3D(-75, 175, -76), new Vector(0, 0, 1), new Color(250, 0, 0), material));
		scene.addGeometry(new Circle(55, new Point3D(-75, 175, -76.1), new Vector(0, 0, 1),
				new Color(java.awt.Color.orange), material));
		scene.addGeometry(
				new Circle(35, new Point3D(-75, 175, -76.2), new Vector(0, 0, 1), new Color(250, 0, 0), material));
		scene.addGeometry(new Circle(15, new Point3D(-75, 175, -76.3), new Vector(0, 0, 1),
				new Color(java.awt.Color.orange), material));
		scene.addGeometry(
				new Circle(5, new Point3D(-75, 175, -76.4), new Vector(0, 0, 1), new Color(250, 0, 0), material));

		scene.addGeometry(new Triangle(new Point3D(-250, 250, 450), new Point3D(-250, -250, 450),
				new Point3D(250, 250, 450), new Color(20, 20, 20), new Material(0.1, 0.1, 0.6, 0, 100, 100)));
		/*
		 * scene.addGeometry(new Rectangle(new Point3D(-125, 100, 60), new Vector(70,
		 * -90, 0), new Vector(50, 3500.0/90, 10), new Color(80, 90, 100), new
		 * Material(0.4, 0.5, 0, 0, 100)));
		 */

		scene.addGeometry(new Rectangle(new Point3D(-150, 250, -75), new Vector(0, -150, 0), new Vector(150, 0, 0),
				new Color(java.awt.Color.orange), new Material(0.4, 0.5, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-150, 250, 75), new Vector(0, -150, 0), new Vector(150, 0, 0),
				new Color(java.awt.Color.orange), new Material(0.4, 0.5, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-150, 250, 75), new Point3D(-150, 250, -75),
				new Point3D(-150, 100, -75), new Color(java.awt.Color.orange), new Material(0.4, 0.5, 0, 0, 100)));

		scene.addGeometry(new Rectangle(new Point3D(-250, 250, 0), new Vector(500, 0, 0), new Vector(0, 0, 750),
				new Color(0, 100, 100), new Material(0.2, 0.2, 0.5, 0, 100)));

		scene.addGeometry(new Sphere(2, new Point3D(-25, 0, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-23, 10, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-27, 5, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-3, 20, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-8, 20, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-10, 18, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));
		scene.addGeometry(new Sphere(2, new Point3D(-15, 15, 0), Color.WHITE, new Material(0.1, 0.1, 0, 0.5, 100)));

		ImageWriter imageWriter = new ImageWriter("glossy Clown", 1000, 1000, 1000, 1000);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void moreShadowTests() throws InterruptedException {
		Scene scene = new Scene("Test scene 1");
		Material material = new Material(0.7, 0.3, 0, 0, 120);
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(0, 75, -140), 1, 0.0001, 0.000001,
				new Vector(0, 0, 1)));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 0), new Color(17, 11, 48), material));
		geometries.add(new Triangle(new Point3D(10, 80, -120), new Point3D(-10, 60, -120), new Point3D(-10, 80, -120),
				new Color(0, 0, 200), material));
		ImageWriter imageWriter = new ImageWriter("shadows test 1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();

		// moving the triangle
		scene = new Scene("Test scene 2");
		geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 0), new Color(17, 11, 48), material));
		geometries.add(new Triangle(new Point3D(20, 60, -120), new Point3D(0, 40, -120), new Point3D(0, 60, -120),
				new Color(0, 0, 200), material));
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		lights.clear();
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(0, 75, -140), 1, 0.0001, 0.000001,
				new Vector(0, 0, 1)));
		scene.setLights(lights);

		imageWriter = new ImageWriter("shadows test 2", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();

		// moving the light
		scene = new Scene("Test scene 3");
		geometries = scene.getGeometries();
		geometries.add(new Sphere(100, new Point3D(0, 0, 0), new Color(17, 11, 48), material));
		geometries.add(new Triangle(new Point3D(10, 80, -120), new Point3D(-10, 60, -120), new Point3D(-10, 80, -120),
				new Color(0, 0, 200), material));
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.1));
		lights.clear();
		lights.add(new SpotLight(new Color(177, 134, 206), new Point3D(0, 75, -125), 1, 0.0001, 0.000001,
				new Vector(0, 0, 1)));
		scene.setLights(lights);

		imageWriter = new ImageWriter("shadows test 3", 500, 500, 500, 500);
		render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void roomTest() throws InterruptedException {
		Scene scene = new Scene("Room");
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(5000);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new DirectionalLight(new Color(200, 0, 0), new Vector(0, 0, 1)));
		lights.add(new PointLight(new Color(0, 30, 200), new Point3D(40, 80, 100), 0.81, 0, 0));
		lights.add(new PointLight(new Color(0, 30, 200), new Point3D(80, 100, 90), 0.81, 0, 0));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();

		geometries.add(
				new Sphere(50, new Point3D(-100, 0, 250), new Color(30, 30, 30), new Material(0.2, 0.2, 0.6, 0, 100)));
		geometries.add(
				new Sphere(50, new Point3D(80, 0, 150), new Color(30, 30, 30), new Material(0.2, 0.2, 0.6, 0, 100)));
		geometries.add(
				new Sphere(50, new Point3D(80, 100, 150), new Color(130, 130, 30), new Material(0.6, 0.4, 0, 0, 100)));
		geometries.add(
				new Sphere(50, new Point3D(0, 0, 0), new Color(10, 10, 100), new Material(0.3, 0.2, 0.05, 0.45, 130)));
		geometries.add(new Cylinder(5, Point3D.ZERO, new Ray(Point3D.ZERO, new Vector(5, -1, 1)),
				new Color(30, 200, 30), new Material(0.3, 0.2, 0, 0, 120), 100));
		ImageWriter imageWriter = new ImageWriter("room test 1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		render.writeToImage();
	}

	@Test
	public void glossTest() throws InterruptedException {
		Scene scene = new Scene("first glossy");
		scene.setCamera(new Camera(new Point3D(0, 0, -500), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(500);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		scene.addGeometry(new Sphere(300, new Point3D(-500, 500, 900), new Color(0, 0, 150),
				new Material(0.3, 0.2, 0.1, 0.3, 0.175, 100)));
		scene.addGeometry(new Sphere(150, new Point3D(-500, 500, 900), new Color(250, 0, 0),
				new Material(0.3, 0.2, 0, 0, 0.1, 100)));
		scene.addGeometry(new Triangle(new Point3D(-1400, 1400, 1400), new Point3D(-1400, -1400, 1400),
				new Point3D(1400, 1400, 1400), new Color(60, 60, 60), new Material(0.3, 0.2, 1, 0, 0.2, 100)));
		scene.addGeometry(new Triangle(new Point3D(250, -250, 350), new Point3D(-1400, -1400, 1400),
				new Point3D(1400, 1400, 1400), new Color(60, 60, 60), new Material(0.3, 0.2, 1, 0, 0.1, 100)));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(200, -200, 150), 1, 0.00001, 0.000001,
				new Vector(-1, 1, 2)));
		scene.setLights(lights);
		ImageWriter imageWriter = new ImageWriter("gloss test 1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void android() throws InterruptedException {
		Scene scene = new Scene("feb5");
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		scene.addGeometry(new Cylinder(50, new Point3D(0, 100, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 100));
		scene.addGeometry(new Cylinder(10, new Point3D(70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(
				new Sphere(50, new Point3D(0, 0, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-300, 200, 600), new Vector(600, 0, 0), new Vector(0, -600, 0),
				new Color(50, 50, 50), new Material(0.2, 0.2, 0.3, 0.3, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-300, 200, -600), new Vector(600, 0, 0), new Vector(0, 0, 1200),
				new Color(50, 50, 50), new Material(0.2, 0.2, 0.2, 0, 0, 100)));
		// scene.addGeometry(new Rectangle(new Point3D(,,500)));

		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(200, -200, 150), 1, 0.00001, 0.000001,
				new Vector(-1, 1, 2)));
		lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(10, 10, 10)));
		scene.setLights(lights);
		ImageWriter imageWriter = new ImageWriter("gloss test 2", 1000, 1000, 1000, 1000);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void android2() throws InterruptedException {
		Scene scene = new Scene("feb5");
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		scene.addGeometry(new Cylinder(50, new Point3D(0, 100, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 100));
		scene.addGeometry(new Cylinder(10, new Point3D(70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(
				new Sphere(50, new Point3D(0, 0, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-300, 200, 600), new Vector(600, 0, 0), new Vector(0, -600, 0),
				new Color(50, 50, 50), new Material(0.2, 0.2, 0.3, 0.3, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-300, 200, -600), new Vector(600, 0, 0), new Vector(0, -600, 0),
				new Color(10, 010, 10), new Material(0.2, 0.2, 0, 0.5, 10, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-300, 200, -600), new Vector(600, 0, 0), new Vector(0, 0, 1200),
				new Color(50, 50, 50), new Material(0.2, 0.2, 0.2, 0, 0, 100)));

		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(200, -200, 150), 1, 0.00001, 0.000001,
				new Vector(-1, 1, 2)));
		lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(10, 10, 10)));
		scene.setLights(lights);
		ImageWriter imageWriter = new ImageWriter("gloss test 3", 1000, 1000, 1000, 1000);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void android3() throws InterruptedException {
		Scene scene = new Scene("feb5");
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		scene.addGeometry(new Cylinder(50, new Point3D(0, 100, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 100));
		scene.addGeometry(new Cylinder(10, new Point3D(70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(
				new Sphere(50, new Point3D(0, 0, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-300, 200, 600), new Vector(600, 0, 0), new Vector(0, -600, 0),
				new Color(50, 50, 50), new Material(0.2, 0.2, 0.3, 0.3, 10, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-300, 200, -600), new Vector(600, 0, 0), new Vector(0, -600, 0),
				new Color(10, 010, 10), new Material(0.2, 0.2, 0, 0.5, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-300, 200, -600), new Vector(600, 0, 0), new Vector(0, 0, 1200),
				new Color(50, 50, 50), new Material(0.2, 0.2, 0.2, 0, 0, 100)));
		// scene.addGeometry(new Rectangle(new Point3D(,,500)));

		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(200, -200, 150), 1, 0.00001, 0.000001,
				new Vector(-1, 1, 2)));
		lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(10, 10, 10)));
		scene.setLights(lights);
		ImageWriter imageWriter = new ImageWriter("gloss test 4", 1000, 1000, 1000, 1000);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void daniel() throws InterruptedException {
		Scene scene = new Scene("daniel");
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(500);
		scene.setBackground(new Color(java.awt.Color.DARK_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		List<LightSource> lights = new ArrayList<>();
		lights.add(new DirectionalLight(new Color(200, 0, 0), new Vector(0, 0, 1)));
		lights.add(new PointLight(new Color(0, 30, 200), new Point3D(40, 80, 100), 0.81, 0, 0));
		lights.add(new PointLight(new Color(0, 30, 200), new Point3D(80, 100, 90), 0.81, 0, 0));
		scene.setLights(lights);
		Geometries geometries = scene.getGeometries();

		geometries.add(new Rectangle(new Point3D(-600, 700, 700), new Vector(0, -70, 0), new Vector(1200, 0, 0),
				new Color(250, 250, 250), new Material(0.4, 0.3, 0, 0, 120)));

		geometries.add(new Sphere(1200, new Point3D(0, 0, 0), new Color(10, 10, 1),
				new Material(0.3, 0.2, 0.05, 0.45, 13, 130)));
		geometries.add(new Cylinder(40, Point3D.ZERO, new Ray(Point3D.ZERO, new Vector(10, 10, 1)),
				new Color(250, 250, 250), new Material(0.3, 0.2, 0, 0, 120), 800));
		geometries.add(new Cylinder(40, Point3D.ZERO, new Ray(Point3D.ZERO, new Vector(-10, 10, 1)),
				new Color(250, 250, 250), new Material(0.3, 0.2, 0, 0, 120), 800));

		geometries.add(new Cylinder(40, Point3D.ZERO, new Ray(Point3D.ZERO, new Vector(0, 0, 1)),
				new Color(250, 250, 250), new Material(0.3, 0.2, 0, 0, 120), 800));

		scene.addGeometry(new Rectangle(new Point3D(-500, 2100, -600), new Vector(0, -1000, 0), new Vector(1200, 0, 0),
				new Color(10, 10, 1), new Material(0.3, 0.2, 0.05, 0.45, 130)));

		ImageWriter imageWriter = new ImageWriter("daniel", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void androidms() throws InterruptedException {
		Scene scene = new Scene("feb5");
		scene.setCamera(new Camera(new Point3D(-100, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		scene.addGeometry(new Cylinder(20, new Point3D(-90, 60, 0), new Vector(-10, -20, 0), new Color(0, 0, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));

		scene.addGeometry(new Cylinder(15, new Point3D(40, -40, 0), new Vector(-1, -2, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 15));
		scene.addGeometry(new Cylinder(15, new Point3D(-40, -40, 0), new Vector(1, -2, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 15));
		scene.addGeometry(new Rectangle(new Point3D(-95, 50, -5), new Vector(10, -5, 5), new Vector(-40, -80, 0),
				new Color(70, 70, 70), new Material(0.3, 0.3, 0.2, 0.1, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-95, 50, -5), new Vector(-10, 5, 5), new Vector(-40, -80, 0),
				new Color(70, 70, 70), new Material(0.3, 0.3, 0.2, 0.1, 0, 100)));
		scene.addGeometry(new Triangle(new Point3D(-135, -30, -5), new Point3D(-125, -35, 0),
				new Point3D(-165, -100, -5), new Color(70, 70, 70), new Material(0.3, 0.3, 0.2, 0.1, 0, 100)));
		scene.addGeometry(new Triangle(new Point3D(-135, -30, -5), new Point3D(-145, -25, 0),
				new Point3D(-165, -100, -5), new Color(70, 70, 70), new Material(0.3, 0.3, 0.2, 0.1, 0, 100)));
		scene.addGeometry(new Cylinder(50, new Point3D(0, 100, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 100));
		scene.addGeometry(new Cylinder(10, new Point3D(70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(
				new Sphere(50, new Point3D(0, 0, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-400, 200, 600), new Vector(600, 0, 0), new Vector(0, -600, 0),
				new Color(java.awt.Color.pink), new Material(0.2, 0.2, 0.3, 0.3, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-400, 200, -600), new Vector(600, 0, 0), new Vector(0, -600, 0),
				new Color(10, 10, 10), new Material(0.2, 0.2, 0, 0.5, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-400, 200, -600), new Vector(600, 0, 0), new Vector(0, 0, 1200),
				new Color(50, 50, 50), new Material(0.2, 0.2, 0.2, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-350, 150, 50), new Vector(50, 0, 0), new Vector(0, -50, 0),
				new Color(0, 0, 200), new Material(0.1, 0.1, 0.1, 0.1, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-350, 90, 50), new Vector(50, 0, 0), new Vector(0, -50, 0),
				new Color(200, 0, 0), new Material(0.1, 0.1, 0.1, 0.1, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-290, 150, 50), new Vector(50, 0, 0), new Vector(0, -50, 0),
				new Color(200, 200, 0), new Material(0.1, 0.1, 0.1, 0.1, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-290, 90, 50), new Vector(50, 0, 0), new Vector(0, -50, 0),
				new Color(0, 200, 0), new Material(0.1, 0.1, 0.1, 0.1, 0, 100)));

		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(200, -200, 150), 1, 0.00001, 0.000001,
				new Vector(-1, 1, 2)));
		lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(10, 10, 10)));
		scene.setLights(lights);
		ImageWriter imageWriter = new ImageWriter("android and ms", 1000, 1000, 1000, 1000);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
	@Test
	public void glossyAndroidMs() throws InterruptedException {
		Scene scene = new Scene("feb5");
		scene.setCamera(new Camera(new Point3D(-100, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(20, 20, 20), 0.1));
		scene.addGeometry(new Cylinder(20, new Point3D(-90, 60, 0), new Vector(-10, -20, 0), new Color(0, 0, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));

		scene.addGeometry(new Cylinder(15, new Point3D(40, -40, 0), new Vector(-1, -2, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 15));
		scene.addGeometry(new Cylinder(15, new Point3D(-40, -40, 0), new Vector(1, -2, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 15));
		scene.addGeometry(new Rectangle(new Point3D(-95, 50, -5), new Vector(10, -5, 5), new Vector(-40, -80, 0),
				new Color(70, 70, 70), new Material(0.3, 0.3, 0.2, 0.1, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-95, 50, -5), new Vector(-10, 5, 5), new Vector(-40, -80, 0),
				new Color(70, 70, 70), new Material(0.3, 0.3, 0.2, 0.1, 0, 100)));
		scene.addGeometry(new Triangle(new Point3D(-135, -30, -5), new Point3D(-125, -35, 0),
				new Point3D(-165, -100, -5), new Color(70, 70, 70), new Material(0.3, 0.3, 0.2, 0.1, 0, 100)));
		scene.addGeometry(new Triangle(new Point3D(-135, -30, -5), new Point3D(-145, -25, 0),
				new Point3D(-165, -100, -5), new Color(70, 70, 70), new Material(0.3, 0.3, 0.2, 0.1, 0, 100)));
		scene.addGeometry(new Cylinder(50, new Point3D(0, 100, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 100));
		scene.addGeometry(new Cylinder(10, new Point3D(70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-70, 60, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(-30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(new Cylinder(10, new Point3D(30, 140, 0), new Vector(0, -1, 0), new Color(0, 200, 0),
				new Material(0.2, 0.2, 0, 0, 0, 100), 20));
		scene.addGeometry(
				new Sphere(50, new Point3D(0, 0, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 40, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-70, 60, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 120, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(-30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(
				new Sphere(10, new Point3D(30, 140, 0), new Color(0, 200, 0), new Material(0.2, 0.2, 0, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-400, 200, 600), new Vector(600, 0, 0), new Vector(0, -600, 0),
				new Color(java.awt.Color.pink), new Material(0.2, 0.2, 0.3, 0.3, 100, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-400, 200, -600), new Vector(600, 0, 0), new Vector(0, -600, 0),
				new Color(10, 10, 10), new Material(0.2, 0.2, 0, 0.5, 100, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-400, 200, -600), new Vector(600, 0, 0), new Vector(0, 0, 1200),
				new Color(50, 50, 50), new Material(0.2, 0.2, 0.2, 0, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-350, 150, 50), new Vector(50, 0, 0), new Vector(0, -50, 0),
				new Color(0, 0, 200), new Material(0.1, 0.1, 0.1, 0.1, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-350, 90, 50), new Vector(50, 0, 0), new Vector(0, -50, 0),
				new Color(200, 0, 0), new Material(0.1, 0.1, 0.1, 0.1, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-290, 150, 50), new Vector(50, 0, 0), new Vector(0, -50, 0),
				new Color(200, 200, 0), new Material(0.1, 0.1, 0.1, 0.1, 0, 100)));
		scene.addGeometry(new Rectangle(new Point3D(-290, 90, 50), new Vector(50, 0, 0), new Vector(0, -50, 0),
				new Color(0, 200, 0), new Material(0.1, 0.1, 0.1, 0.1, 0, 100)));

		List<LightSource> lights = new ArrayList<>();
		lights.add(new SpotLight(new Color(255, 0, 0), new Point3D(200, -200, 150), 1, 0.00001, 0.000001,
				new Vector(-1, 1, 2)));
		lights.add(new DirectionalLight(new Color(150, 150, 150), new Vector(10, 10, 10)));
		scene.setLights(lights);
		ImageWriter imageWriter = new ImageWriter("ткнгги android and ms", 1000, 1000, 1000, 1000);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
}