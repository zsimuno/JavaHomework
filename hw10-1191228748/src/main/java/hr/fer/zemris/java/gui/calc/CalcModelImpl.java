package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Implementation of a model of a calculator.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CalcModelImpl implements CalcModel {

	/** Is the model editable or not. */
	private boolean editable = true;
	/** Is the number in the calculator positive. */
	private boolean positiveValue = true;
	/** Number that is shown on the screen. */
	private String input = "";
	/** Double value of the number on the screen. */
	private double value = 0.0;
	/** Current active operand used in binary operation. */
	private Double activeOperand = null;
	/** Pending binary operation that is waiting for a second operand. */
	private DoubleBinaryOperator pendingOperation = null;

	/** Contains all listeners that observe this model. */
	private List<CalcValueListener> listeners = new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(Objects.requireNonNull(l));
	}

	/**
	 * Notifies all {@link CalcValueListener} object that observe this object.
	 */
	private void notifyListeners() {
		for (CalcValueListener calcValueListener : listeners) {
			calcValueListener.valueChanged(this);
		}
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		positiveValue = value >= 0;
		input = Double.toString(Math.abs(value));
		editable = false;
		notifyListeners();

	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		editable = true;
		positiveValue = true;
		input = "";
		value = 0.0;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clear();
		activeOperand = null;
		pendingOperation = null;
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		throwIfNotEditable();

		positiveValue = !positiveValue;
		value *= (-1);
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		throwIfNotEditable();

		if (input.isBlank() || input.contains("."))
			throw new CalculatorInputException("Cannot input dot!");

		try {
			value = Double.parseDouble((positiveValue ? "" : "-") + input + ".");
		} catch (Exception e) {
			throw new CalculatorInputException("Number too big for the calculator!");
		}

		input += ".";
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		throwIfNotEditable();

		if (digit < 0 || digit > 9)
			throw new IllegalArgumentException("Given digit must be numbers 0-9!");

		if (input.contentEquals("0") && digit == 0)
			return;

		try {
			value = Double.parseDouble((positiveValue ? "" : "-") + input + Integer.toString(digit));
		} catch (Exception e) {
			throw new CalculatorInputException("String does not containa parsable double!");
		}

		if (Double.isInfinite(value))
			throw new CalculatorInputException("Number too big!");

		if (input.contentEquals("0")) {
			input = "";
		}
		input += Integer.toString(digit);
		notifyListeners();
	}

	/**
	 * Throws exception if the calculator is not editable
	 * 
	 * @throws CalculatorInputException if the calculator is not editable.
	 */
	private void throwIfNotEditable() {
		if (!editable)
			throw new CalculatorInputException("calculator is not editable!");
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet())
			throw new IllegalStateException("Active operand not set!");

		return activeOperand.doubleValue();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;

	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;

	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;

	}

	@Override
	public String toString() {
		if (input.isBlank())
			return (positiveValue ? "0" : "-0");

		return (positiveValue ? "" : "-") + input;
	}

}
