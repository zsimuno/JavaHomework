/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Model of an object capable of performing some operation on the passed object.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface Processor {

	/**
	 * Processes the Object.
	 * 
	 * @param value Object to be processed
	 */
	public void process(Object value);

}
