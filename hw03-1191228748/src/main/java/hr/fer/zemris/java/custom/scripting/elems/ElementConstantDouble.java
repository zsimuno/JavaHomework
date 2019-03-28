/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents a constant double element.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Value of the double
	 */
	private double value;

	/**
	 * Constructs {@link ElementConstantDouble} from the given {@code value}
	 * 
	 * @param value value of the double
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Double.toString(value);
	}

	/**
	 * Returns the value of the double
	 * 
	 * @return the value of the double
	 */
	public double getValue() {
		return value;
	}
	

	@Override
	public String toString() {
		return Double.toString(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		return Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}
	
	

}
