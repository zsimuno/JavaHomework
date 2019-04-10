/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	private static StudentDatabase db;
	private static String[] students;

	@BeforeAll
	static void beforeAll() {
		students = new String[] { 
				"0000000050	Sikirica	Alen	3",
				"0000000051	Skočir	Andro	4",
				"0000000052	Slijepčević	Josip	5",
				"0000000053	Srdarević	Dario	2",
				"0000000054	Šamija	Pavle	3",
				"0000000055	Šimunov	Ivan	4",
				"0000000056	Šimunović	Veljko	5",
				"0000000057	Širanović	Hrvoje	2" };

		db = new StudentDatabase(students);
	}

	@Test
	void testSameJmbag() {
		String[] studentList = new String[] { 
				"0000000050	Sikirica	Alen	3",
				"0000000051	Skočir	Andro	4",
				"0000000050	Slijepčević	Josip	5", };

		assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(studentList));
	}
	
	@Test
	void testInvalidGrade() {
		String[] studentList = new String[] { 
				"0000000050	Sikirica	Alen	3",
				"0000000051	Skočir	Andro	12",
				"0000000050	Slijepčević	Josip	5", };

		assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(studentList));
	}

	@Test
	void testForJmbag1() {
		assertEquals("Skočir", db.forJMBAG("0000000051").getLastName());
		assertEquals("Dario", db.forJMBAG("0000000053").getFirstName());
		assertEquals("5", db.forJMBAG("0000000056").getFinalGrade());
	}

	@Test
	void testForJmbag2() {
		StudentRecord student = new StudentRecord("0000000057", "Širanović", "Hrvoje", "2");
		assertEquals(student, db.forJMBAG("0000000057"));
	}

	@Test
	void testFilterAlwaysTrue() {
		IFilter alwaysTrue = (record) -> true;
		List<StudentRecord> list = db.filter(alwaysTrue);

		assertTrue(list.size() == 8);
	}

	@Test
	void testFilterAlwaysFalse() {
		IFilter alwaysFalse = (record) -> false;
		List<StudentRecord> list = db.filter(alwaysFalse);
		assertTrue(list.isEmpty());

	}

}
