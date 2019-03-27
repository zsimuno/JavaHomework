/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

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
		return "\"" + value + "\"";
	}

	/**
	 * Returns the value of the string element
	 * 
	 * @return the value of the string element
	 */
	public String getValue() {
		return value;
	}

}
