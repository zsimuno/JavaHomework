package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Vector3Test {

	private static Vector3 i;
	private static Vector3 j;
	private static Vector3 k;
	private static Vector3 l;

	private static final double precision = 1e-6;

	@BeforeAll
	static void beforeAll() {
		i = new Vector3(1, 0, 0);
		j = new Vector3(0, 1, 0);
		k = i.cross(j);
		l = k.add(j).scale(5);

	}

	@Test
	void testNorm() {
		assertEquals(7.0710678118654755, l.norm());
	}

	@Test
	void testNorm2() {
		assertTrue(Math.abs(Math.sqrt(14) - new Vector3(1, 2, 3).norm()) < precision);
	}

	@Test
	void testNormalized() {
		Vector3 m = l.normalized();
		assertEquals(new Vector3(0.0, 0.707107, 0.707107), m);
	}

	@Test
	void testNormalized2() {
		Vector3 m = new Vector3(3, 1, 2).normalized();
		assertEquals(new Vector3(3.0 / Math.sqrt(14), 1.0 / Math.sqrt(14), Math.sqrt(2.0 / 7.0)), m);
	}

	@Test
	void testNorm3() {
		assertTrue(Math.abs(1 - l.normalized().norm()) < precision);
	}

	@Test
	void testAdd() {
		assertEquals(new Vector3(0, 5, 5), l);
	}

	@Test
	void testAdd2() {
		Vector3 v1 = new Vector3(1, 0, 3);
		Vector3 v2 = new Vector3(-1, 4, 2);

		assertEquals(new Vector3(0, 4, 5), v1.add(v2));
	}

	@Test
	void testSub() {
		assertEquals(new Vector3(1, -1, 0), i.sub(j));
	}

	@Test
	void testSub2() {
		Vector3 v1 = new Vector3(1, 0, 3);
		Vector3 v2 = new Vector3(-1, 4, 2);

		assertEquals(new Vector3(2, -4, 1), v1.sub(v2));
	}

	@Test
	void testDot() {
		assertEquals(5.0, l.dot(j));
	}
	
	@Test
	void testDot2() {
		Vector3 v1 = new Vector3(3, 1, 2);
		Vector3 v2 = new Vector3(1, 2, 3);

		assertEquals(11, v1.dot(v2));
	}

	@Test
	void testCross() {
		assertEquals(new Vector3(0, 0, 1), k);
	}

	@Test
	void testCross2() {
		Vector3 v1 = new Vector3(1, 2, 3);
		Vector3 v2 = new Vector3(1, 5, 7);

		assertEquals(new Vector3(-1, -4, 3), v1.cross(v2));
	}

	@Test
	void testScale() {
		assertEquals(new Vector3(0, 5, 5), l);
	}

	@Test
	void testScale2() {
		assertEquals(new Vector3(0, 12, 12), new Vector3(0, 6, 6).scale(2));
	}

	@Test
	void testCosAngle() {
		assertEquals(0.4999999999999999, i.add(new Vector3(0, 1, 0)).cosAngle(l));
	}

	@Test
	void testCosAngle2() {
		Vector3 v1 = new Vector3(3, 4, 0);
		Vector3 v2 = new Vector3(4, 4, 2);

		assertEquals(14.0 / 15.0, v1.cosAngle(v2));
	}

	@Test
	void testGetX() {
		assertEquals(1, i.getX());
	}

	@Test
	void testGetY() {
		assertEquals(1, j.getY());
	}

	@Test
	void testGetZ() {
		assertEquals(1, k.getZ());
	}

	@Test
	void testToArray() {
		assertArrayEquals(new double[] { 1, 0, 0 }, i.toArray());
	}

}
