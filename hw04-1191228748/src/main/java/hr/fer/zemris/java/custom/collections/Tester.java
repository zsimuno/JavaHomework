/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * This intefrace models objects that recieve some other objects and tests is if it's acceptable or not.
 * 
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface Tester {
	/**
	 * Tests an object and then returns {@code true} or {@code false} depending if the objects passes the test.
	 * 
	 * @param obj object that we are testing
	 * @return {@code true} if object passes the test, {@code false} if it doesn't
	 */
	boolean test(Object obj);
}
