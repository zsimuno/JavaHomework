/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;

/**
 * Class that is used to iterate through a collection or
 * process the elements.
 * 
 * @author Zvonimir Šimunović
 * 
 * @param <T> type of the elements that this {@code ElementsGetter} iterates upon
 *
 */
public interface ElementsGetter<T> {
	/**
	 * Checks if there is a next element in the collection
	 * 
	 * @return {@code true} if there is a next element and {@code false} if there is
	 *         not
	 * @throws ConcurrentModificationException if a modification was made to the
	 *                                         collection after the initialization
	 *                                         if this ElementGetter
	 */
	boolean hasNextElement();

	/**
	 * Returns next element in the collection.
	 * 
	 * @return Next element in the collection
	 * @throws NoSuchElementException          when there's no next element
	 * @throws ConcurrentModificationException if a modification was made to the
	 *                                         collection after the initialization
	 *                                         if this ElementGetter
	 */
	T getNextElement();

	/**
	 * Processes (calls {@code p.process}) all the remaining elements in the
	 * collection.
	 * 
	 * @param p processor which will process all the remaining elements in the
	 *          collection
	 */
	default void processRemaining(Processor<T> p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
