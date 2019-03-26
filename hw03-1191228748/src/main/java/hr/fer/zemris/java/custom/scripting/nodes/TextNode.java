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
	 * TODO docs
	 */
	private String text;

	/**
	 * @param text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	
}
