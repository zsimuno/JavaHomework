/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * TODO Javadoc
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Dictionary<K, V> {

	/**
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class Entry<K, V> {
		/**
		 * 
		 */
		private K key;

		/**
		 * 
		 */
		private V value;

		/**
		 * @param key
		 * @param value
		 * @throws NullPointerException if {@code key} is {@code null}
		 */
		public Entry(K key, V value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
		}

		/**
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

	}

	/**
	 * 
	 */
	private ArrayIndexedCollection<Entry<K, V>> entryList = new ArrayIndexedCollection<Entry<K, V>>();

	/**
	 * 
	 */
	public Dictionary() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return
	 */
	boolean isEmpty() {
		return entryList.isEmpty();
	}

	/**
	 * @return
	 */
	int size() {
		return entryList.size();
	}

	/**
	 * 
	 */
	void clear() {
		entryList.clear();
	}

	/**
	 * @param key
	 * @param value
	 */
	void put(K key, V value) {

		// TODO Ima li ovo smisla?
		if (key == null)
			return;

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
	 * @param key
	 * @return
	 */
	V get(Object key) {
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
