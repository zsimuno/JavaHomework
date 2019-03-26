/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * TODO
 * @author Zvonimir Šimunović
 *
 */
public class ElementVariable extends Element {
	
	/**
	 * 
	 * name of the element
	 */
	private String name;
	
	

	/**
	 * @param name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	@Override
	String asText() {
		return name;
	}

	/**
	 * Returns the name of the element
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
}
