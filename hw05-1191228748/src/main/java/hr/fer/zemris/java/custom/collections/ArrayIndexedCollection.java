package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Resizable array-backed collection of objects.
 * 
 * @author Zvonimir Šimunović
 *
 * @param <T> type of the elements that are stored in this collection
 */
public class ArrayIndexedCollection<T> implements List<T> {
	/**
	 * Current size of collection (number of elements actually stored in elements
	 * array).
	 */
	private int size;
	/**
	 * An array of object references which length is determined by capacity
	 * variable.
	 */
	private T[] elements;
	/**
	 * Capacity that will be used if the one isn't given in the constructor.
	 */
	private static final int defaultArrayCapacity = 16;
	/**
	 * Counts each modification in the collection
	 */
	private long modificationCount = 0;

	/**
	 * Create an empty {@link ArrayIndexedCollection} object with the capacity
	 * equals to {@code initialCapacity} or {@code size} depending on which one is
	 * larger.
	 * 
	 * @param initialCapacity capacity that is set
	 * @param size            size of the new collection
	 * @throws IllegalArgumentException if initialCapacity is below 1
	 */
	@SuppressWarnings("unchecked")
	private ArrayIndexedCollection(int initialCapacity, int size) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.elements = (T[]) new Object[(initialCapacity < size) ? size : initialCapacity];
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
	public ArrayIndexedCollection(Collection<? extends T> collection, int initialCapacity) {
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
	public ArrayIndexedCollection(Collection<? extends T> collection) {
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
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		modificationCount++;
		this.size = 0;
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
	public T get(int index) {
		return this.elements[Objects.checkIndex(index, this.size)];
	}


	@Override
	public void insert(T value, int position) {
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
		modificationCount++;
		this.size++;
	}


	@Override
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

	@Override
	public void remove(int index) {
		for (int i = Objects.checkIndex(index, this.size); i < this.size - 1; i++) {
			this.elements[i] = this.elements[i + 1];
		}
		this.elements[this.size - 1] = null;
		modificationCount++;
		this.size--;
	}

	/**
	 * Class that we use to retrieve elements from the collection
	 * 
	 * @author Zvonimir Šimunović 
	 */
	private static class LocalElementGetter<T> implements ElementsGetter<T> {

		/**
		 * Object array that stores the elements of {@link ArrayIndexedCollection}.
		 */
		private ArrayIndexedCollection<T> arrayList;

		/**
		 * Index of the current element in the Object array.
		 */
		private int currentElementIndex;
		/**
		 * Modification count that was saved during the construction
		 */
		private long savedModificationCount;

		/**
		 * Constructs a new {@link LocalElementGetter} that stores elements of the
		 * {@link ArrayIndexedCollection}.
		 * 
		 * @param elements elements of the {@link ArrayIndexedCollection}
		 */
		public LocalElementGetter(ArrayIndexedCollection<T> array) {
			arrayList = array;
			savedModificationCount = array.modificationCount;
			currentElementIndex = 0;
		}

		@Override
		public boolean hasNextElement() {
			checkModificationCount();
			return (currentElementIndex < arrayList.elements.length && arrayList.elements[currentElementIndex] != null);
		}

		@Override
		public T getNextElement() {
			checkModificationCount();
			if (!hasNextElement())
				throw new NoSuchElementException();

			T returnValue = arrayList.elements[currentElementIndex];
			currentElementIndex++;
			return returnValue;
		}

		private void checkModificationCount() {
			if (savedModificationCount != arrayList.modificationCount)
				throw new ConcurrentModificationException();
		}

	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new LocalElementGetter<>(this);
	}

}
