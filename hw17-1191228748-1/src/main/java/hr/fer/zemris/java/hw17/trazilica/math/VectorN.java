package hr.fer.zemris.java.hw17.trazilica.math;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents an n-dimensional vector. Size is changing depending on values
 * added.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class VectorN implements Iterable<Double> {

	/** Vector coordinates. */
	List<Double> values = new ArrayList<>();

	/** Precision of the equals method. */
	private static final double precision = 1e-6;

	/**
	 * Adds the given {@code value} to this vector.
	 * 
	 * @param value value to be added.
	 */
	public void add(Double value) {
		values.add(value);
	}

	/**
	 * Returns size of the vector.
	 * 
	 * @return size of the vector.
	 */
	public int size() {
		return values.size();
	}

	/**
	 * Norm of this vector.
	 * 
	 * @return Norm of this vector.
	 */
	public double norm() {
		double sum = 0;
		for (Double val : values) {
			sum += val * val;
		}
		return Math.sqrt(sum);
	}

	/**
	 * This vector normalized.
	 * 
	 * @return This vector normalized.
	 */
	public VectorN normalized() {
		double norm = norm();
		VectorN n = new VectorN();
		for (Double value : values) {
			n.add(value / norm);
		}
		return n;
	}

	/**
	 * Creates and returns a new {@link VectorN} object that is dot product of
	 * {@code this} and {@code VectorN}.
	 * 
	 * @param other other {@code VectorN}.
	 * @return {@link VectorN} object that is dot product of {@code this} and
	 *         {@code VectorN}.
	 * @throws IllegalArgumentException if vectors are not the same size.
	 */
	public double dot(VectorN other) {
		if (this.size() != other.size()) {
			throw new IllegalArgumentException("Vectors must be the same size!");
		}
		double dot = 0;
		for (int i = 0; i < values.size(); i++) {
			dot += this.values.get(i) * other.values.get(i);
		}
		return dot;
	}

	/**
	 * Cosine of the angle between {@code this} and {@code other}.
	 * 
	 * @param other other {@code VectorN}.
	 * @return Cosine of the angle between {@code this} and {@code other}.
	 * @throws IllegalArgumentException if vectors are not the same size.
	 */
	public double cosAngle(VectorN other) {
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Returns the element and the given {@code index}.
	 * 
	 * @param index index of the element
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *                                   ({@code index < 0 || index >= size()})
	 */
	public double get(int index) {
		Objects.checkIndex(index, values.size());
		return values.get(index);
	}

	/**
	 * Returns a list of values in the vector.
	 * 
	 * @return values as {@link List}
	 */
	public List<Double> values() {
		return values;
	}

	@Override
	public int hashCode() {
		return Objects.hash(values);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VectorN))
			return false;
		VectorN other = (VectorN) obj;
		if (this.size() != other.size()) {
			return false;
		}
		for (int i = 0; i < values.size(); i++) {
			if (Math.abs(this.values.get(i) - other.values.get(i)) > precision)
				return false;
		}
		return true;
	}

	@Override
	public Iterator<Double> iterator() {
		return values.iterator();
	}

}
