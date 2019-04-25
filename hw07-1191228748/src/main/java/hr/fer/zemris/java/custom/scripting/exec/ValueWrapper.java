/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;

public class ValueWrapper {

	private Object value;

	public ValueWrapper(Object value) {
		this.value = value;
	}

	public void add(Object incValue) {
		this.value = solve(this.value, incValue, (a, b) -> a + b, (a, b) -> a + b);
	}

	public void subtract(Object decValue) {
		this.value = solve(this.value, decValue, (a, b) -> a - b, (a, b) -> a - b);
	}

	public void multiply(Object mulValue) {
		this.value = solve(this.value, mulValue, (a, b) -> a * b, (a, b) -> a * b);
	}

	public void divide(Object divValue) {
		this.value = solve(this.value, divValue, (a, b) -> a / b, (a, b) -> a / b);
	}

	public int numCompare(Object withValue) {
		if (this.value == null && withValue == null) {
			return 0;
		}

		return solve(this.value, withValue, (a, b) -> (a < b) ? -1 : ((a == b) ? 0 : 1),
				(a, b) -> (a < b) ? -1 : ((a == b) ? 0 : 1)).intValue();
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	private Function<Object, Number> tranformer = (value) -> {
		if (value == null)
			return Integer.valueOf(0);

		if (value instanceof Double || value instanceof Integer)
			return (Number) value;

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

	private Number solve(Object value1, Object value2, DoubleBinaryOperator opDouble, IntBinaryOperator opInt) {
		Number value1Number = tranformer.apply(value1);
		Number value2Number = tranformer.apply(value2);

		if (value1Number instanceof Double || value2Number instanceof Double) {
			return opDouble.applyAsDouble(value1Number.doubleValue(), value2Number.doubleValue());
		} else {
			return opInt.applyAsInt(value1Number.intValue(), value2Number.intValue());
		}

	}

}
