package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

import com.sun.org.apache.bcel.internal.classfile.ArrayElementValue;

/**
 * Resizable array-backed collection of objects.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ArrayIndexedCollection implements Collection {
	/**
	 * Current size of collection (number of elements actually stored in elements
	 * array).
	 */
	private int size;
	/**
	 * An array of object references which length is determined by capacity
	 * variable.
	 */
	private Object[] elements;
	/**
	 * Capacity that will be used if the one isn't given in the constructor.
	 */
	private static final int defaultArrayCapacity = 16;

	/**
	 * Create an empty {@link ArrayIndexedCollection} object with the capacity
	 * equals to {@code initialCapacity} or {@code size} depending on which one is
	 * larger.
	 * 
	 * @param initialCapacity capacity that is set
	 * @param size            size of the new collection
	 * @throws IllegalArgumentException if initialCapacity is below 1
	 */
	private ArrayIndexedCollection(int initialCapacity, int size) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.elements = new Object[(initialCapacity < size) ? size : initialCapacity];
		this.size = 0;
	}

	/**
	 * Create an empty {@link ArrayIndexedCollection} object with the capacity
	 * equals to {@code initialCapacity}
	 * 
	 * @param initialCapacity capacity that is set
	 * @throws IllegalArgumentException if initialCapacity is below 1
	 * 
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this(initialCapacity, 0);
	}

	/**
	 * Create an empty {@link ArrayIndexedCollection} object with the capacity
	 * equals to {@value #defaultArrayCapacity}
	 */
	public ArrayIndexedCollection() {
		this(defaultArrayCapacity);
	}

	/**
	 * Create an {@link ArrayIndexedCollection} object which elements are copied
	 * from the other {@link Collection} object.
	 * 
	 * <p>
	 * New {@link ArrayIndexedCollection} will have the capacity equals to
	 * {@code initialCapacity} or the size of the {@code collection} if it's bigger
	 * than {@code initialCapacity}.
	 * </p>
	 * 
	 * @param collection      {@link Collection} which elements are copied into this
	 *                        newly constructed collection
	 * @param initialCapacity capacity that is set
	 * @throws NullPointerException     If the given collection is null
	 * @throws IllegalArgumentException if initialCapacity is below 1
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this(initialCapacity, Objects.requireNonNull(collection).size());

		this.addAll(collection);
	}

	/**
	 * Create an {@link ArrayIndexedCollection} object which elements are copied
	 * from the other {@link Collection} object.
	 * <p>
	 * New {@link ArrayIndexedCollection} will have the capacity equals to
	 * {@value #defaultArrayCapacity} or the size of the {@code collection} if it's
	 * bigger than {@value #defaultArrayCapacity}.
	 * </p>
	 * 
	 * @param collection {@link Collection} which elements are copied into this
	 *                   newly constructed collection
	 * @throws NullPointerException If the given collection is null
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, defaultArrayCapacity);
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
		for (int i = 0; i < this.size; i++) {
			processor.process(elements[i]);
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if {@code value} is null
	 */
	@Override
	public void add(Object value) {
		this.insert(value, this.size);
	}

	/**
	 * Returns the object that is stored in backing array at position {@code index}.
	 * Complexity is O(1).
	 * 
	 * @param index index of element to be returned (0 to size-1)
	 * @return object that is stored at position {@code index}
	 * @throws IndexOutOfBoundsException if the {@code index} is out of bounds
	 */
	public Object get(int index) {
		return this.elements[Objects.checkIndex(index, this.size)];
	}

	/**
	 * Inserts (does not overwrite) the given {@code value} at the given
	 * {@code position} in array. Complexity is O(size)
	 * 
	 * @param value    value to be inserted
	 * @param position position to insert the element to (0 to {@code size})
	 * @throws IndexOutOfBoundsException If {@code position} is invalid
	 * @throws NullPointerException      if {@code value} is null
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException();
		}

		if (this.size == elements.length) {
			this.elements = Arrays.copyOf(this.elements, elements.length * 2);
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
	 * given {@code value} or -1 if the {@code value} is not found. Complexity
	 * O(size).
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
	 * Removes element at specified {@code index} (0 to {@code size - 1}) from
	 * collection.
	 * 
	 * @param index position of element to be removed
	 * @throws IndexOutOfBoundsException If {@code index} is out of bounds
	 */
	public void remove(int index) {
		for (int i = Objects.checkIndex(index, this.size); i < this.size - 1; i++) {
			this.elements[i] = this.elements[i + 1];
		}
		this.elements[this.size - 1] = null;
		this.size--;
	}
	
	/**
	 * @author Zvonimir Šimunović
	 *TODO
	 */
	private static class LocalElementGetter implements ElementsGetter {
		
		/**
		 * Object array that stores the elements of {@link ArrayIndexedCollection}.
		 */
		private Object[] arrayElements;
		
		/**
		 * Index of the current element in the Object array.
		 */
		private int currentElementIndex;

		/**
		 * Constructs a new {@link LocalElementGetter} that stores elements of the {@link ArrayIndexedCollection}.
		 * @param elements elements of the {@link ArrayIndexedCollection}
		 */
		public LocalElementGetter(Object[] elements) {
			arrayElements = elements;
			currentElementIndex = 0;
		}

		@Override
		public boolean hasNextElement() {
			// TODO Auto-generated method stub
			return (currentElementIndex < arrayElements.length - 1 && arrayElements[currentElementIndex + 1] != null);
		}

		@Override
		public Object getNextElement() {
			currentElementIndex++;
			return arrayElements[currentElementIndex];
		}
		
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new LocalElementGetter(elements);
	}

}
