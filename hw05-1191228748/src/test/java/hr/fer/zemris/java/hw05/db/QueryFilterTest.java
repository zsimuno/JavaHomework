package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QueryFilterTest {

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
	void testDirectQuery1() {

		QueryParser parser = new QueryParser(" jmbag = \"0000000051\"");
		assertTrue(parser.isDirectQuery());
		
		StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
		assertEquals("Andro", FieldValueGetters.FIRST_NAME.get(r));
		assertEquals("Skočir", FieldValueGetters.LAST_NAME.get(r));
			
	}
	
	@Test
	void testDirectQuery2() {

		QueryParser parser = new QueryParser("   jmbag    =  \"0000000055\"  ");
		assertTrue(parser.isDirectQuery());
		
		StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
		assertEquals("Ivan", FieldValueGetters.FIRST_NAME.get(r));
		assertEquals("Šimunov", FieldValueGetters.LAST_NAME.get(r));
			
	}
	
	@Test
	void testDirectQuery3() {

		QueryParser parser = new QueryParser("jmbag=\"0000000057\"");
		assertTrue(parser.isDirectQuery());
		
		StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
		assertEquals("Hrvoje", FieldValueGetters.FIRST_NAME.get(r));
		assertEquals("Širanović", FieldValueGetters.LAST_NAME.get(r));
			
	}
	
	@Test
	void testMultipleConditions1() {
		QueryParser parser = new QueryParser("jmbag>\"0000000051\" aNd firstName > \"U\" ");
		assertFalse(parser.isDirectQuery());
		
		for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
			assertEquals("Veljko", FieldValueGetters.FIRST_NAME.get(r));
			assertEquals("Šimunović", FieldValueGetters.LAST_NAME.get(r));
		}
	}
	
	@Test
	void testMultipleConditions2() {
		QueryParser parser = new QueryParser("firstName like \"*\" and lastName LiKe \"Šam*\"");
		assertFalse(parser.isDirectQuery());
		
		for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
			assertEquals("Pavle", FieldValueGetters.FIRST_NAME.get(r));
			assertEquals("Šamija", FieldValueGetters.LAST_NAME.get(r));
		}
	}
	
	@Test
	void testMultipleConditions3() {
		QueryParser parser = new QueryParser("lastName > \"Srdarević\" anD lastName LIKE \"Šimun*\"");
		assertFalse(parser.isDirectQuery());
		
		ArrayList<StudentRecord> expectedResult = new ArrayList<>();
		expectedResult.add(new StudentRecord("0000000055", "Šimunov", "Ivan", "4"));
		expectedResult.add(new StudentRecord("0000000056", "Šimunović", "Veljko", "5"));
		
		for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
			assertTrue(expectedResult.contains(r));
		}
	}
	

}
