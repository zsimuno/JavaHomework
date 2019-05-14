package hr.fer.zemris.java.gui.charts;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * Demo for the BarChartComponent.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public BarChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo().setVisible(true);
		});
	}

}
