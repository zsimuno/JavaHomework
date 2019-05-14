package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

/**
 * Button for unary operator that will be used when this button is clicked.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JUnaryOperatorButton extends JButton {

	private static final long serialVersionUID = 1L;

	/** Operator used when "Inv" CheckBox is unchecked. */
	DoubleUnaryOperator operator;
	/** Operator used when "Inv" CheckBox is checked. */
	DoubleUnaryOperator invOperator;

	/**
	 * Constructs new button for unary operator that will be used when this button
	 * is clicked.
	 * 
	 * @param operator    operator used when "Inv" CheckBox is unchecked.
	 * @param invOperator operator used when "Inv" CheckBox is checked.
	 * @param text        text on the button.
	 */
	public JUnaryOperatorButton(DoubleUnaryOperator operator, DoubleUnaryOperator invOperator, String text) {
		super(text);
		this.operator = operator;
		this.invOperator = invOperator;
	}

	/**
	 * Constructs new button for unary operator that will be used when this button
	 * is clicked. Sets both operators to the same operator.
	 * 
	 * @param operator operator used when "Inv" CheckBox is unchecked.
	 * @param text     text on the button.
	 */
	public JUnaryOperatorButton(DoubleUnaryOperator operator, String text) {
		this(operator, operator, text);
	}

	/**
	 * @return the operator operator used when "Inv" CheckBox is unchecked.
	 */
	public DoubleUnaryOperator getOperator() {
		return operator;
	}

	/**
	 * @return the invOperator operator used when "Inv" CheckBox is checked.
	 */
	public DoubleUnaryOperator getInvOperator() {
		return invOperator;
	}

	/**
	 * Apply this operator to the given {@code operand}.
	 * 
	 * @param operand operand to which we apply operator to.
	 * @param inv     {@code boolean} that says is "Inv" checked or not.
	 * @return value that we get from applying the operator.
	 */
	public double apply(double operand, boolean inv) {
		if (inv) {
			return invOperator.applyAsDouble(operand);
		} else {
			return operator.applyAsDouble(operand);
		}
	}

}
