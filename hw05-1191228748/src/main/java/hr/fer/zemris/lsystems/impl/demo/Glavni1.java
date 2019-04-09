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
public class Glavni1 {

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
		return provider.createLSystemBuilder()
				.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4)
				.setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0/3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
	}

}
