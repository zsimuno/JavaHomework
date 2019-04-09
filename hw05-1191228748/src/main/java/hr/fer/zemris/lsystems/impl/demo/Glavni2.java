/**
 * 
 */
package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo for LSystem classes
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Glavni2 {

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments. None are required here.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));

	}

	
	
	/**
	 * Creates a Koch curve and returns the necessary LSystem
	 * 
	 * @param provider an LSystem builder provider
	 * @return LSystem object
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		String[] data = new String[] {
				"origin 0.05 0.4",
				"angle 0",
				"unitLength 0.9",
				"unitLengthDegreeScaler 1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
				};
				return provider.createLSystemBuilder().configureFromText(data).build();
	}

}
