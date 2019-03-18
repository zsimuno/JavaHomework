/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * @author Zvonimir Šimunović
 *
 */
public class Collection {

	/**
	 * returns {@code true} if collection contains no objects and {@code false}
	 * otherwise
	 * 
	 * @return {@code true} if collection contains no objects and {@code false}
	 *         otherwise
	 */
	public boolean isEmpty() {
		return (this.size() == 0);
	}

	/**
	 * Returns the number of currently stored objects in this collections.
	 * 
	 * @return number of currently stored objects in this collections
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection
	 * 
	 * @param value
	 */
	public void add(Object value) {

	}

	/**
	 * Returns {@code true} only if the collection contains given {@code value} , as
	 * determined by {@code equals} method
	 * 
	 * @param value
	 * @return false
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns {@code true} only if the collection contains given {@code value} , as
	 * determined by {@code equals} method and removes one occurrence of it (in this
	 * class it is not specified which one)
	 * 
	 * @param value
	 * @return false
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * {@code null}
	 * 
	 * @return
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls {@code processor.process(.)} for each element of this
	 * collection. The order in which elements will be sent is undefined in this
	 * class.
	 * 
	 * @param processor
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Method adds into the current collection all elements from the given
	 * collection
	 * 
	 * @param other
	 */
	public void addAll(Collection other) {

		class LocalProcessor extends Processor {

			/**
			 * @param value
			 */
			public void process(Object value) {
				add(value);
			}

		}

		LocalProcessor processor = new LocalProcessor();

		other.forEach(processor);
	}

	/**
	 * Removes all elements from this collection
	 */
	public void clear() {

	}

}
