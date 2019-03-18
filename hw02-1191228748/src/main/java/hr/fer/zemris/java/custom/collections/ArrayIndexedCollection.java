package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * Resizable array-backed collection of objects.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ArrayIndexedCollection extends Collection {
	/**
	 * Current size of collection (number of elements actually stored in elements
	 * array).
	 */
	private int size;
	/**
	 * Capacity of the elements array.
	 */
	private int capacity;
	/**
	 * An array of object references which length is determined by capacity variable.
	 */
	Object[] elements;

	/**
	 * @param initialCapacity
	 * @param size
	 */
	private ArrayIndexedCollection(int initialCapacity, int size) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.capacity = (initialCapacity < size) ? size : initialCapacity;
		this.elements = new Object[this.capacity];
		this.size = size;
	}

	/**
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this(initialCapacity, 0);
	}

	/**
	 * 
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * @param collection
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this(initialCapacity, Objects.requireNonNull(collection).size());

		// TODO
//		Other two constructors are variation of the previous two, but they accept additional parameter (as first
//		argument): a non-null reference to some other Collection which elements are copied into this newly
//		constructed collection; if the initialCapacity is smaller than the size of the given collection, the size of
//		the given collection should be used for elements array preallocation. If the given collection is null , a
//		NullPointerException should be thrown.
	}

	/**
	 * @param collection
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 16);
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
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}

	@Override
	public void add(Object value) {
		this.insert(value, this.size);
	}

	// TODO see if this metod needs to write "throws exception"
	/**
	 * Returns the object that is stored in backing array at position {@code index}.
	 * 
	 * @param index index of element to be returned (0 to size-1)
	 * @return object that is stored at position {@code index}
	 * @throws IndexOutOfBoundsException if the {@code index} is out of bounds
	 */
	public Object get(int index) {
		return this.elements[Objects.checkIndex(index, this.size)];
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

		if (this.size == this.capacity) {
			this.capacity *= 2;
			this.elements = Arrays.copyOf(this.elements, this.capacity);
			// TODO See if the implementation is valid ("do not allocate new array" ?)
		}

		for (int i = this.size; i > position; i--) {
			this.elements[i] = this.elements[i - 1];
		}

		this.elements[position] = value;
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
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * Removes element at specified {@code index} from collection.
	 * 
	 * @param index position of element to be removed
	 * @throws IndexOutOfBoundsException If {@code index} is out of bounds
	 */
	public void remove(int index) {
		for (int i = Objects.checkIndex(index, this.size); i < this.size; i++) {
			this.elements[i] = this.elements[i + 1];
		}
		this.size--;
	}

}
