/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class TextNode extends Node {
	/**
	 * text stored in the node
	 */
	private String text;

	/**
	 * Constructs a TextNode from given text
	 * 
	 * @param text text that will be stored in TextNode
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * @return the text that is stored in the node
	 */
	public String getText() {
		return text;
	}

	
}
