package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	@Test
	void testAdd1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertNull(v2.getValue());

	}

	@Test
	void testAdd2() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
		assertEquals(Double.valueOf(13), v1.getValue());
		assertEquals(Integer.valueOf(1), v2.getValue());
	}

	@Test
	void testAdd3() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
		assertEquals(Integer.valueOf(13), v1.getValue());
		assertEquals(Integer.valueOf(1), v2.getValue());
	}

	@Test
	void testThrowsStringAdd() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> {
			v1.add(v2.getValue());
		});
	}

	@Test
	void testThrowsStringSub() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> {
			v1.divide(v2.getValue());
		});
	}

	@Test
	void testThrowsStringMul() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> {
			v1.multiply(v2.getValue());
		});
	}

	@Test
	void testThrowsStringDiv() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> {
			v1.divide(v2.getValue());
		});
	}

	@Test
	void testThrowsStringCmp() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> {
			v1.numCompare(v2.getValue());
		});
	}

	@Test
	void testThrowsBoolean() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		assertThrows(RuntimeException.class, () -> {
			v1.add(Boolean.valueOf(true));
		});
	}

	@Test
	void testThrowsBoolean2() {
		ValueWrapper v1 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> {
			v1.add(Integer.valueOf(5));
		});
	}

	@Test
	void testSub1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertNull(v2.getValue());

	}

	@Test
	void testSub2() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.subtract(v2.getValue());
		assertEquals(Double.valueOf(11), v1.getValue());
		assertEquals(Integer.valueOf(1), v2.getValue());
	}

	@Test
	void testSub3() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.subtract(v2.getValue());
		assertEquals(Integer.valueOf(11), v1.getValue());
		assertEquals(Integer.valueOf(1), v2.getValue());
	}

	@Test
	void testSub4() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		assertEquals(Integer.valueOf(1), v1.getValue());
		assertNull(v2.getValue());

	}

	@Test
	void testSub5() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.subtract(v2.getValue());
		assertEquals(Integer.valueOf(-1), v1.getValue());
		assertEquals(Integer.valueOf(1), v2.getValue());

	}

	@Test
	void testMul1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.multiply(v2.getValue());
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertNull(v2.getValue());

	}

	@Test
	void testMul2() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.multiply(v2.getValue());
		assertEquals(Double.valueOf(12), v1.getValue());
		assertEquals(Integer.valueOf(1), v2.getValue());
	}

	@Test
	void testMul3() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.multiply(v2.getValue());
		assertEquals(Integer.valueOf(12), v1.getValue());
		assertEquals(Integer.valueOf(1), v2.getValue());
	}

	@Test
	void testMul4() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("12");
		v1.multiply(v2.getValue());
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals("12", v2.getValue());

	}

	@Test
	void testDiv1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(6));
		v1.divide(v2.getValue());
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(Integer.valueOf(6), v2.getValue());

	}

	@Test
	void testDiv2() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(3));
		v1.divide(v2.getValue());
		assertEquals(Double.valueOf(4), v1.getValue());
		assertEquals(Integer.valueOf(3), v2.getValue());
	}

	@Test
	void testDiv3() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(3));
		v1.divide(v2.getValue());
		assertEquals(Integer.valueOf(4), v1.getValue());
		assertEquals(Integer.valueOf(3), v2.getValue());
	}

	@Test
	void testCompare1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(6));
		assertEquals(-1, v1.numCompare(v2.getValue()));
		assertNull(v1.getValue());
		assertEquals(Integer.valueOf(6), v2.getValue());

	}

	@Test
	void testCompare2() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(3));
		assertEquals(1, v1.numCompare(v2.getValue()));
		assertEquals("1.2E1", v1.getValue());
		assertEquals(Integer.valueOf(3), v2.getValue());
	}

	@Test
	void testCompare3() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(3));
		assertEquals(1, v1.numCompare(v2.getValue()));
		assertEquals("12", v1.getValue());
		assertEquals(Integer.valueOf(3), v2.getValue());
	}

	@Test
	void testCompare4() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("12");
		assertEquals(-1, v1.numCompare(v2.getValue()));
		assertNull(v1.getValue());
		assertEquals("12", v2.getValue());

	}

	@Test
	void testCompare5() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper("1.2E1");
		assertEquals(0, v1.numCompare(v2.getValue()));
		assertEquals("12", v1.getValue());
		assertEquals("1.2E1", v2.getValue());
	}

	@Test
	void testCompareNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		assertEquals(0, v1.numCompare(v2.getValue()));
		assertEquals(null, v1.getValue());
		assertEquals(null, v2.getValue());
	}

	@Test
	void testSetValue() {
		ValueWrapper v1 = new ValueWrapper(null);
		v1.setValue(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), v1.getValue());
	}

}
