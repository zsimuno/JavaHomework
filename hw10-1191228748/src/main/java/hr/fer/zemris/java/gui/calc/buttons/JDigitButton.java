package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;

/**
 * Button for a number that will be used when this button is clicked.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JDigitButton extends JButton {

	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a button that has the given number on it.
	 * 
	 * @param number number of the button.
	 */
	public JDigitButton(int digit, CalcModelImpl calculator) {
		super(Integer.toString(Double.valueOf(digit).intValue()));
		
		this.addActionListener( a -> {
			if (!calculator.isEditable()) {
				calculator.clear();
			}
			calculator.insertDigit(digit);
		});
		this.setFont(this.getFont().deriveFont(30f));
	}


}
