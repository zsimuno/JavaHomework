package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static hr.fer.zemris.java.hw01.Factorial.*;

/**
 * @author Zvonimir Šimunović
 *
 */
class FactorialTest {

	@Test
	void fiveFactorial() {
		assertEquals(120, factorial(5));
	}
	
	@Test
	void tenFactorial() {
		assertEquals(3628800, factorial(10));
	}

	@Test
	void zeroFactorial() {
		assertEquals(1, factorial(0));
	}

	@Test
	void negativeTenFactorial() {
		assertThrows(IllegalArgumentException.class, () -> {
			factorial(-10);
		});
	}
}
