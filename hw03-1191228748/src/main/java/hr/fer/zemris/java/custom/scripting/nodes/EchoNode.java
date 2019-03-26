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
	 * TODO
	 */
	private Element[] elements;

	
	
	/**
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}



	/**
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	

}
