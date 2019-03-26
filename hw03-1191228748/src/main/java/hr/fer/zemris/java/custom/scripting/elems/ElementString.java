/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * TODO
 * @author Zvonimir Šimunović
 *
 */
public class ElementString extends Element {

	/**
	 * 
	 * Value of the element
	 */
	private String value;
	
	

	/**
	 * @param value
	 */
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	String asText() {
		return value;
	}

	/**
	 * Returns the value of the element
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
