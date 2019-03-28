/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

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


	@Override
	public String toString() {
		return text.replace("\\", "\\\\").replace("{", "\\{");
	}
	
	
	/**
	 * Returns string representation but without escaping characters
	 * @return string representation but without escaping characters
	 */
	public String toStringWithoutEscaping() {
		return  text.replace("\n", "\\n").replace("\t", "\\t").replace("\r", "\\r");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(text);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TextNode))
			return false;
		TextNode other = (TextNode) obj;
		return text.equals(other.text);
	}


	

	
}
