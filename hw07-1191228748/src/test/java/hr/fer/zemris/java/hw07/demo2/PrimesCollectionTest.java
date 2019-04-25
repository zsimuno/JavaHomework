package hr.fer.zemris.java.hw07.demo2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class PrimesCollectionTest {

	@Test
	void test1() {
		PrimesCollection primesCollection = new PrimesCollection(5);
		List<Integer> list = Arrays.asList(2, 3, 5, 7, 11);
		var IteratorList = list.iterator();
		for (Integer prime : primesCollection) {
			assertEquals(IteratorList.next(), prime);
		}
	}

	@Test
	void test2() {
		PrimesCollection primesCollection = new PrimesCollection(10);
		List<Integer> list = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
		var IteratorPrimes = primesCollection.iterator();
		var IteratorList = list.iterator();
		while (IteratorPrimes.hasNext()) {
			assertEquals(IteratorList.next(), IteratorPrimes.next());
		}
	}

	@Test
	void test3() {
		PrimesCollection primesCollection = new PrimesCollection(0);
		for (Integer prime : primesCollection) {
			fail("Should not enter the loop! " + prime);
		}
	}

	@Test
	void test4() {
		PrimesCollection primesCollection = new PrimesCollection(1);
		for (Integer prime : primesCollection) {
			assertEquals(2, prime);
		}
	}

	@Test
	void testThrows1() {
		PrimesCollection primesCollection = new PrimesCollection(2);
		var IteratorPrimes = primesCollection.iterator();
		while (IteratorPrimes.hasNext()) {
			IteratorPrimes.next();
		}

		assertThrows(NoSuchElementException.class, () -> IteratorPrimes.next());
	}
	
	@Test
	void testThrows2() {
		PrimesCollection primesCollection = new PrimesCollection(0);
		var IteratorPrimes = primesCollection.iterator();
		assertThrows(NoSuchElementException.class, () -> IteratorPrimes.next());
	}

}
