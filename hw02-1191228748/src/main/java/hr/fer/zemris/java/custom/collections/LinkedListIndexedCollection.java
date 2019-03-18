/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * Implementation of linked list-backed collection of objects.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class ListNode {
		/**
		 * Pointer to previous list node.
		 */
		ListNode previous;
		/**
		 * Pointer to next list node.
		 */
		ListNode next;

	}

	/**
	 * Current size of collection.
	 */
	private int size;
	/**
	 * Reference to the first node of the linked list.
	 */
	private ListNode first;
	/**
	 * Reference to the last node of the linked list.
	 */
	private ListNode last;

	public LinkedListIndexedCollection() {
		first = last = null;
	}

	public LinkedListIndexedCollection(Collection collection) {
		// TODO

	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return super.isEmpty();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	@Override
	public boolean contains(Object value) {
		// TODO Auto-generated method stub
		return super.contains(value);
	}

	@Override
	public boolean remove(Object value) {
		// TODO Auto-generated method stub
		return super.remove(value);
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return super.toArray();
	}

	@Override
	public void forEach(Processor processor) {
		// TODO Auto-generated method stub
		super.forEach(processor);
	}

	@Override
	public void addAll(Collection other) {
		// TODO Auto-generated method stub
		super.addAll(other);
	}

	@Override
	public void add(Object value) {
		// TODO Auto-generated method stub
		super.add(value);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}

	// TODO see if this metod needs to write "throws exception"
	/**
	 * Returns the object that is stored in linked list at position {@code index}.
	 * 
	 * @param index index of element to be returned (0 to size-1)
	 * @return object that is stored at position {@code index}
	 * @throws IndexOutOfBoundsException if index is not valid
	 */
	public Object get(int index) {
		Objects.checkIndex(index, this.size);
	}

	// TODO Check if docs should write legal positions (The legal positions are 0 to
	// size (both are included))
	/**
	 * Inserts (does not overwrite) the given {@code value} at the given
	 * {@code position} in array.
	 * 
	 * @param value    value to be inserted
	 * @param position position to insert the element to
	 * @throws IndexOutOfBoundsException If {@code position} is invalid
	 */
	public void insert(Object value, int position) {
		Objects.checkIndex(position, this.size + 1); // size+1 because size is inclusive

	}

	/**
	 * 
	 * Searches the collection and returns the index of the first occurrence of the
	 * given {@code value} or -1 if the {@code value} is not found.
	 * 
	 * @param value element to search for
	 * @return index of the first occurrence of the given {@code value} or -1 if the
	 *         {@code value} is not found.
	 */
	public int indexOf(Object value) {

	}

	/**
	 * 
	 * Removes element at specified {@code index} from collection.
	 * 
	 * @param index index position of element to be removed
	 * @throws IndexOutOfBoundsException If {@code position} is invalid
	 */
	public void remove(int index) {
		Objects.checkIndex(position, this.size);

	}

}
