/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

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
	 * Class that is used to represent one node of the linked list
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
		/**
		 * Value stored on this node.
		 */
		Object value;

		/**
		 * Constructs a new {@link ListNode} object from the given values.
		 * 
		 * @param previous pointer to previous list node
		 * @param next     pointer to next list node
		 * @param value    value stored on this node
		 */
		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}

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

	/**
	 * Default constructor that constructs an empty
	 * {@link LinkedListIndexedCollection} object
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
	}

	/**
	 * Constructor that constructs an {@link LinkedListIndexedCollection} object
	 * that has the elements copied from the other {@code collection}
	 * 
	 * @param collection other {@code Collection} whose elements are copied into
	 *                   this newly constructed collection
	 */
	public LinkedListIndexedCollection(Collection collection) {
		this.addAll(collection);
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean contains(Object value) {
		for (ListNode el = this.first; el != null; el = el.next) {
			if (el.value.equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(Object value) {
		int indexOf = this.indexOf(value);

		if (indexOf == -1) {
			return false;
		}

		this.remove(indexOf);
		return true;
	}

	@Override
	public Object[] toArray() {
		// TODO Stavit kopiju reference ili vrijednosti?
		Object[] array = new Object[this.size];
		ListNode element = this.first;

		for (int i = 0; i < this.size; ++i, element = element.next) {
			array[i] = element.value;
		}

		return array;
	}

	@Override
	public void forEach(Processor processor) {
		for (ListNode el = this.first; el != null; el = el.next) {
			processor.process(el.value);
		}
	}

	@Override
	public void add(Object value) {
		this.insert(value, this.size);
	}

	@Override
	public void clear() {
		// TODO Provjeriti treba li osoboditi sve reference
		this.first = this.last = null;
	}

	/**
	 * Returns ListNode element stored at the position {@code index}
	 * 
	 * @param index index of the element to be returned
	 * @return ListNode object that is stored at position {@code index}
	 */
	private ListNode getNode(int index) {
		// We check in which half is the index
		if (index < this.size / 2) { // Start from beginning
			ListNode current = this.first;

			for (int i = 0; i < index; i++) {
				current = current.next;
			}

			return current;
		} else { // Start from end
			ListNode current = this.last;

			for (int i = size - 1; i > index; i--) {
				current = current.previous;
			}

			return current;
		}
	}

	// TODO see if this method needs to write "throws exception"
	/**
	 * Returns the object that is stored in linked list at position {@code index}.
	 * 
	 * @param index index of element to be returned (0 to size-1)
	 * @return object that is stored at position {@code index}
	 * @throws IndexOutOfBoundsException if index is not valid
	 */
	public Object get(int index) {
		return this.getNode(Objects.checkIndex(index, this.size)).value;
	}

	// TODO see if this method needs to write "throws exception"
	/**
	 * Inserts (does not overwrite) the given {@code value} at the given
	 * {@code position} in array. (0 to {@code size - 1})
	 * 
	 * @param value    value to be inserted
	 * @param position position to insert the element to
	 * @throws IndexOutOfBoundsException If {@code position} is invalid
	 */
	public void insert(Object value, int position) {
		if (Objects.checkIndex(position, this.size + 1) == this.size) { // size+1 because size is inclusive
			// Add to the end
			this.last = new ListNode(this.last, null, Objects.requireNonNull(value));

			if (this.isEmpty()) { // this.size is not yet changed so this checks if it was empty before inserting
				this.first = this.last;
			} else {
				this.last.previous.next = this.last;
			}
		} else {
			ListNode element = this.getNode(position);
			element.previous = new ListNode(element.previous, element, value);
		}
		this.size++;

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
		if (value == null) {
			return -1;
		}

		ListNode current = this.first;

		for (int i = 0; i < this.size; i++, current = current.next) {
			if (current.value == value) {
				return i;
			}
		}

		return -1;
	}

	// TODO see if this method needs to write "throws exception"
	/**
	 * 
	 * Removes element at specified {@code index} (0 to {@code size - 1}) from
	 * collection.
	 * 
	 * @param index position of element to be removed
	 * @throws IndexOutOfBoundsException If {@code index} is invalid
	 */
	public void remove(int index) {
		ListNode element = this.getNode(Objects.checkIndex(index, this.size));

		if (element.previous == null) {
			this.first = element.next;
		} else {
			element.previous.next = element.next;
		}

		if (element.next == null) {
			this.last = element.previous;
		} else {
			element.next.previous = element.previous;
		}

		this.size--;

	}

}
