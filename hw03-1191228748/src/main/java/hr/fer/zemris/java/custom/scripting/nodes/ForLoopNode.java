/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ForLoopNode extends Node {
	/**
	 * TODO
	 */
	private ElementVariable variable;
	/**
	 * 
	 */
	private Element startExpression;
	/**
	 * 
	 */
	private Element endExpression;
	/**
	 * 
	 */
	private Element stepExpression; // can be null 
	/**
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	/**
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	/**
	 * @return the startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	/**
	 * @return the endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	/**
	 * @return the stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	
}
