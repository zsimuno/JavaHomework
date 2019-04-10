package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	void test1() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("0000000057", "Širanović", "Hrvoje", "2");

		assertEquals("Bos*", expr.getStringLiteral());
		assertEquals("Širanović", expr.getFieldGetter().get(record));

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), expr.getStringLiteral());

		assertFalse(recordSatisfies);
	}
	
	@Test
	void test2() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.FIRST_NAME,
				"Ana",
				ComparisonOperators.GREATER);

		StudentRecord record = new StudentRecord("0000000057", "Širanović", "Hrvoje", "2");

		assertEquals("Ana", expr.getStringLiteral());
		assertEquals("Hrvoje", expr.getFieldGetter().get(record));

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), expr.getStringLiteral());

		assertTrue(recordSatisfies);
	}
	
	@Test
	void test3() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.JMBAG,
				"0000000057",
				ComparisonOperators.EQUALS);

		StudentRecord record = new StudentRecord("0000000057", "Širanović", "Hrvoje", "2");

		assertEquals("0000000057", expr.getStringLiteral());
		assertEquals("0000000057", expr.getFieldGetter().get(record));

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), expr.getStringLiteral());

		assertTrue(recordSatisfies);
	}
	
	@Test
	void test4() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME, 
				"Bos*",
				ComparisonOperators.LIKE);

		StudentRecord record = new StudentRecord("0000000057", "Bosnik", "Hrvoje", "2");

		assertEquals("Bos*", expr.getStringLiteral());
		assertEquals("Bosnik", expr.getFieldGetter().get(record));

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), expr.getStringLiteral());

		assertTrue(recordSatisfies);
	}

}
