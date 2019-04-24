/**
 * 
 */
package hr.fer.zemris.java.hw07.demo2;

/**
 * @author Zvonimir Šimunović
 *
 */
public class PrimesDemo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
