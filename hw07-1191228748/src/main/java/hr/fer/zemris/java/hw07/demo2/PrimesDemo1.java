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
public class PrimesDemo1 {

	/**
	 * Method that starts the program
	 * 
	 * @param args command line arguments (not used here)
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

//		Got prime: 2
//		Got prime: 3
//		Got prime: 5
//		Got prime: 7
//		Got prime: 11

	}

}
