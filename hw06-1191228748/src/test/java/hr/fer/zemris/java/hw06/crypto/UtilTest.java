/**
 * 
 */
package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Util} class
 * 
 * @author Zvonimir Šimunović
 *
 */
class UtilTest {

	@Test
	void testToByte() {
		byte[] actual = Util.hextobyte("01aE22");
		byte[] expected = new byte[] { 1, -82, 34 };

		assertArrayEquals(expected, actual);

	}

	@Test
	void testToByte2() {
		byte[] actual = Util.hextobyte("010204081020");
		byte[] expected = new byte[] { 1, 2, 4, 8, 16, 32 };

		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testToByte3() {
		byte[] actual = Util.hextobyte("2d");
		byte[] expected = new byte[] { 45 };

		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testToByte4() {
		byte[] actual = Util.hextobyte("Fc");
		byte[] expected = new byte[] { -4 };

		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testToByteZero() {
		byte[] actual = Util.hextobyte("00");
		byte[] expected = new byte[] { 0 };

		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testToByteEmpty() {
		byte[] actual = Util.hextobyte("");
		byte[] expected = new byte[0];

		assertArrayEquals(expected, actual);
	}

	@Test
	void testToByteWrongLenght() {
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("01a22");
		});
	}

	@Test
	void testToByteWrongChar() {
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("0aG2");
		});

	}

	@Test
	void testToHex() {
		String result = Util.bytetohex(new byte[] { 1, -82, 34 });

		assertEquals("01ae22", result);

	}

	@Test
	void testToHex2() {
		String result = Util.bytetohex(new byte[] { 1, 2, 4, 8, 16, 32 });

		assertEquals("010204081020", result);

	}
	
	@Test
	void testToHex3() {
		String result = Util.bytetohex(new byte[] { 45 });

		assertEquals("2d", result);

	}
	
	@Test
	void testToHex4() {
		String result = Util.bytetohex(new byte[] { -4 });

		assertEquals("fc", result);

	}
	
	@Test
	void testToHexZero() {
		String result = Util.bytetohex(new byte[] { 0 });

		assertEquals("00", result);

	}
	
	@Test
	void testToHexEmpty() {
		String result = Util.bytetohex(new byte[0]);

		assertEquals("", result);

	}

}
