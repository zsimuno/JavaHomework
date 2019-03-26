/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Interface that represents a list of elements.
 * 
 * @author Zvonimir Šimunović
 * 
 */
public interface List extends Collection {
	/**
	 * Returns the object that is stored in the list at position {@code index}.
	 * 
	 * @param index index of element to be returned (0 to size-1)
	 * @return object that is stored at position {@code index}
	 * @throws IndexOutOfBoundsException if the {@code index} is out of bounds
	 */
	Object get(int index);

	/**
	 * Inserts (does not overwrite) the given {@code value} at the given
	 * {@code position} in the list.
	 * 
	 * @param value    value to be inserted
	 * @param position position to insert the element to (0 to {@code size - 1})
	 * @throws IndexOutOfBoundsException If {@code position} is invalid
	 * @throws NullPointerException      if {@code value} is null
	 */
	void insert(Object value, int position);

	/**
	 * 
	 * Searches the list and returns the index of the first occurrence of the given
	 * {@code value} or -1 if the {@code value} is not found.
	 * 
	 * @param value element to search for
	 * @return index of the first occurrence of the given {@code value} or -1 if the
	 *         {@code value} is not found.
	 */
	int indexOf(Object value);

	/**
	 * 
	 * Removes element at specified {@code index} (0 to {@code size - 1}) from the
	 * list.
	 * 
	 * @param index position of element to be removed
	 * @throws IndexOutOfBoundsException If {@code index} is invalid
	 */
	void remove(int index);
}
