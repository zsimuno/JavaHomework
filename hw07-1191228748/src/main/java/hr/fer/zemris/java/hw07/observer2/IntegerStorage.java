package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that is the subject in observer pattern. Stores one integer and the
 * list of observers.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class IntegerStorage {
	/**
	 * Integer value stored in this class.
	 */
	private int value;
	/**
	 * List of all observers that currently observe this object.
	 */
	private List<IntegerStorageObserver> observers = new ArrayList<>(); // use ArrayList here!!!

	/**
	 * Constructs and {@code IntegerStorage} with the given integer value.
	 * 
	 * @param initialValue integer value to be stored here.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds the observer in observers if not already there.
	 * 
	 * @param observer observer that is added.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers = new ArrayList<>(observers);
			observers.add(observer);
		}
	}

	/**
	 * Remove the observer from observers if present.
	 * 
	 * @param observer observer that is to be removed.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if(observers.contains(observer)) {
			observers = new ArrayList<>(observers);
			observers.remove(observer);
		}
	}

	/**
	 * Removes all observers from observers list.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Gets the integer value stored here.
	 * 
	 * @return integer value stored here.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the integer value stored here.
	 * 
	 * @param value integer value that will now be stored.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			
			// The IntegerStorageChange that will be passed down to observers
			IntegerStorageChange istorageChange = new IntegerStorageChange(this, this.value, value);
			
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(istorageChange);
				}
			}
		}
	}

}
