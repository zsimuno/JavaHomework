/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ArrayIndexedCollection<Object>} class.
 * 
 * @author Zvonimir Šimunović
 *
 */
class ArrayIndexedCollectionTest {

	/**
	 * {@link ArrayIndexedCollection<Object>} object we will use for testing
	 */
	private ArrayIndexedCollection<Object> ar;

	/**
	 * Setups one simple {@link ArrayIndexedCollection<Object>} object
	 * 
	 */
	@BeforeEach
	void setUp() {
		ar = new ArrayIndexedCollection<Object>();
		ar.add("Java");
		ar.add(Integer.valueOf(32));
		ar.add("London");
		ar.add(Double.valueOf(1.0));
	}

	@Test
	void emptyConstructor() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<Object>();
		assertTrue(col.isEmpty());
	}

	@Test
	void constructFromOther() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<Object>(ar);
		assertTrue(col.get(0).equals(ar.get(0)));
		assertTrue(col.get(1).equals(ar.get(1)));
		assertTrue(col.get(2).equals(ar.get(2)));
		assertTrue(col.get(3).equals(ar.get(3)));
	}

	@Test
	void constructFromNull() {
		assertThrows(NullPointerException.class, () -> {
			new ArrayIndexedCollection<Object>(null);
		});
	}

	@Test
	void constructInvalidCapacity() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ArrayIndexedCollection<Object>(0);
		});
	}

	@Test
	void invalidCapacityWithCollection() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ArrayIndexedCollection<Object>(ar, 0);
		});
	}

	@Test
	void validCapacityNullCollection() {
		assertThrows(NullPointerException.class, () -> {
			new ArrayIndexedCollection<Object>(null, 5);
		});
	}

	@Test
	void constructCapacityWithCollection() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<Object>(ar, 2);
		assertEquals(4, col.size());
	}

	@Test
	void testSize() {
		assertEquals(4, ar.size());
	}

	@Test
	void sizeOfEmpty() {
		ArrayIndexedCollection<Object> empty = new ArrayIndexedCollection<Object>();
		assertEquals(0, empty.size());
	}

	@Test
	void testContains() {
		assertTrue(ar.contains("Java"));

	}

	@Test
	void testContainsFalse() {
		assertFalse(ar.contains("NotJava"));

	}

	@Test
	void testContainsNull() {
		assertFalse(ar.contains(null));

	}

	@Test
	void removeObject() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<Object>();
		col.add("Java");
		col.add(Integer.valueOf(2));
		col.add("Here i am!");
		assertTrue(col.remove("Java"));
		assertEquals(2, col.size());
		assertFalse(col.contains("Java"));
	}

	@Test
	void testRemoveNonexistentObject() {
		assertFalse(ar.remove("NotJava"));
	}

	@Test
	void testToArray() {
		Object[] array = ar.toArray();
		for (int i = 0; i < array.length; i++) {
			assertEquals(i, ar.indexOf(array[i]));
		}
	}

	@Test
	void emptyToArray() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<Object>();
		assertNotNull(col.toArray());
	}

	@Test
	void testForEach() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<Object>();
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(1));
		col.add(Integer.valueOf(2));
		ArrayIndexedCollection<Object> col2 = new ArrayIndexedCollection<Object>();
		
		Processor<Object> processor = col2::add;

		col.forEach(processor);
		assertTrue(col2.contains(Integer.valueOf(1)));
		assertTrue(col2.contains(Integer.valueOf(2)));
		assertEquals(3, col2.size());
	}

	@Test
	void testAdding() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<Object>();
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
			ar.add(null);
		});
	}

	@Test
	void testGet() {
		assertEquals("London", ar.get(2));
	}

	@Test
	void getWrongIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ar.get(4);
		});
	}

	@Test
	void testInsert() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<Object>();
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
			ar.insert(null, 0);
		});

	}

	@Test
	void insertOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ar.insert("Out of bounds", 5);
		});
	}
	
	@Test
	void testIndexOf() {
		assertEquals(2, ar.indexOf("London"));
	}
	
	@Test
	void testIndexOfNull() {
		assertEquals(-1, ar.indexOf(null));
	}
	
	@Test
	void indexOfNonexistent() {
		assertEquals(-1, ar.indexOf("Where am I?"));
	}
	
	@Test
	void removeIndex() {
		ar.remove(2);
		assertFalse(ar.contains("Here i am!"));
		assertEquals(3, ar.size());
	}
	
	@Test
	void removeIndexOOB() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ar.remove(5);
		});
	}

	@Test
	void testClear() {
		ar.clear();
		assertTrue(ar.isEmpty());

	}
	
	@Test
	void testStringCollection() {
		ArrayIndexedCollection<String> col = new ArrayIndexedCollection<String>();
		col.add("Java");
		col.add("Java");
		col.insert("Here i am!", 1);
		assertEquals(1, col.indexOf("Here i am!"));
		assertEquals(3, col.size());
		
	}
	
	@Test
	void testAddNull() {
		ArrayIndexedCollection<String> col = new ArrayIndexedCollection<String>();
		assertThrows(NullPointerException.class, () -> {
			col.add(null);
		});
		
		
	}
	
	@Test
	void containsNullStringCol() {
		ArrayIndexedCollection<String> col = new ArrayIndexedCollection<String>();
		col.add("Java");
		col.add("Java");
		col.insert("Here i am!", 2);
		assertFalse(col.contains(null));
	}
	
	@Test
	void testIntegerContainsString() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertFalse(col.contains("2"));
	
	}
	
	@Test
	void removeStringFromIntegerCol() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertFalse(col.remove("2"));
	}
	
	@Test
	void removeNullFromIntegerCol() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertFalse(col.remove(null));
	}
	
	@Test
	void removeFromIntegerCol() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertTrue(col.remove(Integer.valueOf(2)));
		assertEquals(1, col.size());
	}
	
	@Test
	void indexOfNullIntegerCol() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<Integer>();
		col.add(2);
		col.insert(10, 1);
		assertEquals(-1, col.indexOf(null));
	}

}
