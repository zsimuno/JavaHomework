/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.exec;

/**
 * TODO javadoc
 * @author Zvonimir Šimunović
 *
 */
public class ValueWrapper {

	/**
	 * 
	 */
	private Object value;

	/**
	 * @param value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * @param incValue
	 */
	public void add(Object incValue) {
		incValue = checkForNull(incValue);
	}

	/**
	 * @param decValue
	 */
	public void subtract(Object decValue) {
		decValue = checkForNull(decValue);
	}

	/**
	 * @param mulValue
	 */
	public void multiply(Object mulValue) {
		mulValue = checkForNull(mulValue);
	}

	/**
	 * @param divValue
	 */
	public void divide(Object divValue) {
		divValue = checkForNull(divValue);
	}

	/**
	 * @param withValue
	 * @return
	 */
	public int numCompare(Object withValue) {
		if(this.value == null && withValue == null) {
			return 0;
		}
		withValue = checkForNull(withValue);
	}

	/**
	 * @param givenValue
	 * @return
	 */
	private Object checkForNull(Object givenValue) {
		if(this.value == null) {
			this.value = Integer.valueOf(0);
		}
		
		return (givenValue == null) ? Integer.valueOf(0) : givenValue;
	}
	
	/**
	 * @param givenValue
	 * @throws RuntimeException
	 */
	private void checkInstances(Object givenValue) {
		
	}

}
