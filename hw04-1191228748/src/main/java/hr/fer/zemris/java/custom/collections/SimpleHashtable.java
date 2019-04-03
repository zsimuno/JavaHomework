/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 
 * 
 * @author Zvonimir Šimunović
 *
 */
/**
 * Implements a hash table that maps keys to values. Keys are non-null objects
 * that are unique to every entry. Values don't have to be unique but every key
 * has only one value.
 * 
 * <p>
 * Elements are stored in the table depending on the {@code hashCode} method
 * return value of the key. Keys are compared using the {@code equals} method.
 * </p>
 * 
 * @author Zvonimir Šimunović
 *
 * @param <K> type of the keys stored in this hash table
 * @param <V> type of the values od elements
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Size of the table if the initial capacity wasn't given
	 */
	private final static int defaultSize = 16;
	/**
	 * Percentage threshold that determines when do we resize the table
	 */
	private final static double resizePercentage = 0.75;

	/**
	 * Counts the number of modifications to the hash table
	 */
	private int modificationCount;

	/**
	 * Represents one entry in the table. It consists of a non-null unique key that
	 * maps to the value (doesn't need to be unique and can be null). Also every
	 * entry contains a reference to the next entry in the table spot. Key and value
	 * are both objects.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	public static class TableEntry<K, V> {
		/**
		 * Key of the entry. Must be non-null and unique.
		 */
		private K key;

		/**
		 * Value of the entry.
		 */
		private V value;

		/**
		 * Reference to the next element in the spot of the table
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructs a new {@code TableEntry} from the given values
		 * 
		 * @param key   key part of the entry, non-null and unique
		 * @param value value part of the entry
		 * @param next  reference to the next element in the table slot
		 * @throws NullPointerException if key is {@code null}
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns the value part of the entry
		 * 
		 * @return the value part of the entry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the {@code value} part of the entry to the given one
		 * 
		 * @param value the value to set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns the value of the {@code key} part of the entry.
		 * 
		 * @return the key key part of the entry
		 */
		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}

	}

	/**
	 * Table that contains keys and values of the hashtable.
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Size of the hashtable.
	 */
	private int size;

	/**
	 * Default constructor that sets the size of the table to {@value #defaultSize}
	 */
	public SimpleHashtable() {
		this(defaultSize);
	}

	/**
	 * Constructs a {@code SimpleHashtable} where the table capacity will be the
	 * first number in the form of 2^n bigger than given {@code capacity}
	 * 
	 * @param capacity capacity that the table will be constructed with
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}

		int newCapacity = 1;
		while (newCapacity < capacity) {
			newCapacity *= 2;
		}

		table = (TableEntry<K, V>[]) new TableEntry[newCapacity];
	}

	/**
	 * Puts one key and value entry in the hashtable. If the key already exists than
	 * overwrites the {@code value} part of the entry with the given one.
	 * 
	 * @param key   key to be put in the hashtable if it doesn't exists.
	 * @param value value to be put in the hashtable
	 * @throws NullPointerException if the given {@code key} is {@code null}
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key value cannot be null!");
		}

		// Resize the array if necessary
		// TODO Jel size provjeravamo ili broj popunjenih slotova u tablici? Kada
		// provjeravamo?
		if (size >= table.length * resizePercentage) {
			resize();
		}

		int slot = Math.abs(key.hashCode()) % table.length;

		// If the slot is empty
		if (table[slot] == null) {

			size++;
			table[slot] = new TableEntry<>(key, value, null);
			modificationCount++;
			return;
		}

		// Slot is not empty so lets add or update the element
		for (TableEntry<K, V> entry = table[slot]; entry != null; entry = entry.next) {

			// Is there already a key like the one given?
			if (entry.getKey().equals(key)) {
				entry.setValue(value);
				return;
			}

			// This entry is the last in this slot
			if (entry.next == null) {
				entry.next = new TableEntry<>(key, value, null);
				modificationCount++;
				size++;
				return;
			}

		}

	}

	/**
	 * Resizes the table to 2 * current size. Puts element in their new positions.
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		TableEntry<K, V>[] oldTable = table;

		this.table = (TableEntry<K, V>[]) new TableEntry[2 * oldTable.length];

		// TODO kill all references to old table?

		size = 0;

		for (int i = 0; i < oldTable.length; i++) {

			for (TableEntry<K, V> entry = oldTable[i]; entry != null; entry = entry.next) {
				this.put(entry.getKey(), entry.getValue());
			}
		}

	}

	/**
	 * Returns the {@code value} object that is associated with the given key. If
	 * the key doesn't exist {@code null} is returned. Note that the {@code value}
	 * can also be {@code null}.
	 * 
	 * @param key key that is used to look up the value associated with it
	 * @return the value that is associated with the given key or {@code null} if
	 *         the key doesn't exist.
	 */
	public V get(Object key) {
		if (key == null)
			return null;

		int slot = Math.abs(key.hashCode()) % table.length;

		for (TableEntry<K, V> entry = table[slot]; entry != null; entry = entry.next) {
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}

		return null;
	}

	/**
	 * Returns the size of the {@code SimpleHashtable} object.
	 * 
	 * @return the size of the {@code SimpleHashtable} object.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * TODO ova metoda i get imaju preslican kod. Mozda neki getEntry implementirat
	 * tako da prima i tester.
	 * 
	 * Checks if object is a key in this hashtable.
	 * 
	 * @param key object that we check if it's in this hashtable
	 * @return {@code true} if hashtable contains the key (determined by the
	 *         {@code equals} method), {@code false} otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;

		int slot = Math.abs(key.hashCode()) % table.length;

		for (TableEntry<K, V> entry = table[slot]; entry != null; entry = entry.next) {
			if (entry.getKey().equals(key)) {
				return true;
			}
		}

		return false;

	}

	/**
	 * Checks if this {@code SimpleHashtable} contains the given {@code value}
	 * 
	 * @param value value that we search for in the {@code SimpleHashtable}
	 * @return {@code true} if the hashtable contains the {@code value},
	 *         {@code false} otherwise
	 */
	public boolean containsValue(Object value) {

		for (int i = 0; i < table.length; i++) {

			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				if (entry.getValue() == null) {
					if (entry.getValue() == value) {
						return true;
					}
				} else if (entry.getValue().equals(value)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Removes an entry in this hashtable that has the given key.
	 * 
	 * @param key key of the entry that is to be removed
	 */
	public void remove(Object key) {
		if (key == null)
			return;

		for (int i = 0; i < table.length; i++) {

			for (TableEntry<K, V> entry = table[i], previous = null; entry != null; entry = entry.next) {
				if (entry.getKey().equals(key)) {

					if (previous == null) { // entry is the fist element in the slot

						table[i] = entry.next;
						entry.next = null;
					} else {
						// Current one has a previous element
						previous.next = entry.next;
						entry.next = null;
					}

					modificationCount++;
					size--;
					return;
				}

				previous = entry;
			}
		}

	}

	/**
	 * Checks if the {@code SimpleHashtable} is empty.
	 * 
	 * @return {@code true} if the hashtable is empty, {@code false} if it's not
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < table.length; i++) {

			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				sb.append(entry.toString() + ", ");
			}

		}

		int size = sb.length();
		sb.delete(size - 2, size); // Remove last two chars that are ", "
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Clears the hashtable
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			// TODO treba li sve elemente postavljat na null?
			table[i] = null;
		}
		modificationCount++;
		size = 0;
	}

	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Class that is used to iterate through a {@code SimpleHashtable} object
	 * elements.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Index of the current table slot
		 */
		private int currentTableIndex;
		/**
		 * Current entry in the hashtable
		 */
		private TableEntry<K, V> current;
		/**
		 * Next entry in the hashtable
		 */
		private TableEntry<K, V> nextElement;
		/**
		 * Modification count that was saved during the construction
		 */
		private long savedModificationCount;

		/**
		 * Conctruts an {@code IteratorImpl} object
		 */
		public IteratorImpl() {
			super();
			nextTableSlot();

			savedModificationCount = modificationCount;
		}

		@Override
		public boolean hasNext() {
			checkModificationCount();
			return nextElement != null; // Is it at least second to last element
		}

		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			checkModificationCount();
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			current = nextElement;

			// Last element in this slot.
			if (nextElement.next == null) {
				nextTableSlot();
			} else {
				nextElement = nextElement.next;
			}

			return current;
		}

		/**
		 * Sets the {@code nextElement} to the next non-null table slot. If there is not
		 * one like that then sets {@code nextElement} to {@code null}.
		 */
		private void nextTableSlot() {
			do {
				currentTableIndex++;
			} while (currentTableIndex < table.length && table[currentTableIndex] == null);

			// No elements in hash table
			if (currentTableIndex == table.length) {
				nextElement = null;
			} else {
				nextElement = table[currentTableIndex];
			}
		}

		@Override
		public void remove() {
			checkModificationCount();
			// TODO provjerit treba li postavljati current
			// Next not called yet or remove called already
			if (current == null) {
				throw new IllegalStateException("No element to be removed!");
			}

			SimpleHashtable.this.remove(current.getKey());
			current = null;
			savedModificationCount++;
		}

		private void checkModificationCount() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
		}

	}
}
