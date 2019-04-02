/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Zvonimir Šimunović
 *
 */
class SimpleHashtableTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		assertEquals(5, examMarks.get("Kristina"));
		// What is collection's size? Must be four!
		assertEquals(4, examMarks.size()); 
		
		assertEquals("[Ante=2, Ivana=5, Jasna=2, Kristina=5]", examMarks.toString());
	}

}
