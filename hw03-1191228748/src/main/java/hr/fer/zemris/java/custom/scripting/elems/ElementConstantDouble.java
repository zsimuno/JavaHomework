/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

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
	public ElementConstantDouble(int value) {
		this.value = value;
	}
	
	@Override
	String asText() {
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

}
