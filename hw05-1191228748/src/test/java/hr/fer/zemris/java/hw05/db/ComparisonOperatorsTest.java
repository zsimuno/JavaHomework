package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	void testLess1() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	void testLess2() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	void testLessOrEquals1() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	void testLessOrEquals2() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	void testLessOrEquals3() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	void testGreater1() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	void testGreater2() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	void testGreaterOrEquals1() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	void testGreaterOrEquals2() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	void testGreaterOrEquals3() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	void testEquals1() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	void testEquals2() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	void testNotEquals1() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	void testNotEquals2() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	void testLike1() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
	}
	
	@Test
	void testLike2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("AAA", "AA*AA"));
	}
	
	@Test
	void testLike3() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
	}
	
	@Test
	void testLike4() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Bijanić", "B*ć"));
	}
	
	

}
