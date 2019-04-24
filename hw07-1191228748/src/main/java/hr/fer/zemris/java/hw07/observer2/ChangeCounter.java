package hr.fer.zemris.java.hw07.observer2;

/**
 * Counts (and writes to the standard output) the number of times the value
 * stored has been changed since this observer’s registration.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Counter that counts the number of times the value stored has been changed
	 * since this observer’s registration
	 */
	private int counter = 0;

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);

	}

}
