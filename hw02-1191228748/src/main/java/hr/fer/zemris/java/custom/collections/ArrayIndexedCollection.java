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
	 * An array of object references which length is determined by capacity
	 * variable.
	 */
	Object[] elements;
	/**
	 * Capacity that will be used if the one isn't given in the constructor.
	 */
	private static final int defaultArrayCapacity = 16;

	/**
	 * @param initialCapacity
	 * @param size
	 */
	private ArrayIndexedCollection(int initialCapacity, int size) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.elements = new Object[(initialCapacity < size) ? size : initialCapacity];
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
		this(defaultArrayCapacity);
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
		this(collection, defaultArrayCapacity);
	}

	@Override
	public boolean isEmpty() {
		return (this.size == 0);
	}

	@Override
	public int size() {
		return this.size();
	}

	@Override
	public boolean contains(Object value) {
		return (this.indexOf(value) > -1);
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
		return Arrays.copyOf(this.elements, this.size);
	}

	@Override
	public void forEach(Processor processor) {
		for (Object object : elements) {
			processor.process(object);
		}
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

	// TODO see if this method needs to write "throws exception"
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

		if (this.size == elements.length) {
			this.elements = Arrays.copyOf(this.elements, elements.length*2);
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
