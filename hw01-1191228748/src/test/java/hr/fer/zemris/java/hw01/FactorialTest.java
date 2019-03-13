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
		int n = factorial(5);
		assertEquals(120, n);
	}

	@Test
	void zeroFactorial() {
		int n = factorial(0);
		assertEquals(1, n);
	}

	@Test
	void negativeTenFactorial() {
		assertThrows(IllegalArgumentException.class, () -> {
			factorial(-10);
		});
	}
}
