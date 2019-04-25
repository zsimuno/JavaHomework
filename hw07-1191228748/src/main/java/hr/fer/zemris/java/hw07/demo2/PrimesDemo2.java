/**
 * 
 */
package hr.fer.zemris.java.hw07.demo2;

/**
 * Class that demos {@link PrimesCollection} class
 * 
 * @author Zvonimir Šimunović
 *
 */
public class PrimesDemo2 {

	/**
	 * Method that starts the program
	 * 
	 * @param args command line arguments (not used here)
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for(Integer prime : primesCollection) {
		for(Integer prime2 : primesCollection) {
		System.out.println("Got prime pair: "+prime+", "+prime2);
		}
		}
		
//		Got prime pair: 2, 2
//		Got prime pair: 2, 3
//		Got prime pair: 3, 2
//		Got prime pair: 3, 3
	}

}
