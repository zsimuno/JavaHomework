/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

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
	public String asText() {
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


	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementVariable))
			return false;
		ElementVariable other = (ElementVariable) obj;
		return Objects.equals(name, other.name);
	}
	
	
	
	

}
