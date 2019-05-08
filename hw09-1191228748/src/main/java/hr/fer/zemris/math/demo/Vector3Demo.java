package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Vector3;

/**
 * Class that demos {@link Vector3} class.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Vector3Demo {

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments (not used here).
	 */
	public static void main(String[] args) {
		Vector3 i = new Vector3(1, 0, 0);
		Vector3 j = new Vector3(0, 1, 0);
		Vector3 k = i.cross(j);
		
		Vector3 l = k.add(j).scale(5);
		
		Vector3 m = l.normalized();
		
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
		System.out.println(l);
		System.out.println(l.norm());
		System.out.println(m);
		System.out.println(l.dot(j));
		System.out.println(i.add(new Vector3(0, 1, 0)).cosAngle(l));

//		(1.000000, 0.000000, 0.000000)
//		(0.000000, 1.000000, 0.000000)
//		(0.000000, 0.000000, 1.000000)
//		(0.000000, 5.000000, 5.000000)
//		7.0710678118654755
//		(0.000000, 0.707107, 0.707107)
//		5.0
//		0.4999999999999999
	}

}
