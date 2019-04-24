/**
 * 
 */
package hr.fer.zemris.java.hw07.observer2;

/**
 * Writes a square of the integer stored in the IntegerStorage to the standard
 * output (but the stored integer itself is not modified).
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int currentValue = istorage.getNewValue();
		System.out.println("Provided new value: " + currentValue + ", square is " + (currentValue * currentValue));
	}

}
