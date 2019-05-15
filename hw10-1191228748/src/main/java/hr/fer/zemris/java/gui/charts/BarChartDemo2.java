package hr.fer.zemris.java.gui.charts;

import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo for the BarChartComponent.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class BarChartDemo2 extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public BarChartDemo2() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		BarChart model = new BarChart(
				Arrays.asList(new XYValue(1, 8), new XYValue(2, 20), new XYValue(3, 22), new XYValue(4, 10),
						new XYValue(5, 4)),
				"Number of people in the car", "Frequency", 0, // y-os kreće od 0
				22, // y-os ide do 22
				2);
		BarChartComponent chart = new BarChartComponent(model);
		getContentPane().add(chart);
	}

	/**
	 *  Main method that starts the program.
	 * 
	 * @param args command line arguments. (not used here)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo2().setVisible(true);
		});
	}

}
