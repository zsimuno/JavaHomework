package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	private static StudentRecord r1, r2, r3;

	@BeforeAll
	static void beforeAll() {
		r1 = new StudentRecord("0000000057", "Širanović", "Hrvoje", "2");
		r2 = new StudentRecord("0000000020", "Hibner", "Sonja", "5");
		r3 = new StudentRecord("0000000041", "Orešković", "Jakša", "2");

	}

	@Test
	void testGetFirstName() {
		assertEquals("Hrvoje", FieldValueGetters.FIRST_NAME.get(r1));
		assertEquals("Sonja", FieldValueGetters.FIRST_NAME.get(r2));
		assertEquals("Jakša", FieldValueGetters.FIRST_NAME.get(r3));
	}

	@Test
	void testGetLastName() {
		assertEquals("Širanović", FieldValueGetters.LAST_NAME.get(r1));
		assertEquals("Hibner", FieldValueGetters.LAST_NAME.get(r2));
		assertEquals("Orešković", FieldValueGetters.LAST_NAME.get(r3));
	}

	@Test
	void testGetJmbag() {
		assertEquals("0000000057", FieldValueGetters.JMBAG.get(r1));
		assertEquals("0000000020", FieldValueGetters.JMBAG.get(r2));
		assertEquals("0000000041", FieldValueGetters.JMBAG.get(r3));
	}

	@Test
	void testForDifferent() {
		assertNotEquals("Ivana", FieldValueGetters.FIRST_NAME.get(r1));
		assertNotEquals("Ivana", FieldValueGetters.LAST_NAME.get(r2));
		assertNotEquals("Ivana", FieldValueGetters.JMBAG.get(r3));
	}

}
