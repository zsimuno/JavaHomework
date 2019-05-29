/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents a constant integer element.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ElementConstantInteger implements Element {

	/**
	 * Value of the integer
	 */
	private int value;

	/**
	 * Constructs {@link ElementConstantInteger} from the given {@code value}
	 * 
	 * @param value value of the integer
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}

	/**
	 * Returns the value of the integer
	 * 
	 * @return the value of the integer
	 */
	public int getValue() {
		return value;
	}


	@Override
	public String toString() {
		return Integer.toString(value);
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
		if (!(obj instanceof ElementConstantInteger))
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		return value == other.value;
	}
	
	
	
	

}
