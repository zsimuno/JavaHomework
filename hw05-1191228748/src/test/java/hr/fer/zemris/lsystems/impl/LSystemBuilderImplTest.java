/**
 * 
 */
package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

/**
 * Tests for {@link LSystemBuilderImpl}
 * 
 * @author Zvonimir Šimunović
 *
 */
class LSystemBuilderImplTest {
	
	private static LSystem ls;
	
	@BeforeAll
	static void beforeAll() {
		LSystemBuilderImpl lsBuilder = new LSystemBuilderImpl();
		ls = lsBuilder.setAxiom("F").registerProduction('F', "F+F--F+F").build();
	}

	@Test
	void generateZero() {
		assertEquals("F", ls.generate(0));
	}
	
	@Test
	void generateOne() {
		assertEquals("F+F--F+F", ls.generate(1));
	}
	
	@Test
	void generateTwo() {
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", ls.generate(2));
	}

}
