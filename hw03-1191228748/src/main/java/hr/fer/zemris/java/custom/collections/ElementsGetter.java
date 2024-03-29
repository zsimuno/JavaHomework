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
 */
public interface ElementsGetter {
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
	Object getNextElement();

	/**
	 * Processes (calls {@code p.process}) all the remaining elements in the
	 * collection.
	 * 
	 * @param p processor which will process all the remaining elements in the
	 *          collection
	 */
	default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
