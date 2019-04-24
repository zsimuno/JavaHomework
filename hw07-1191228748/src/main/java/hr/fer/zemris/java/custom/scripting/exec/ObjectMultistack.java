/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO javadoc
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ObjectMultistack {

	/**
	 * 
	 */
	private Map<String, MultistackEntry> map = new HashMap<>();

	// TODO paziti na nepravilne vrijednosti (tipa pop na prazno, peek na prazno i
	// isEmpty na prazno)

	/**
	 * @param keyName
	 * @param valueWrapper
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		var elementStack = map.get(keyName);

		if (elementStack == null) {
			map.put(keyName, new MultistackEntry(valueWrapper, null));

		} else {
			map.put(keyName, new MultistackEntry(valueWrapper, elementStack));
		}
	}

	/**
	 * @param keyName
	 * @return
	 */
	public ValueWrapper pop(String keyName) {
		var elementStack = map.get(keyName);

		if (elementStack == null)
			return null;

		map.put(keyName, elementStack.next);

		return elementStack.getValueWrapper();
	}

	/**
	 * @param keyName
	 * @return
	 */
	public ValueWrapper peek(String keyName) {
		var elementStack = map.get(keyName);

		if (elementStack == null)
			return null;

		return elementStack.getValueWrapper();
	}

	/**
	 * @param keyName
	 * @return
	 */
	public boolean isEmpty(String keyName) {
		return map.get(keyName) == null;

	}

	/**
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class MultistackEntry {
		/**
		 * 
		 */
		private ValueWrapper valueWrapper;

		/**
		 * 
		 */
		private MultistackEntry next;

		/**
		 * @param value
		 * @param next
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.valueWrapper = value;
			this.next = next;
		}

		/**
		 * @return the value
		 */
		public ValueWrapper getValueWrapper() {
			return valueWrapper;
		}

	}
}
