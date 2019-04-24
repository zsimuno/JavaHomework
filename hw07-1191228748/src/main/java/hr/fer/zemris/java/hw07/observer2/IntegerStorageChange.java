/**
 * 
 */
package hr.fer.zemris.java.hw07.observer2;

/**
 * Class that is passed down to the observers that contains the data about the
 * subject and state before the subject.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class IntegerStorageChange {

	/**
	 * A reference to the IntegerStorage.
	 */
	private IntegerStorage storage;

	/**
	 * Value of stored integer before the change has occurred.
	 */
	private int valueBeforeChange;

	/**
	 * The new value of currently stored integer.
	 */
	private int newValue;

	/**
	 * Constructs a new {@code IntegerStorageChange} from given values.
	 * 
	 * @param storage           reference to the IntegerStorage.
	 * @param valueBeforeChange Value of stored integer before the change has
	 *                          occurred.
	 * @param newValue          new value of currently stored integer.
	 */
	public IntegerStorageChange(IntegerStorage storage, int valueBeforeChange, int newValue) {
		this.storage = storage;
		this.valueBeforeChange = valueBeforeChange;
		this.newValue = newValue;
	}

	/**
	 * Returns a reference to the IntegerStorage.
	 * 
	 * @return reference to the IntegerStorage
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	/**
	 * Returns the value of stored integer before the change has occurred.
	 * 
	 * @return the value of stored integer before the change has occurred.
	 */
	public int getValueBeforeChange() {
		return valueBeforeChange;
	}

	/**
	 * Returns the new value of currently stored integer.
	 * 
	 * @return the new value of currently stored integer.
	 */
	public int getNewValue() {
		return newValue;
	}

}
