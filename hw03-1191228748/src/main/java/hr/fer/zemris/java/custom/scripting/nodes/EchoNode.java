/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String output = "{$= ";
		for (Element element : elements) {
			output += element.asText() + " ";
		}
		return output + "$}";
	}
	
	

}
