/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class {@code Dictionary} maps keys to values. Every key and value are
 * objects. Every entry is uniquely determined by the key value. Keys cannot be
 * null while values can. From a given key a value associated with that key can
 * be found.
 * 
 * @author Zvonimir Šimunović
 * 
 * @param <K> type of the keys stored in this dictionary
 * @param <V> type of the values od elements
 *
 */
public class Dictionary<K, V> {

	/**
	 * Class {@code Entry} represents one entry in the {@link Dictionary}.
	 * Represents a pair of key and value where both are objects. Keys cannot be
	 * null.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class Entry<K, V> {
		/**
		 * Key part of the {@code Entry}. Must be unique in one {@link Dictionary}
		 */
		private K key;

		/**
		 * Value part of the {@code Entry}. Can be null.
		 */
		private V value;

		/**
		 * Constructs an {@code Entry} object from given values.
		 * 
		 * @param key   key that will be stored in the entry
		 * @param value value that will be stored in the entry
		 * @throws NullPointerException if {@code key} is {@code null}
		 */
		public Entry(K key, V value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
		}

		/**
		 * Returns the key object
		 * 
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value object
		 * 
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

	}

	/**
	 * List of {@link Entry} objects. Stores entries of the dictionary.
	 */
	private ArrayIndexedCollection<Entry<K, V>> entryList = new ArrayIndexedCollection<Entry<K, V>>();

	/**
	 * Determines whether the {@code Dictionary} is empty
	 * 
	 * @return {@code true} if the dictionary is empty, {@code false} if it's not
	 */
	public boolean isEmpty() {
		return entryList.isEmpty();
	}

	/**
	 * Returns the size of the {@code Dictionary}.
	 * 
	 * @return the size of the {@code Dictionary}
	 */
	public int size() {
		return entryList.size();
	}

	/**
	 * Clears the dictionary.
	 */
	public void clear() {
		entryList.clear();
	}

	/**
	 * Puts one key and value entry in the dictionary. If the key already exists
	 * than overwrites the {@code value} part of the entry with the given one.
	 * 
	 * @param key   key to be put in the dictionary if it doesn't exists.
	 * @param value value to be put in the dictionary
	 * @throws NullPointerException if given {@code key} value is {@code null}
	 */
	public void put(K key, V value) {

		if (key == null) {
			throw new NullPointerException("Key value cannot be null!");
		}

		// If a key already exists
		ElementsGetter<Entry<K, V>> getter = entryList.createElementsGetter();

		while (getter.hasNextElement()) {
			Entry<K, V> entry = getter.getNextElement();
			if (entry.getKey().equals(key)) {
				entry.value = value;
				return;
			}
		}

		// If there is no such key
		entryList.add(new Entry<>(key, value));
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

		ElementsGetter<Entry<K, V>> getter = entryList.createElementsGetter();

		while (getter.hasNextElement()) {
			Entry<K, V> entry = getter.getNextElement();
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}

		// If there is no such key
		return null;

	}

}
