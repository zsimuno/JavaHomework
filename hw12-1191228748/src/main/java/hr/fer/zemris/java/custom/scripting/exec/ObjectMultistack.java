/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a map that has {@link String} objects as keys and a
 * stack of {@link ValueWrapper} objects as value. Has usual stack methods that
 * do stack operations on the map element of the given key.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ObjectMultistack {

	/**
	 * Map that contains elements.
	 */
	private Map<String, MultistackEntry> map = new HashMap<>();

	/**
	 * Pushes the given {@code ValueWrapper} to the top of the stack of the given
	 * key.
	 * 
	 * @param keyName      key of the element.
	 * @param valueWrapper value that is to be pushed on the stack.
	 * @throws NullPointerException if the specified key or value is {@code null}
	 *                              and this map does not permit {@code null} keys
	 *                              or values
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		if (keyName == null || valueWrapper == null) {
			throw new NullPointerException("Keys and values can't be null!");
		}
		var elementStack = map.get(keyName);

		if (elementStack == null) {
			map.put(keyName, new MultistackEntry(valueWrapper, null));

		} else {
			map.put(keyName, new MultistackEntry(valueWrapper, elementStack));
		}
	}

	/**
	 * Pops the top element of the stack with the given key and returns the value
	 * that was on top.
	 * 
	 * @param keyName key of the element.
	 * @return the value that was on top of the stack.
	 * @throws EmptyStackException if stack of given key is empty.
	 */
	public ValueWrapper pop(String keyName) {
		var elementStack = map.get(keyName);

		if (elementStack == null)
			throw new EmptyStackException();

		// If last element is going to be popped then remove key
		if (elementStack.next == null) {
			map.remove(keyName);
		} else {
			map.put(keyName, elementStack.next);
		}

		return elementStack.getValueWrapper();
	}

	/**
	 * Returns the top element of the stack without removing it.
	 * 
	 * @param keyName key of the element.
	 * @return the value that is on top of the stack.
	 * @throws EmptyStackException if stack of given key is empty.
	 */
	public ValueWrapper peek(String keyName) {
		var elementStack = map.get(keyName);

		if (elementStack == null)
			throw new EmptyStackException();

		return elementStack.getValueWrapper();
	}

	/**
	 * Check if the stack with the given key is empty.
	 * 
	 * @param keyName key of the stack.
	 * @return {@code true} if the stack is empty, {@code false} otherwise.
	 */
	public boolean isEmpty(String keyName) {
		return map.get(keyName) == null;

	}

	/**
	 * Represents an entry of the {@link ObjectMultistack}. Used to represent the
	 * stack with a linked list.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class MultistackEntry {
		/**
		 * {@link ValueWrapper} object that is contained in this entry.
		 */
		private ValueWrapper valueWrapper;

		/**
		 * Next element in the linked list of entries.
		 */
		private MultistackEntry next;

		/**
		 * Constructs a new {@link MultistackEntry} with given values.
		 * 
		 * @param value value that is to be stored in this entry.
		 * @param next  next {@link MultistackEntry} object in the linked list.
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.valueWrapper = value;
			this.next = next;
		}

		/**
		 * Returns the {@link ValueWrapper} object stored in this entry.
		 * 
		 * @return {@link ValueWrapper} object stored in this entry.
		 */
		public ValueWrapper getValueWrapper() {
			return valueWrapper;
		}

	}
}
