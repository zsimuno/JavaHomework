/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents a function element
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ElementFunction extends Element {

	/**
	 * Name of the function
	 */
	private String name;

	/**
	 * Constructs {@link ElementFunction} from it's name
	 * @param name name of the function
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return "@" + name;
	}

	/**
	 * Returns the name of the function
	 * 
	 * @return the name of the function
	 */
	public String getName() {
		return name;
	}

}
