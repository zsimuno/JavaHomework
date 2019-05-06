package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Vector3Test {

//	System.out.println(i); //	(1.000000, 0.000000, 0.000000)
//	System.out.println(j); //	(0.000000, 1.000000, 0.000000)
//	System.out.println(k); //	(0.000000, 0.000000, 1.000000)
//	System.out.println(l); //	(0.000000, 5.000000, 5.000000)
//	System.out.println(l.norm()); //	7.0710678118654755
//	System.out.println(m); //	(0.000000, 0.707107, 0.707107)
//	System.out.println(l.dot(j)); //	5.0
//	System.out.println(i.add(new Vector3(0, 1, 0)).cosAngle(l)); //	0.4999999999999999

	private static Vector3 i;
	private static Vector3 j;
	private static Vector3 k;
	private static Vector3 l;

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
	void testNormalized() {
		Vector3 m = l.normalized();
		assertEquals(new Vector3(0.0, 0.707107, 0.707107), m);
	}

	@Test
	void testAdd() {
		assertEquals(new Vector3(0, 5, 5), l);
	}

	@Test
	void testSub() {
		assertEquals(new Vector3(1, -1, 0), i.sub(j));
	}

	@Test
	void testDot() {
		assertEquals(5.0, l.dot(j));
	}

	@Test
	void testCross() {
		assertEquals(new Vector3(0, 0, 1), k);
	}

	@Test
	void testScale() {
		assertEquals(new Vector3(0, 5, 5), l);
	}

	@Test
	void testCosAngle() {
		assertEquals(0.4999999999999999, i.add(new Vector3(0, 1, 0)).cosAngle(l));
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
