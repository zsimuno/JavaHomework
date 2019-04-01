/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents a collection
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface Collection<T> {

	/**
	 * returns {@code true} if collection contains no objects and {@code false}
	 * otherwise
	 * 
	 * @return {@code true} if collection contains no objects and {@code false}
	 *         otherwise
	 */
	default boolean isEmpty() {
		return (this.size() == 0);
	}

	/**
	 * Returns the number of currently stored objects in this collections.
	 * 
	 * @return number of currently stored objects in this collections
	 * 
	 */
	int size();

	/**
	 * Adds the given object into this collection. Adds the object to the end.
	 * 
	 * @param value value to be added to the collection
	 */
	void add(T value);

	/**
	 * Returns {@code true} only if the collection contains given {@code value} , as
	 * determined by {@code equals} method
	 * 
	 * @param value value that we are looking if the method contains
	 * @return {@code true} only if the collection contains given {@code value},
	 *         {@code false} if it doesn't
	 */
	boolean contains(Object value);

	/**
	 * Returns {@code true} only if the collection contains given {@code value} , as
	 * determined by {@code equals} method and removes one occurrence of it (in this
	 * class it is not specified which one)
	 * 
	 * @param value value to be removed from the collection
	 * @return {@code true} only if the collection contains given {@code value},
	 *         {@code false} if it doesn't
	 */
	boolean remove(Object value);

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * {@code null}
	 * 
	 * @return Object array made from collection content
	 */
	Object[] toArray();

	/**
	 * Method calls {@code processor.process(.)} for each element of this
	 * collection. The order in which elements will be sent is undefined in this
	 * class.
	 * 
	 * @param processor processor which method process will be called for each
	 *                  element of this collection
	 */
	default void forEach(Processor<T> processor) {
		ElementsGetter<T> getter = this.createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}

	/**
	 * Method adds into the current collection all elements from the given
	 * collection
	 * 
	 * @param other other collection which we add elements from
	 */
	default void addAll(Collection<T> other) {
		Processor<T> processor = this::add;

		other.forEach(processor);
	}

	/**
	 * Removes all elements from this collection
	 */
	void clear();

	/**
	 * Creates and returns an {@link ElementsGetter} object that iterates through
	 * this collection.
	 * 
	 * @return {@link ElementsGetter} object that iterates through this collection
	 */
	ElementsGetter<T> createElementsGetter();

	/**
	 * @param col
	 * @param tester
	 */
	default void addAllSatisfying(Collection<T> col, Tester tester) {
		ElementsGetter<T> getter = col.createElementsGetter();
		while (getter.hasNextElement()) {
			T nextElement = getter.getNextElement();
			if (tester.test(nextElement)) {
				this.add(nextElement);
			}
		}
	}

}
