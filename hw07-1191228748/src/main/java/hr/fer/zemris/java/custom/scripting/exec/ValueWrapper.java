/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * Value wrapper that wraps an object and contains arithmetic and comparing
 * operations for applicable objects.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ValueWrapper {

	/**
	 * Value wrapped in this wrapper.
	 */
	private Object value;

	/**
	 * Constructs {@link ValueWrapper} object that wraps given value.
	 * 
	 * @param value initial value to be wrapped.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Adds value stored in this wrapper to given one if possible. Stores the value
	 * in this {@link ValueWrapper}.
	 * 
	 * @param incValue value to be added to the value stored in this wrapper.
	 */
	public void add(Object incValue) {
		this.value = solve(this.value, incValue, (a, b) -> a + b, (a, b) -> a + b);
	}

	/**
	 * Subtracts given value from the value stored in this wrapper if possible.
	 * Stores the value in this {@link ValueWrapper}.
	 * 
	 * @param decValue value to be subtracted from the value stored in this wrapper.
	 */
	public void subtract(Object decValue) {
		this.value = solve(this.value, decValue, (a, b) -> a - b, (a, b) -> a - b);
	}

	/**
	 * Multiplies given value and the value stored in this wrapper if possible.
	 * Stores the value in this {@link ValueWrapper}.
	 * 
	 * @param mulValue value to be multiplied with the value stored in this wrapper.
	 */
	public void multiply(Object mulValue) {
		this.value = solve(this.value, mulValue, (a, b) -> a * b, (a, b) -> a * b);
	}

	/**
	 * Divides given value from the value stored in this wrapper if possible. Stores
	 * the value in this {@link ValueWrapper}.
	 * 
	 * @param divValue value to be divided from the value stored in this wrapper.
	 */
	public void divide(Object divValue) {
		this.value = solve(this.value, divValue, (a, b) -> a / b, (a, b) -> a / b);
	}

	/**
	 * Compares given value and value stored in this wrapper.
	 * 
	 * @param withValue value to be compared to the value stored in this wrapper.
	 * @return the value {@code 0} if {@code withValue} is equal to value wrapped in
	 *         this wrapper; a value less than {@code 0} if this value wrapped in
	 *         this wrapper is less than {@code withValue}; and a value greater than
	 *         {@code 0} if this {@code withValue} is greater than value wrapped in
	 *         this wrapper.
	 */
	public int numCompare(Object withValue) {
		if (this.value == null && withValue == null) {
			return 0;
		}

		return solve(this.value, withValue, (a, b) -> Double.valueOf(a.compareTo(b)), (a, b) -> a.compareTo(b))
				.intValue();
	}

	/**
	 * Returns the value wrapped in this wrapper.
	 * 
	 * @return the value wrapped in this wrapper.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value wrapped in this wrapper.
	 * 
	 * @param value value to be wrapped in this wrapper.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Function that converts the given object in an object that can be used in
	 * operations given by {@link ValueWrapper} ({@code Double} and {@code Integer})
	 */
	private Function<Object, Number> tranformer = (value) -> {
		if (value == null)
			return Integer.valueOf(0);

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

		if (value instanceof Double || value instanceof Integer)
			return (Number) value;

		throw new RuntimeException("Input is not supported!");
	};

	/**
	 * Solves the given operation. Needs operation for {@code Double} objects and
	 * {@code Integer} objects.
	 * 
	 * @param value1          first operand.
	 * @param value2          second operand.
	 * @param DoubleOperator  operation for {@code Double} objects.
	 * @param IntegerOperator operation for {@code Integer} objects.
	 * @return Result of the given operation, a {@code Number} object that can be
	 *         {@code Double} or {@code Integer} depending on the operands.
	 * @throws RuntimeException if one of the given operands is an invalid input.
	 */
	private Number solve(Object value1, Object value2, BinaryOperator<Double> DoubleOperator,
			BinaryOperator<Integer> IntegerOperator) {
		Number value1Number = tranformer.apply(value1);
		Number value2Number = tranformer.apply(value2);

		if (value1Number instanceof Double || value2Number instanceof Double) {
			return DoubleOperator.apply(value1Number.doubleValue(), value2Number.doubleValue());
		} else {
			return IntegerOperator.apply(value1Number.intValue(), value2Number.intValue());
		}

	}

}
