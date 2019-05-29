package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents a visitor that visits nodes used in {@code SmartScript} program
 * compiling.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface INodeVisitor {
	/**
	 * Visits a textual node.
	 * 
	 * @param node node that is visited.
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visits a node representing a for loop.
	 * 
	 * @param node node that is visited.
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visits an echo node.
	 * 
	 * @param node node that is visited.
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visits a document node.
	 * 
	 * @param node node that is visited.
	 */
	public void visitDocumentNode(DocumentNode node);
}
