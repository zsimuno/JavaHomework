/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents a varible
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ElementVariable extends Element {

	/**
	 * name of the variable
	 */
	private String name;

	/**
	 * Constructs {@link ElementVariable} from it's name
	 * @param name name of the variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	@Override
	String asText() {
		return name;
	}

	/**
	 * Returns the name of the variable
	 * 
	 * @return the name of the variable
	 */
	public String getName() {
		return name;
	}

}
