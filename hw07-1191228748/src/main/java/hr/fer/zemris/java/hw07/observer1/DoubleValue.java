package hr.fer.zemris.java.hw07.observer1;

/**
 * writes to the standard output double value (i.e. “value * 2”) of the current
 * value which is stored in subject, but only first n times since its
 * registration with the subject (n is given in constructor); after writing the
 * double value for the n-th time, the observer automatically de-registers
 * itself from the subject.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * How many times the value has changed
	 */
	private int counter = 0;

	/**
	 * Maximal number of call of this observer
	 */
	private int maxNumberOfCalls;

	/**
	 * Counstructs an {@code DoubleValue} with the given number of maximum calls
	 * 
	 * @param n number of maximum calls
	 */
	public DoubleValue(int n) {
		maxNumberOfCalls = n;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Double value: " + (istorage.getValue() * 2));
		
		if(counter == maxNumberOfCalls) {
			istorage.removeObserver(this);
		}
	}

}
