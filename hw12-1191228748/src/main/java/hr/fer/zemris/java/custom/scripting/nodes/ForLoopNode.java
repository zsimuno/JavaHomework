/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

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
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

	@Override
	public String toString() {
		return "{$ FOR " + variable.toString() + " " + startExpression.toString() + " " + endExpression.toString() + " "
				+ stepExpression.toString() + " $}";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(endExpression, startExpression, stepExpression, variable);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		return Objects.equals(endExpression, other.endExpression)
				&& Objects.equals(startExpression, other.startExpression)
				&& Objects.equals(stepExpression, other.stepExpression) && Objects.equals(variable, other.variable);
	}
	
	


}
