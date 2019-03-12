package unittests;

import primitives.*;
import elements.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CameraTest {
	@Test
	void testRaysConstruction() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -14), new Vector(0, -20, 0));
		Point3D p0 = Point3D.ZERO;
		Vector vector;
		Ray ray;

		// size of 3*3 pixels
		//the ray crosses one of the diagonal edges
		vector = new Vector(1, -1, -2);
		ray = new Ray(p0, vector);
		assertEquals(ray, camera.constructRayThroughPixel(3, 3, 1, 1, 2, 3, 3),"bad constructRayThroughPixel 3x3 crossing one of the diagonal edges");
		//the ray crosses the center of the view plane
		vector = new Vector(0, 0, -2);
		ray = new Ray(p0, vector);
		assertEquals(ray, camera.constructRayThroughPixel(3, 3, 2, 2, 2, 3, 3),"bad constructRayThroughPixel 3x3 crossing the center of the view plane");
		//the ray crosses one of the regular edges
		vector = new Vector(1, 0, -2);
		ray = new Ray(p0, vector);
		assertEquals(ray, camera.constructRayThroughPixel(3, 3, 1, 2, 2, 3, 3),"bad constructRayThroughPixel 3x3 crossing one of the regular edges");

		// size of 4*4 pixels
		//the ray crosses one of the diagonal edges
		vector = new Vector(1.5, -1.5, -2);
		ray = new Ray(p0, vector);
		assertEquals(ray, camera.constructRayThroughPixel(4, 4, 1, 1, 2, 4, 4),"bad constructRayThroughPixel 4x4 crossing one of the diagonal edges");
		//the ray crosses the center of the view plane
		vector = new Vector(0.5, -0.5, -2);
		ray = new Ray(p0, vector);
		assertEquals(ray, camera.constructRayThroughPixel(4, 4, 2, 2, 2, 4, 4),"bad constructRayThroughPixel 4x4 crossing the center of the view plane");
		//the ray crosses one of the regular edges
		vector = new Vector(-0.5, -1.5, -2);
		ray = new Ray(p0, vector);
		assertEquals(ray, camera.constructRayThroughPixel(4, 4, 3, 1, 2, 4, 4),"bad constructRayThroughPixel 4x4 crossing one of the regular edges");
	}
}