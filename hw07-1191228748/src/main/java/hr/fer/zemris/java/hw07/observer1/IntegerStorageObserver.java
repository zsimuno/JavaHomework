/**
 * 
 */
package hr.fer.zemris.java.hw07.observer1;

/**
 * 
 * Interface that represents one observer of {@link IntegerStorage}
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Method that determines what will this observer do when the value of the
	 * subject changes.
	 * 
	 * @param istorage subject that is observed.
	 */
	public void valueChanged(IntegerStorage istorage);
}
