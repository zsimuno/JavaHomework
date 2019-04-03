/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * @author Zvonimir Šimunović
 *
 */
class SimpleHashtableTest {

	@Test
	void testBadConstructor() {

		assertThrows(IllegalArgumentException.class, () -> {
			new SimpleHashtable<>(-5);
		});
	}

	@Test
	void testIsEmptyFalse() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("Ivana", 2);
		hash.put("Ante", 2);
		hash.put("Jasna", 2);
		hash.put("Kristina", 5);
		hash.put("Ivana", 5);
		assertFalse(hash.isEmpty());
	}

	@Test
	void testIsEmptyOnEmpty() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		assertTrue(hash.isEmpty());
	}

	@Test
	void testSize() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("Ivana", 2);
		hash.put("Ante", 2);
		hash.put("Jasna", 2);
		hash.put("Kristina", 5);
		hash.put("Ivana", 5);
		assertEquals(4, hash.size());
	}

	@Test
	void testSizeEmpty() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		assertEquals(0, hash.size());
	}

	@Test
	void testPutNullKey() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> hash.put(null, null));
	}

	@Test
	void testPut() {
		SimpleHashtable<Integer, Integer> hash = new SimpleHashtable<>();
		hash.put(21, 2);
		assertEquals(1, hash.size());
		assertEquals(2, hash.get(21));
	}

	@Test
	void testPutNullValue() {
		SimpleHashtable<Integer, Integer> hash = new SimpleHashtable<>();
		hash.put(21, null);
		assertEquals(1, hash.size());
		assertNull(hash.get(21));
	}

	@Test
	void testGet() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("Ivana", 2);
		hash.put("Ante", 2);
		hash.put("Jasna", 2);
		hash.put("Kristina", 5);
		hash.put("Ivana", 5);
		assertEquals(5, hash.get("Ivana"));
		assertEquals(2, hash.get("Jasna"));
	}

	@Test
	void testGetNull() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("Ivana", 2);
		hash.put("Ante", 2);
		assertNull(hash.get(null));
	}

	@Test
	void testGetOnEmpty() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		assertNull(hash.get("Ivana"));
	}

	@Test
	void getOnNonExisting() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("Ivana", 2);
		hash.put("Ante", 2);
		assertNull(hash.get("Jasna"));
	}

	@Test
	void testClear() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("Ivana", 2);
		hash.put("Ante", 2);
		hash.put("Jasna", 2);
		hash.clear();
		assertEquals(0, hash.size());
		assertNull(hash.get("Ivana"));
	}

	@Test
	void getExistingNullValue() {
		SimpleHashtable<Double, Integer> hash = new SimpleHashtable<>();
		hash.put(21.2, 2);
		hash.put(2.1, null);
		hash.put(23.3, 3);
		hash.put(24.4, 4);
		assertNull(hash.get(2.1));
	}

	@Test
	void putExistingKey() {
		SimpleHashtable<String, String> hash = new SimpleHashtable<>();
		hash.put("null", null);
		hash.put("key", "one");
		hash.put("Key", "My first value");
		hash.put("Key", "My second value");
		hash.put("Key", "My third value");
		assertEquals(3, hash.size());
		assertEquals("My third value", hash.get("Key"));
	}

	@Test
	void testContains() {

		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);
		hash.put("one that is null", null);
		hash.put("42", 21);
		assertTrue(hash.containsKey("First"));
		assertTrue(hash.containsKey("Second"));
		assertTrue(hash.containsKey("one that is null"));
		assertTrue(hash.containsKey("42"));
	}

	@Test
	void testContainsNonExisting() {

		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);

		assertFalse(hash.containsKey("Third"));
		assertFalse(hash.containsKey("Not null one"));
	}

	@Test
	void testContainsNull() {

		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);
		hash.put("one that is null", null);
		hash.put("42", 21);

		assertFalse(hash.containsKey(null));
	}

	@Test
	void testContainsDiffParam() {

		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);

		assertFalse(hash.containsKey(2144));
	}

	@Test
	void testContainsValue() {

		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);
		hash.put("32", 32);
		hash.put("42", 21);
		assertTrue(hash.containsValue(1));
		assertTrue(hash.containsValue(2));
		assertTrue(hash.containsValue(32));
		assertTrue(hash.containsValue(21));
	}

	@Test
	void containsNonExistingValue() {

		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);

		assertFalse(hash.containsKey(3));
		assertFalse(hash.containsKey(21));
	}

	@Test
	void containsNullValueFalse() {

		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);
		hash.put("42", 21);

		assertFalse(hash.containsKey(null));
	}

	@Test
	void containsNullValueTrue() {

		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);
		hash.put("one that is null", null);
		hash.put("42", 21);

		assertTrue(hash.containsValue(null));
	}

	@Test
	void containsDiffParamValue() {

		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);

		assertFalse(hash.containsValue("First"));
	}

	@Test
	void removeAnObject() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);
		hash.put("one that is null", null);
		hash.put("42", 21);
		assertEquals(4, hash.size());
		hash.remove("Second");
		assertEquals(3, hash.size());
		assertFalse(hash.containsKey("Second"));
	}

	@Test
	void removeFirstInSlot() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);
		hash.put("one that is null", null);
		hash.put("42", 21);
		assertEquals(4, hash.size());
		hash.remove("First");
		assertEquals(3, hash.size());
		assertFalse(hash.containsKey("First"));
	}

	@Test
	void removeNonFirstInSlot() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		assertEquals(4, examMarks.size());
		examMarks.remove("Jasna");
		assertEquals(3, examMarks.size());
		assertFalse(examMarks.containsKey("Jasna"));
	}

	@Test
	void removeNonExistring() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);
		hash.remove("Not here!");
		assertEquals(2, hash.size());
	}
	
	@Test
	void removeNull() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>();
		hash.put("First", 1);
		hash.put("Second", 2);
		hash.remove(null);
		assertEquals(2, hash.size());
	}

	@Test
	void testToString() {
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

	@Test
	void testForEach() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		int sum = 0;
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			sum += pair.getValue();
		}
		assertEquals(14, sum);
	}

	@Test
	void testIterator() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		assertEquals(0, examMarks.size());

		assertFalse(examMarks.containsKey("Ivana"));
		assertFalse(examMarks.containsKey("Ante"));
		assertFalse(examMarks.containsKey("Jasna"));
		assertFalse(examMarks.containsKey("Kristina"));

	}

	@Test
	void iteratorNextOnEmpty() {
		SimpleHashtable<String, Integer> hash = new SimpleHashtable<>(10);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = hash.iterator();

		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});

	}

	@Test
	void iteratorNextNoMoreElems() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}

		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});

	}

	@Test
	void iteratorRemoveTwice() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

		iter.next();
		iter.remove();

		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});

	}

	@Test
	void iteratorModificationRemove() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Josip", 5);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

		iter.next();

		examMarks.remove("Ivana");

		assertThrows(ConcurrentModificationException.class, () -> {
			iter.next();
		});

	}

	@Test
	void iteratorModificationAdd() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

		iter.next();

		examMarks.put("Josip", 5);

		assertThrows(ConcurrentModificationException.class, () -> {
			iter.next();
		});

	}

	// TODO Test changing of last element

}
