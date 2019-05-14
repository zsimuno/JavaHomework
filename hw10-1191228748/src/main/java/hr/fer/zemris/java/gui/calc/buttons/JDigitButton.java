package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

/**
 * Button for a number that will be used when this button is clicked.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JDigitButton extends JButton {

	/** */
	private static final long serialVersionUID = 1L;
	private int digit;

	/**
	 * Constructs a button that has the given number on it.
	 * 
	 * @param number number of the button.
	 */
	public JDigitButton(int digit) {
		super(Integer.toString(Double.valueOf(digit).intValue()));
		this.digit = digit;
	}

	/**
	 * @return the number of the button.
	 */
	public int getDigit() {
		return digit;
	}

}
