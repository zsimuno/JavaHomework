/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link LinkedListIndexedCollection<Object>} class.
 * 
 * @author Zvonimir Šimunović
 *
 */
class LinkedListIndexedCollectionTest {

	/**
	 * {@link LinkedListIndexedCollection<Object>} object we will use for testing
	 */
	private LinkedListIndexedCollection<Object> list;

	/**
	 * Setups one simple {@link LinkedListIndexedCollection<Object>} object
	 * 
	 */
	@BeforeEach
	void setUp() {
		list = new LinkedListIndexedCollection<Object>();
		list.add("Java");
		list.add(Integer.valueOf(32));
		list.add("London");
		list.add(Double.valueOf(1.0));
	}

	@Test
	void emptyConstructor() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<Object>();
		assertTrue(col.isEmpty());
	}

	@Test
	void constructFromOther() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<Object>(list);
		assertTrue(col.get(0).equals(list.get(0)));
		assertTrue(col.get(1).equals(list.get(1)));
		assertTrue(col.get(2).equals(list.get(2)));
		assertTrue(col.get(3).equals(list.get(3)));
	}

	@Test
	void constructFromNull() {
		assertThrows(NullPointerException.class, () -> {
			new LinkedListIndexedCollection<Object>(null);
		});
	}

	@Test
	void testSize() {
		assertEquals(4, list.size());
	}

	@Test
	void sizeOfEmpty() {
		LinkedListIndexedCollection<Object> empty = new LinkedListIndexedCollection<Object>();
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
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<Object>();
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
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<Object>();
		assertNotNull(col.toArray());
	}

	@Test
	void testForEach() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<Object>();
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		LinkedListIndexedCollection<Object> col2 = new LinkedListIndexedCollection<Object>();
		
		Processor<Object> processor = col2::add;


		col.forEach(processor);
		assertTrue(col2.contains(Integer.valueOf(1)));
		assertTrue(col2.contains(Integer.valueOf(2)));
		assertEquals(3, col2.size());
	}

	@Test
	void testAdding() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<Object>();
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
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<Object>();
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
	
	@Test
	void testStringCollection() {
		LinkedListIndexedCollection<String> col = new LinkedListIndexedCollection<String>();
		col.add("Java");
		col.add("Java");
		col.insert("Here i am!", 1);
		assertEquals(1, col.indexOf("Here i am!"));
		assertEquals(3, col.size());
		
	}
	
	@Test
	void testAddNull() {
		LinkedListIndexedCollection<String> col = new LinkedListIndexedCollection<String>();
		assertThrows(NullPointerException.class, () -> {
			col.add(null);
		});
		
		
	}
	
	@Test
	void containsNullStringCol() {
		LinkedListIndexedCollection<String> col = new LinkedListIndexedCollection<String>();
		col.add("Java");
		col.add("Java");
		col.insert("Here i am!", 2);
		assertFalse(col.contains(null));
	}
	
	@Test
	void testIntegerContainsString() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertFalse(col.contains("2"));
	
	}
	
	@Test
	void removeStringFromIntegerCol() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertFalse(col.remove("2"));
	}
	
	@Test
	void removeNullFromIntegerCol() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertFalse(col.remove(null));
	}
	
	@Test
	void removeFromIntegerCol() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertTrue(col.remove(Integer.valueOf(2)));
		assertEquals(1, col.size());
	}
	
	@Test
	void indexOfNullIntegerCol() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertEquals(-1, col.indexOf(null));
	}
	
	@Test
	void addIntegerToNumberList() {
		LinkedListIndexedCollection<Number> col1 = new LinkedListIndexedCollection<Number>();
		LinkedListIndexedCollection<Integer> col2 = new LinkedListIndexedCollection<Integer>();
		col1.add(2.1);
		col1.add(2);
		col2.add(5);
		col2.add(6);
		col1.addAllSatisfying(col2, (number) -> {
			return number.intValue() % 2 == 0;
		});
		assertEquals(3, col1.size());
		assertFalse(col1.contains(5));
		assertTrue(col1.contains(6));
	}
	
	@Test
	void addIntegerToNumberList2() {
		LinkedListIndexedCollection<Number> col1 = new LinkedListIndexedCollection<Number>();
		LinkedListIndexedCollection<Integer> col2 = new LinkedListIndexedCollection<Integer>();
		col1.add(2.1);
		col1.add(2);
		col2.add(5);
		col2.add(6);
		col1.addAll(col2);
		assertEquals(4, col1.size());
		assertTrue(col1.contains(5));
		assertTrue(col1.contains(6));
	}

}
