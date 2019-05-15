package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model of prime numbers.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/** List data listeners for this model. */
	private List<ListDataListener> listListeners = new ArrayList<>();

	/** list of primes */
	List<Integer> primes = new ArrayList<>();

	/**
	 * Constructor.
	 */
	public PrimListModel() {
		primes.add(1);
	}

	/**
	 * Put next prime in the list and notify listeners.
	 */
	public void next() {
		int size = primes.size();
		int previous = primes.get(size - 1);
		do {
			previous++;
		} while (!isPrime(previous));

		primes.add(previous);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, size, size);
		for (ListDataListener l : listListeners) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Checks if the given {@code number} is prime or not.
	 * 
	 * @param number number that is checked.
	 * @return {@code true} if the given number is prime and {@code false}
	 *         otherwise.
	 */
	private boolean isPrime(int number) {
		for (int i = 2; i * i <= number; i++) {
			if (number % i == 0)
				return false;
		}

		return true;
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listListeners.add(l);

	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listListeners.remove(l);

	}
}
