/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link LinkedListIndexedCollection} class.
 * 
 * @author Zvonimir Šimunović
 *
 */
class LinkedListIndexedCollectionTest {

	/**
	 * {@link LinkedListIndexedCollection} object we will use for testing
	 */
	private LinkedListIndexedCollection list;

	/**
	 * Setups one simple {@link LinkedListIndexedCollection} object
	 * 
	 */
	@BeforeEach
	void setUp() {
		list = new LinkedListIndexedCollection();
		list.add("Java");
		list.add(Integer.valueOf(32));
		list.add("London");
		list.add(Double.valueOf(1.0));
	}

	@Test
	void emptyConstructor() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertTrue(col.isEmpty());
	}

	@Test
	void constructFromOther() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection(list);
		assertTrue(col.get(0).equals(list.get(0)));
		assertTrue(col.get(1).equals(list.get(1)));
		assertTrue(col.get(2).equals(list.get(2)));
		assertTrue(col.get(3).equals(list.get(3)));
	}

	@Test
	void constructFromNull() {
		assertThrows(NullPointerException.class, () -> {
			new LinkedListIndexedCollection(null);
		});
	}

	@Test
	void testSize() {
		assertEquals(4, list.size());
	}

	@Test
	void sizeOfEmpty() {
		LinkedListIndexedCollection empty = new LinkedListIndexedCollection();
		assertEquals(0, empty.size());
	}

	@Test
	void testContains() {
		assertTrue(list.contains("Java"));

	}

	@Test
	void testContainsFalse() {
		assertFalse(list.contains("NotJava"));

	}

	@Test
	void testContainsNull() {
		assertFalse(list.contains(null));

	}

	@Test
	void removeObject() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Java");
		col.add(Integer.valueOf(2));
		col.add("Here i am!");
		assertTrue(col.remove("Java"));
		assertEquals(2, col.size());
		assertFalse(col.contains("Java"));
	}

	@Test
	void testRemoveNonexistentObject() {
		assertFalse(list.remove("NotJava"));
	}

	@Test
	void testTolistray() {
		Object[] listray = list.toArray();
		for (int i = 0; i < listray.length; i++) {
			assertEquals(i, list.indexOf(listray[i]));
		}
	}

	@Test
	void emptyTolistray() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertNotNull(col.toArray());
	}

	@Test
	void testForEach() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		
		Processor processor = col2::add;


		col.forEach(processor);
		assertTrue(col2.contains(Integer.valueOf(1)));
		assertTrue(col2.contains(Integer.valueOf(2)));
		assertEquals(3, col2.size());
	}

	@Test
	void testAdding() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Java");
		assertEquals("Java", col.get(0));
		col.add("Java");
		assertEquals("Java", col.get(1));
		col.add(Integer.valueOf(2));
		assertEquals(Integer.valueOf(2), col.get(2));
	}

	@Test
	void testAddingNull() {
		assertThrows(NullPointerException.class, () -> {
			list.add(null);
		});
	}

	@Test
	void testGet() {
		assertEquals("London", list.get(2));
	}

	@Test
	void getWrongIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(4);
		});
	}

	@Test
	void testInsert() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Java");
		col.add("Java");
		col.add(Integer.valueOf(2));
		col.insert("Here i am!", 2);
		assertEquals(2, col.indexOf("Here i am!"));
		assertEquals(4, col.size());
	}

	@Test
	void insertNull() {
		assertThrows(NullPointerException.class, () -> {
			list.insert(null, 0);
		});

	}

	@Test
	void insertOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.insert("Out of bounds", 5);
		});
	}
	
	@Test
	void testIndexOf() {
		assertEquals(2, list.indexOf("London"));
	}
	
	@Test
	void testIndexOfNull() {
		assertEquals(-1, list.indexOf(null));
	}
	
	@Test
	void indexOfNonexistent() {
		assertEquals(-1, list.indexOf("Where am I?"));
	}
	
	@Test
	void removeIndex() {
		list.remove(2);
		assertFalse(list.contains("Here i am!"));
		assertEquals(3, list.size());
	}
	
	@Test
	void removeIndexOOB() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(5);
		});
	}

	@Test
	void testClear() {
		list.clear();
		assertTrue(list.isEmpty());

	}

}
