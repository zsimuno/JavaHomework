/**
 * 
 */
package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Vector2D} class
 * 
 * @author Zvonimir Šimunović
 *
 */
class Vector2DTest {

	@Test
	void testConstructor() {
		Vector2D v = new Vector2D(2, 5);
		assertEquals(2, v.getX());
		assertEquals(5, v.getY());
	}

	@Test
	void testConstructor2() {
		Vector2D v = new Vector2D(128, 26);
		assertEquals(128, v.getX());
		assertEquals(26, v.getY());
	}

	@Test
	void testConstructor3() {
		Vector2D v = new Vector2D(0, 0);
		assertEquals(0, v.getX());
		assertEquals(0, v.getY());
	}

	@Test
	void testConstructor4() {
		Vector2D v = new Vector2D(-5, -10);
		assertEquals(-5, v.getX());
		assertEquals(-10, v.getY());
	}

	@Test
	void testCopy() {
		Vector2D v1 = new Vector2D(2, 5);
		Vector2D v = v1.copy();
		assertEquals(2, v.getX());
		assertEquals(5, v.getY());
	}

	@Test
	void testCopy2() {
		Vector2D v1 = new Vector2D(124, 12);
		Vector2D v = v1.copy();
		assertEquals(v, v1);
	}

	@Test
	void testTranslate() {
		Vector2D v = new Vector2D(2, 5);
		v.translate(new Vector2D(3, 7));
		assertEquals(new Vector2D(5, 12), v);
	}

	@Test
	void testTranslate2() {
		Vector2D v = new Vector2D(-5, -6);
		v.translate(new Vector2D(10, 12));
		assertEquals(new Vector2D(5, 6), v);
	}

	@Test
	void testTranslated() {
		Vector2D v1 = new Vector2D(2, 5);
		Vector2D v = v1.translated(new Vector2D(2, 6));
		assertEquals(new Vector2D(4, 11), v);
	}

	@Test
	void testTranslated2() {
		Vector2D v1 = new Vector2D(-5, -6);
		Vector2D v = v1.translated(new Vector2D(10, 12));
		assertEquals(new Vector2D(5, 6), v);
	}

	@Test
	void testTranslateNull() {
		Vector2D v = new Vector2D(2, 5);
		assertThrows(NullPointerException.class, () -> {
			v.translate(null);
		});
	}

	@Test
	void testTranslatedNull() {
		Vector2D v = new Vector2D(2, 5);
		assertThrows(NullPointerException.class, () -> {
			v.translated(null);
		});
	}

	@Test
	void testRotate() {
		Vector2D v = new Vector2D(2, 5);
		v.rotate(Math.PI);
		assertEquals(new Vector2D(-2, -5), v);

	}

	@Test
	void testRotate2() {
		Vector2D v = new Vector2D(2, 5);
		v.rotate(Math.PI / 2);
		assertEquals(new Vector2D(-5, 2), v);

	}

	@Test
	void testRotated() {
		Vector2D v1 = new Vector2D(2, 5);
		Vector2D v = v1.rotated(Math.PI);
		assertEquals(new Vector2D(-2, -5), v);
	}

	@Test
	void testRotated2() {
		Vector2D v1 = new Vector2D(2, 5);
		Vector2D v = v1.rotated(Math.PI / 2);
		assertEquals(new Vector2D(-5, 2), v);
	}

	@Test
	void testScale() {
		Vector2D v = new Vector2D(2, 5);
		v.scale(2);
		assertEquals(new Vector2D(4, 10), v);
	}

	@Test
	void testScale2() {
		Vector2D v = new Vector2D(4, 11);
		v.scale(0.5);
		assertEquals(new Vector2D(2, 5.5), v);
	}

	@Test
	void testScaled() {
		Vector2D v1 = new Vector2D(2, 5);
		Vector2D v = v1.scaled(3);
		assertEquals(new Vector2D(6, 15), v);
	}

	@Test
	void testScaled2() {
		Vector2D v1 = new Vector2D(4, 11);
		Vector2D v = v1.scaled(0.5);
		assertEquals(new Vector2D(2, 5.5), v);
	}
}
