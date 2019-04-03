/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Dictionary} class
 * 
 * @author Zvonimir Šimunović
 *
 */
class DictionaryTest {

	@Test
	void testIsEmptyFalse() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 2);
		dict.put("Ante", 2);
		dict.put("Jasna", 2);
		dict.put("Kristina", 5);
		dict.put("Ivana", 5);
		assertFalse(dict.isEmpty());
	}
	
	@Test
	void testIsEmptyFalse2() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 2);
		assertFalse(dict.isEmpty());
	}

	@Test
	void testIsEmptyOnEmpty() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		assertTrue(dict.isEmpty());
	}

	@Test
	void testSize() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 2);
		dict.put("Ante", 2);
		dict.put("Jasna", 2);
		dict.put("Kristina", 5);
		dict.put("Ivana", 5);
		assertEquals(4, dict.size());
	}
	
	@Test
	void testSize2() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 2);
		dict.put("Ante", 2);
		assertEquals(2, dict.size());
	}

	@Test
	void testSizeEmpty() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		assertEquals(0, dict.size());
	}

	@Test
	void testPutNullKey() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> dict.put(null, null));
	}

	@Test
	void testPut() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		dict.put(21, 2);
		dict.put(124, 4);
		assertEquals(2, dict.size());
		assertEquals(2, dict.get(21));
		assertEquals(4, dict.get(124));
	}
	
	@Test
	void testPut2() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		dict.put(21, 2);
		dict.put(124, 4);
		dict.put(21, 12);
		assertEquals(2, dict.size());
		assertEquals(12, dict.get(21));
		assertEquals(4, dict.get(124));
	}

	@Test
	void testPutNullValue() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		dict.put(21, null);
		assertEquals(1, dict.size());
		assertNull(dict.get(21));
	}

	@Test
	void testGet() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 2);
		dict.put("Ante", 2);
		dict.put("Jasna", 2);
		dict.put("Kristina", 5);
		dict.put("Ivana", 5);
		assertEquals(5, dict.get("Ivana"));
		assertEquals(2, dict.get("Jasna"));
	}

	@Test
	void testGet2() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 2);
		dict.put("Ante", 2);
		dict.put("Jasna", 2);
		assertEquals(2, dict.get("Ante"));
		assertEquals(2, dict.get("Jasna"));
	}
	
	@Test
	void testGetNull() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 2);
		dict.put("Ante", 2);
		assertNull(dict.get(null));
	}

	@Test
	void testGetOnEmpty() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		assertNull(dict.get("Ivana"));
	}

	@Test
	void getOnNonExisting() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 2);
		dict.put("Ante", 2);
		assertNull(dict.get("Jasna"));
	}
	
	@Test
	void testClear() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 2);
		dict.put("Ante", 2);
		dict.put("Jasna", 2);
		dict.clear();
		assertEquals(0, dict.size());
		assertNull(dict.get("Ivana"));
	}
	
	@Test
	void testClearOnEmpty() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.clear();
		assertEquals(0, dict.size());
	}
	
	@Test
	void getExistingNullValue() {
		Dictionary<Double, Integer> dict = new Dictionary<>();
		dict.put(21.2, 2);
		dict.put(2.1, null);
		dict.put(23.3, 3);
		dict.put(24.4, 4);
		assertNull(dict.get(2.1));
	}
	
	@Test
	void putExistingKey() {
		Dictionary<String, String> dict = new Dictionary<>();
		dict.put("null", null);
		dict.put("key", "one");
		dict.put("Key", "My first value");
		dict.put("Key", "My second value");
		dict.put("Key", "My third value");
		assertEquals(3, dict.size());
		assertEquals("My third value", dict.get("Key"));
	}
}
