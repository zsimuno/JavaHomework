/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;


public class ValueWrapper {

	private Object value;

	private SolveValues solver = new SolveValues();

	public ValueWrapper(Object value) {
		this.value = value;
	}

	public void add(Object incValue) {
		this.value = solver.solve(this.value, incValue, (a, b) -> a + b);
	}

	public void subtract(Object decValue) {
		this.value = solver.solve(this.value, decValue, (a, b) -> a - b);
	}

	public void multiply(Object mulValue) {
		this.value = solver.solve(this.value, mulValue, (a, b) -> a * b);
	}

	public void divide(Object divValue) {
		this.value = solver.solve(this.value, divValue, (a, b) -> a / b);
	}

	public int numCompare(Object withValue) {
		if (this.value == null && withValue == null) {
			return 0;
		}
		
		return ((Double) solver.solve(this.value, withValue, (a, b) -> Double.valueOf(a.compareTo(b)))).intValue();
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	private static class SolveValues {

		private UnaryOperator<Object> tranformer = (value) -> {
			if (value == null)
				return Integer.valueOf(0);

			if (value instanceof Double || value instanceof Integer)
				return value;

			if (value instanceof String) {
				String stringValue = (String) value;

				try {
					if (stringValue.contains(".") || stringValue.contains("E")) {
						return Double.parseDouble(stringValue);

					} else {
						return Integer.parseInt(stringValue);
					}
				} catch (Exception e) {
					throw new RuntimeException("String is not parsable to a number!");
				}
			}

			throw new RuntimeException("Input is not supported!");

		};

		public Object solve(Object value1, Object value2, BinaryOperator<Double> operator) {
			value1 = tranformer.apply(value1);
			value2 = tranformer.apply(value2);

			if (value1 instanceof Double || value2 instanceof Double) {
			} else {
			}

		}

	}

}
