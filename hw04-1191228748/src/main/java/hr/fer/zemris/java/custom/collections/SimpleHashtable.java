/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 
 * TODO javadoc od SimpleHashtable
 * @author Zvonimir Šimunović
 *
 */
/**
 * @author Zvonimir Šimunović
 *
 * @param <K>
 * @param <V>
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * 
	 */
	private final static int defaultSize = 16;

	/**
	 * @author Zvonimir Šimunović
	 *
	 */
	public static class TableEntry<K, V> {
		/**
		 * 
		 */
		private K key;

		/**
		 * 
		 */
		private V value;

		/**
		 * 
		 */
		private TableEntry<K, V> next;

		/**
		 * @param key
		 * @param value
		 * @throws NullPointerException if {@code key} is {@code null}
		 */
		public TableEntry(K key, V value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
		}

		/**
		 * TODO trebaju li nam za ovo getteri i setter?
		 * 
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * @return the key
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
	 * 
	 */
	TableEntry<K, V>[] table;

	/**
	 * 
	 */
	int size;

	/**
	 * 
	 */
	public SimpleHashtable() {
		this(defaultSize);
	}

	/**
	 * @param capacity
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
	 * @param key
	 * @param value
	 * @throws NullPointerException
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}

		int slot = Math.abs(key.hashCode()) % table.length;

		if (table[slot] == null) {
			size++;

			// Do we need to resize the table array?
			// TODO ne mozemo ovako jer modulo vrijednosti vise nisu iste?
			if (size >= table.length * 0.75) {
				table = Arrays.copyOf(table, table.length * 2);
			}

			table[slot] = new TableEntry<>(key, value);
			return;
		}

		TableEntry<K, V> entry = table[slot];

		// Is there already a key like the one given?
		while (entry.next != null) {
			if (entry.getKey().equals(key)) {
				entry.setValue(value);
				return;
			}
			entry = entry.next;

		}

		// No such key already
		entry.next = new TableEntry<>(key, value);
		size++;
	}

	/**
	 * @param key
	 * @return
	 */
	public V get(Object key) {
		if (key == null)
			return null; // TODO mozda vratiti iznimku

		int slot = Math.abs(key.hashCode()) % table.length;

		for (TableEntry<K, V> entry = table[slot]; entry != null; entry = entry.next) {
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}

		return null;
	}

	/**
	 * @return
	 */
	public int size() {
		return this.size;
	}

	/**
	 * TODO ova metoda i get imaju preslican kod. Mozda neki getEntry implementirat
	 * tako da prima i tester.
	 * 
	 * @param key
	 * @return
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
	 * @param value
	 * @return
	 */
	public boolean containsValue(Object value) {

		for (int i = 0; i < table.length; i++) {

			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				if (entry.getValue().equals(value)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @param key
	 */
	public void remove(Object key) {
		if (key == null)
			return;

	}

	/**
	 * @return
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
		sb.delete(size - 2, size - 1); // Remove last two chars that are ", "
		sb.append("]");

		return sb.toString();
	}

	/**
	 * 
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			// TODO treba li sve elemente postavljat na null?
			table[i] = null;
		}
	}

	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * @author Zvonimir Šimunović
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * 
		 */
		private int nextIndex;
		/**
		 * 
		 */
		private int elementsCount;
		/**
		 * 
		 */
		private TableEntry<K, V> current;

		@Override
		public boolean hasNext() {
			return elementsCount < size;
		}

		@Override
		public TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			// TODO Paziti na pristup indexima
			current = current.next;

			if (current == null) {
				while (table[nextIndex] != null) {
					nextIndex++;
				}
				current = table[nextIndex];
			}
			
			elementsCount++;
			return current;
		}

		@Override
		public void remove() {

		}

	}
}
