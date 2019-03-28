/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output
 * dynamically.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class EchoNode extends Node {
	/**
	 * Elements of the node
	 */
	private Element[] elements;

	/**
	 * Constructs {@link EchoNode} from given elements
	 * 
	 * @param elements elements of the node
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * 
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements;
	}


	@Override
	public String toString() {
		String output = "{$= ";
		for (Element element : elements) {
			output += element.toString() + " ";
		}
		return output + "$}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(elements);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EchoNode))
			return false;
		EchoNode other = (EchoNode) obj;
		return Arrays.equals(elements, other.elements);
	}
	
	
	
	

}
