/**
 * 
 */
package hr.fer.zemris.java.hw07.demo2;

/**
 * @author Zvonimir Šimunović
 *
 */
public class PrimesDemo2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
