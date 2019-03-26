/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * TODO
 * @author Zvonimir Šimunović
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Value of the element
	 */
	private int value;

	/**
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	@Override
	String asText() {
		return Integer.toString(value);
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	

}
