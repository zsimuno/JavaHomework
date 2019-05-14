package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * Button for binary operator that will be used when this button is clicked.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JBinaryOperatorButton extends JButton {

	private static final long serialVersionUID = 1L;

	/** Binary operator applied to values. */
	DoubleBinaryOperator operator;
	/** Binary operator applied to values when "Inv" is checked. */
	DoubleBinaryOperator invOperator;

	/**
	 * Constructs new button for binary operators that will be used when this button
	 * is clicked.
	 * 
	 * @param operator    binary operator that will be used when this button is
	 *                    clicked.
	 * @param invOperator operator that will be used when this button is clicked and
	 *                    when "Inv" is checked.
	 * @param text        text on the button.
	 */
	public JBinaryOperatorButton(DoubleBinaryOperator operator, DoubleBinaryOperator invOperator, String text) {
		super(text);
		this.operator = operator;
		this.invOperator = invOperator;
	}

	/**
	 * Constructs new button for binary operator that will be used when this button
	 * is clicked. Sets "Inv" operator to the same one.
	 * 
	 * @param operator binary operator that will be used when this button is
	 *                 clicked.
	 * @param text     text on the button.
	 */
	public JBinaryOperatorButton(DoubleBinaryOperator operator, String text) {
		this(operator, operator, text);
	}

	/**
	 * @return the binary operator that will be used when this button is clicked.
	 */
	public DoubleBinaryOperator getOperator() {
		return operator;
	}

	/**
	 * @return the invOperator operator that will be used when this button is
	 *         clicked and when "Inv" is checked.
	 */
	public DoubleBinaryOperator getInvOperator() {
		return invOperator;
	}

}
