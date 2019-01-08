package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Util;
import primitives.Vector;

public class VectorTests {

	@Test
	public void testAdd() {
		Vector v1 = new Vector(3.5, -5, -10);
		Vector v2 = new Vector(35, 5, 1);
		Vector expected = new Vector(38.5, 0, -9);
		try {
			v1.add(v1.scale(-1));
			fail("Vector added with negative self returned zero");
		} catch (Exception e) {
		}
		assertEquals(expected, v1.add(v2));
	}

	@Test
	public void testSubtract() {
		Vector v1 = new Vector(3.5, -5, 1);
		Vector v2 = new Vector(35, 5, 10);
		Vector expected = new Vector(31.5, 10, 9);
		try {
			v1.subtract(v1);
			fail("Vector subtracted itself returned zero");
		} catch (Exception e) {
		}
		assertEquals(expected, v2.subtract(v1));
	}

	@Test
	public void testNormalize() {
		Vector v = new Vector(3.5, -5, 10);
		v.normalization();
		assertTrue(Util.isOne(v.length()));
	}

	@Test
	public void testDotProduct() {
		Vector v1 = new Vector(1, 2, 0);
		Vector v2 = new Vector(1, -0.5, 0);
		Vector v3 = new Vector(5, 12, 10);
		assertEquals(v1.length() * v1.length(), v1.dotProduct(v1), 0.000000000001); // 0 degrees
		assertEquals(0, v1.dotProduct(v2)); // 90 degrees
		assertEquals(-v1.length() * v1.length(), v1.dotProduct(v1.scale(-1)), 0.000000000001); // 180 degrees
		assertEquals(29, v1.dotProduct(v3)); // random case
	}

	@Test
	public void testLength() {
		Vector v1 = new Vector(1, 2, 3);
		assertEquals(Math.sqrt(14), v1.length());
	}

	@Test
	public void testScaling() {
		Vector v1 = new Vector(35, 5, 10);
		Vector expected = new Vector(70, 10, 20);
		assertEquals(expected, v1.scale(2));
		try {
			v1.scale(0);
			fail("scaling vector to zero gives a zero vector and function did not throw exception");
		} catch (Exception e) {
		}
	}

	@Test
	public void testCrossProduct() {
		Vector v1 = new Vector(35, 5, 10);
		Vector v2 = new Vector(3, 12, 58);
		Vector expected = new Vector(170, -2000, 405);
		assertEquals(expected, v1.crossproduct(v2));
		try {
			v1.crossproduct(v1);
			fail("Non zero crossproduct with itself");
		} catch (Exception e) {
		}
	}
}
