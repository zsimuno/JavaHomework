package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.JBinaryOperatorButton;
import hr.fer.zemris.java.gui.calc.buttons.JDigitButton;
import hr.fer.zemris.java.gui.calc.buttons.JUnaryOperatorButton;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

// TODO +/- on non editable?
// TODO what to do on exceptions?
/**
 * Calculator program that shows a calculator that can calculate.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Calculator extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		CalcModelImpl calculator = new CalcModelImpl();

		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));

		JLabel screen = new JLabel(calculator.toString());
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setFont(screen.getFont().deriveFont(30f));
		cp.add(screen, new RCPosition(1, 1));

		CalcValueListener screenListener = a -> {
			screen.setText(a.toString());
		};
		calculator.addCalcValueListener(screenListener);

		JCheckBox inv = new JCheckBox("Inv", false);
		cp.add(inv, new RCPosition(5, 7));

		addNumberButtons(calculator);

		addBinaryOperatorButtons(calculator, inv);

		addUnaryOperatoButtons(calculator, inv);

		addOtherButtons(calculator);

	}

	/**
	 * Adds buttons that represent numerical inputs.
	 * 
	 * @param calculator calculator model that we use for calculations.
	 */
	private void addNumberButtons(CalcModelImpl calculator) {
		Container cp = getContentPane();
		// TODO check when editable
		ActionListener number = a -> {
			JDigitButton b = (JDigitButton) a.getSource();
			if (!calculator.isEditable()) {
				calculator.clear();
			}
			calculator.insertDigit(b.getDigit());
		};

		JDigitButton[] buttons = new JDigitButton[10];
		for (int i = 0; i < 10; i++) {
			buttons[i] = new JDigitButton(i);
			buttons[i].addActionListener(number);
		}
		cp.add(buttons[0], new RCPosition(5, 3));
		cp.add(buttons[1], new RCPosition(4, 3));
		cp.add(buttons[2], new RCPosition(4, 4));
		cp.add(buttons[3], new RCPosition(4, 5));
		cp.add(buttons[4], new RCPosition(3, 3));
		cp.add(buttons[5], new RCPosition(3, 4));
		cp.add(buttons[6], new RCPosition(3, 5));
		cp.add(buttons[7], new RCPosition(2, 3));
		cp.add(buttons[8], new RCPosition(2, 4));
		cp.add(buttons[9], new RCPosition(2, 5));

	}

	/**
	 * Adds buttons that represent binary operators.
	 * 
	 * @param calculator calculator model that we use for calculations.
	 * @param inv        checkbox that says if we invert operations or not.
	 */
	private void addBinaryOperatorButtons(CalcModelImpl calculator, JCheckBox inv) {

		ActionListener binary = a -> {
			JBinaryOperatorButton b = (JBinaryOperatorButton) a.getSource();
			Double activeOperand;
			if (calculator.isActiveOperandSet()) {
				DoubleBinaryOperator op = calculator.getPendingBinaryOperation();
				Double operand1 = calculator.getActiveOperand();
				Double operand2 = calculator.getValue();
				activeOperand = op.applyAsDouble(operand1, operand2);
			} else {
				activeOperand = calculator.getValue();
			}
			calculator.setActiveOperand(activeOperand);
			if (!inv.isSelected()) {
				calculator.setPendingBinaryOperation(b.getOperator());
			} else {
				calculator.setPendingBinaryOperation(b.getInvOperator());
			}
			calculator.setValue(activeOperand);

		};

		Container cp = getContentPane();
		cp.add(binaryOp((a, b) -> a / b, "/", binary), new RCPosition(2, 6));
		cp.add(binaryOp((a, b) -> a * b, "*", binary), new RCPosition(3, 6));
		cp.add(binaryOp((a, b) -> a - b, "-", binary), new RCPosition(4, 6));
		cp.add(binaryOp((a, b) -> a + b, "+", binary), new RCPosition(5, 6));

		cp.add(binaryOp((a, b) -> Math.pow(a, b), (a, b) -> Math.pow(a, 1.0 / b), "x^n", binary), new RCPosition(5, 1));

	}

	/**
	 * Creates a new button that is a binary operator with the given operator and
	 * other operator that will be used when "inv" is checked and with a given
	 * listener for the button.
	 * 
	 * @param operator    operator used when the created button is clicked.
	 * @param invOperator operator used when the created button is clicked and "inv"
	 *                    is checked.
	 * @param text        text on the button.
	 * @param listener    listener for the button.
	 * @return new button that is a binary operator.
	 */
	private JBinaryOperatorButton binaryOp(DoubleBinaryOperator operator, DoubleBinaryOperator invOperator, String text,
			ActionListener listener) {
		JBinaryOperatorButton op = new JBinaryOperatorButton(operator, invOperator, text);
		op.addActionListener(listener);
		return op;
	}

	/**
	 * Creates a new button that is a binary operator with the given operator
	 * (doesn't change on "inv" checked) and with a given listener for the button.
	 * 
	 * @param operator operator used when the created button is clicked.
	 * @param text     text on the button.
	 * @param listener listener for the button.
	 * @return new button that is a binary operator.
	 */
	private JBinaryOperatorButton binaryOp(DoubleBinaryOperator operator, String text, ActionListener listener) {
		return binaryOp(operator, operator, text, listener);
	}

	/**
	 * Adds buttons that represent unary operators.
	 * 
	 * @param calculator calculator model that we use for calculations.
	 * @param inv        checkbox that says if we invert operations or not.
	 */
	private void addUnaryOperatoButtons(CalcModelImpl calculator, JCheckBox inv) {
		ActionListener unary = a -> {
			JUnaryOperatorButton b = (JUnaryOperatorButton) a.getSource();
			calculator.setValue(b.apply(calculator.getValue(), inv.isSelected()));
		};

		Container cp = getContentPane();
		cp.add(unaryOp((a) -> 1.0 / a, "1/x", unary), new RCPosition(2, 1));
		cp.add(unaryOp(Math::log10, (a) -> Math.pow(10, a), "log", unary), new RCPosition(3, 1));
		cp.add(unaryOp(Math::log, Math::exp, "ln", unary), new RCPosition(4, 1));

		cp.add(unaryOp(Math::sin, Math::asin, "sin", unary), new RCPosition(2, 2));
		cp.add(unaryOp(Math::cos, Math::acos, "cos", unary), new RCPosition(3, 2));
		cp.add(unaryOp(Math::tan, Math::atan, "tan", unary), new RCPosition(4, 2));
		cp.add(unaryOp((a) -> 1.0 / Math.tan(a), (a) -> Math.PI / 2 - Math.atan(a), "ctg", unary),
				new RCPosition(5, 2));

	}

	/**
	 * Creates a new button that is a unary operator with the given operator and
	 * other operator that will be used when "inv" is checked and with a given
	 * listener for the button.
	 * 
	 * @param operator    operator used when the created button is clicked.
	 * @param invOperator operator used when the created button is clicked and "inv"
	 *                    is checked.
	 * @param text        text on the button.
	 * @param listener    listener for the button.
	 * @return new button that is a unary operator.
	 */
	private JUnaryOperatorButton unaryOp(DoubleUnaryOperator operator, DoubleUnaryOperator invOperator, String text,
			ActionListener listener) {
		JUnaryOperatorButton op = new JUnaryOperatorButton(operator, invOperator, text);
		op.addActionListener(listener);
		return op;
	}

	/**
	 * Creates a new button that is a unary operator with the given operator
	 * (doesn't change on "inv" checked) and with a given listener for the button.
	 * 
	 * @param operator operator used when the created button is clicked.
	 * @param text     text on the button.
	 * @param listener listener for the button.
	 * @return new button that is a unary operator.
	 */
	private JUnaryOperatorButton unaryOp(DoubleUnaryOperator operator, String text, ActionListener listener) {
		return unaryOp(operator, operator, text, listener);
	}

	/**
	 * Adds various buttons such as equals, dot etc. to the container.
	 * 
	 * @param calculator calculator model that we use for calculations.
	 */
	private void addOtherButtons(CalcModelImpl calculator) {
		Container cp = getContentPane();
		JButton equals = new JButton("=");
		equals.addActionListener(a -> {
			if (calculator.isActiveOperandSet()) {
				DoubleBinaryOperator op = calculator.getPendingBinaryOperation();
				Double operand1 = calculator.getActiveOperand();
				Double operand2 = calculator.getValue();
				calculator.setValue(op.applyAsDouble(operand1, operand2));
				calculator.clearActiveOperand();
			}
		});
		cp.add(equals, new RCPosition(1, 6));

		JButton swapSign = new JButton("+/-");
		swapSign.addActionListener(a -> {
			calculator.swapSign();
		});
		cp.add(swapSign, new RCPosition(5, 4));

		JButton dot = new JButton(".");
		dot.addActionListener(a -> {
			calculator.insertDecimalPoint();
		});
		cp.add(dot, new RCPosition(5, 5));

		JButton clr = new JButton("clr");
		clr.addActionListener(a -> {
			calculator.clear();
		});
		cp.add(clr, new RCPosition(1, 7));

		JButton res = new JButton("res");
		res.addActionListener(a -> {
			calculator.clearAll();
		});
		cp.add(res, new RCPosition(2, 7));

		Stack<Double> stack = new Stack<>();

		JButton push = new JButton("push");
		push.addActionListener(a -> {
			stack.push(calculator.getValue());
		});
		cp.add(push, new RCPosition(3, 7));

		JButton pop = new JButton("pop");
		pop.addActionListener(a -> {
			// TODO Check stack
			calculator.setValue(stack.pop());
		});
		cp.add(pop, new RCPosition(4, 7));

	}

	/**
	 * Method that starts the program.
	 * 
	 * @param args command line arguments (not used here).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
