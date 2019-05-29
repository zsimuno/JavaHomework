/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing an entire document.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
