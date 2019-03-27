/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents a constant integer element.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ElementConstantInteger extends Element {

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

}
