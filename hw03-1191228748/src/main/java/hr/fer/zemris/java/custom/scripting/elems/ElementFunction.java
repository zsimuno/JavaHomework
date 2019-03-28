/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

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
		return name;
	}

	/**
	 * Returns the name of the function
	 * 
	 * @return the name of the function
	 */
	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return "@" +  name;
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
		if (!(obj instanceof ElementFunction))
			return false;
		ElementFunction other = (ElementFunction) obj;
		return Objects.equals(name, other.name);
	}
	
	

}
