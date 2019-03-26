/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * TODO
 * @author Zvonimir Šimunović
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Value of the element
	 */
	private double value;

	/**
	 * @param value
	 */
	public ElementConstantDouble(int value) {
		this.value = value;
	}
	
	@Override
	String asText() {
		return Double.toString(value);
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

}
