/**
 * 
 */
package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo for LSystem classes
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Glavni3 {

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments. None are required here.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}

}
