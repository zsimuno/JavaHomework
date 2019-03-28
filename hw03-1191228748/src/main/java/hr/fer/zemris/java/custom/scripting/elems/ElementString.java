/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents a string element
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ElementString extends Element {

	/**
	 * Value of the string
	 */
	private String value;

	/**
	 * Constructs {@link ElementString} from the given {@code value}
	 * @param value value of the string
	 */
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return value;
	}

	/**
	 * Returns the value of the string element
	 * 
	 * @return the value of the string element
	 */
	public String getValue() {
		return value;
	}
	

	@Override
	public String toString() {
		return "\"" + value + "\"";
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
		if (!(obj instanceof ElementString))
			return false;
		ElementString other = (ElementString) obj;
		return Objects.equals(value, other.value);
	}
	
	


}
