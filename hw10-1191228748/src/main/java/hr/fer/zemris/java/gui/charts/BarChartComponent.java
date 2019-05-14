package hr.fer.zemris.java.gui.charts;

import javax.swing.JComponent;

/**
 * Component that draws the bar chart given in the constructor.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	/** BarChart to draw. */
	private BarChart chart;

	/**
	 * Constructor.
	 * 
	 * @param chart BarChart to draw.
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
		drawChart();
	}

	/**
	 * Draws the chart.
	 */
	private void drawChart() {
		// TODO Auto-generated method stub

	}

}
