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
	 * Represents a variable that will change in the loop
	 */
	private ElementVariable variable;
	/**
	 * Starting value of the {@code variable} in the loop
	 */
	private Element startExpression;
	/**
	 * Loop will stop when {@code variable} reaches this value
	 */
	private Element endExpression;
	/**
	 * Value of {@code variable} will change by this value
	 */
	private Element stepExpression; // can be null

	/**
	 * Constructs a {@link ForLoopNode} from given values
	 * 
	 * @param variable        variable that will change in the loop
	 * @param startExpression Starting value of the {@code variable} in the loop
	 * @param endExpression   Loop will stop when {@code variable} reaches this
	 *                        value
	 * @param stepExpression  Value of {@code variable} will change by this value
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{$ FOR " + variable.asText() + " " + startExpression.asText() + " " + endExpression.asText() + " "
				+ stepExpression.asText() + " $}";
	}

}
