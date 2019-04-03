/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of linked list-backed collection of objects.
 * 
 * @author Zvonimir Šimunović
 * 
 * @param <T> type of the elements that are stored in this collection
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {

	/**
	 * 
	 * Class that is used to represent one node of the linked list
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class ListNode<T> {
		/**
		 * Pointer to previous list node.
		 */
		ListNode<T> previous;
		/**
		 * Pointer to next list node.
		 */
		ListNode<T> next;
		/**
		 * Value stored on this node.
		 */
		T value;

		/**
		 * Constructs a new {@link ListNode} object from the given values.
		 * 
		 * @param previous pointer to previous list node
		 * @param next     pointer to next list node
		 * @param value    value stored on this node
		 */
		public ListNode(ListNode<T> previous, ListNode<T> next, T value) {
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
	private ListNode<T> first;
	/**
	 * Reference to the last node of the linked list.
	 */
	private ListNode<T> last;
	/**
	 * Counts each modification in the collection
	 */
	private long modificationCount = 0;

	/**
	 * Default constructor that constructs an empty
	 * {@link LinkedListIndexedCollection} object
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
		this.size = 0;
	}

	/**
	 * Constructor that constructs an {@link LinkedListIndexedCollection} object
	 * that has the elements copied from the other {@code collection}
	 * 
	 * @param collection other {@code Collection} whose elements are copied into
	 *                   this newly constructed collection
	 * @throws NullPointerException if {@code collection} is null
	 */
	public LinkedListIndexedCollection(Collection<? extends T> collection) {
		this();
		this.addAll(Objects.requireNonNull(collection));
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean contains(Object value) {
		return (this.indexOf(value) > -1);
	}

	@Override
	public boolean remove(Object value) {
		if(value == null) 
			return false;

		for (ListNode<T> el = this.first; el != null; el = el.next) {
			if (el.value.equals(value)) {
				removeNode(el);
				return true;
			}
		}
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[this.size];
		ListNode<T> element = this.first;

		for (int i = 0; i < this.size; ++i, element = element.next) {
			array[i] = element.value;
		}

		return array;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if {@code value} is null
	 */
	@Override
	public void add(T value) {
		this.insert(value, this.size);
	}

	@Override
	public void clear() {
		// Removing all links
		for (ListNode<T> el = this.first; el != null;) {
			ListNode<T> next = el.next;
			el.value = null;
			el.next = null;
			el.previous = null;
			el = next;
		}

		this.first = this.last = null;
		modificationCount++;
		this.size = 0;
	}

	/**
	 * Returns ListNode element stored at the position {@code index}
	 * 
	 * @param index index of the element to be returned
	 * @return ListNode object that is stored at position {@code index}
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	private ListNode<T> getNode(int index) {
		// We check in which half is the index
		if (Objects.checkIndex(index, this.size) < this.size / 2) { // Start from beginning
			ListNode<T> current = this.first;

			for (int i = 0; i < index; i++) {
				current = current.next;
			}

			return current;
		} else { // Start from end
			ListNode<T> current = this.last;

			for (int i = size - 1; i > index; i--) {
				current = current.previous;
			}

			return current;
		}
	}


	@Override
	public T get(int index) {
		return this.getNode(index).value; // getNode checks the validity of the index
	}


	@Override
	public void insert(T value, int position) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (Objects.checkIndex(position, this.size + 1) == this.size) { // size+1 because size is inclusive
			// Add to the end
			this.last = new ListNode<T>(this.last, null, value);

			if (this.isEmpty()) { // this.size is not yet changed so this checks if it was empty before inserting
				this.first = this.last;
			} else {
				this.last.previous.next = this.last;
			}
		} else {
			ListNode<T> element = this.getNode(position);
			if (element.previous == null) {
				element.previous = new ListNode<T>(element.previous, element, value);
				this.first = element.previous;
			} else {
				element.previous.next = element.previous = new ListNode<T>(element.previous, element, value);
			}

		}
		modificationCount++;
		this.size++;

	}

	@Override
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}

		ListNode<T> current = this.first;

		for (int i = 0; i < this.size; i++, current = current.next) {
			if (current.value.equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Removes one ListNode element from the collection.
	 * 
	 * @param element element that needs to be removed
	 */
	public void removeNode(ListNode<T> element) {
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
		modificationCount++;
	}

	
	@Override
	public void remove(int index) {
		ListNode<T> element = this.getNode(index); // getNode checks the validity of the index

		removeNode(element);

	}

	/**
	 * Class that we use to retrieve elements from the collection
	 * 
	 * @author Zvonimir Šimunović 
	 */
	private static class LocalElementGetter<T> implements ElementsGetter<T> {
		/**
		 * Represents current node of the collection
		 */
		private ListNode<T> currentNode;
		/**
		 * Modification count that was saved during the construction
		 */
		private long savedModificationCount;

		/**
		 * Variable that we store the list in. This list is the one we are getting elements from.
		 */
		private LinkedListIndexedCollection<T> linkedList;

		/**
		 * Constructs new {@link LocalElementGetter} that points to the first element of
		 * the given {@link LinkedListIndexedCollection}
		 * 
		 * @param first reference to the first element of the
		 *              {@link LinkedListIndexedCollection}
		 */
		public LocalElementGetter(LinkedListIndexedCollection<T> list) {
			linkedList = list;
			savedModificationCount = list.modificationCount;
			currentNode = list.first;
		}

		@Override
		public boolean hasNextElement() {
			checkModificationCount();
			return currentNode != null;
		}

		@Override
		public T getNextElement() {
			checkModificationCount();
			if (!hasNextElement())
				throw new NoSuchElementException();

			T returnValue = currentNode.value;
			currentNode = currentNode.next;
			return returnValue;
		}

		private void checkModificationCount() {
			if (savedModificationCount != linkedList.modificationCount)
				throw new ConcurrentModificationException();
		}

	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new LocalElementGetter<>(this);
	}

}
