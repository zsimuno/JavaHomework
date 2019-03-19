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
	 * 
	 */
	protected Collection() {
		
	}

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
	 * @param value value to be added to the collection
	 */
	public void add(Object value) {

	}

	/**
	 * Returns {@code true} only if the collection contains given {@code value} , as
	 * determined by {@code equals} method
	 * 
	 * @param value value that we are looking if the method contains
	 * @return {@code true} only if the collection contains given {@code value},
	 *         {@code false} if it doesn't
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns {@code true} only if the collection contains given {@code value} , as
	 * determined by {@code equals} method and removes one occurrence of it (in this
	 * class it is not specified which one)
	 * 
	 * @param value value to be removed from the collection
	 * @return {@code true} only if the collection contains given {@code value},
	 *         {@code false} if it doesn't
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * {@code null}
	 * 
	 * @return Object array made from collection content
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
	 * @param other other collection which we add elements from
	 */
	public void addAll(Collection other) {
		// TODO fill javadoc and see if the method shuld be maybe private or public
		/**
		 * @author Zvonimir Šimunović
		 *
		 */
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
