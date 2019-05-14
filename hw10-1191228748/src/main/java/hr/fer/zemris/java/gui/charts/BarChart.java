package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents one bar chart.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class BarChart {

	/** List of values in the chart. */
	private List<XYValue> list;
	/** Description for x values. */
	private String xDescript;
	/** Description for y values. */
	private String yDescript;
	/** Minimal value for y. */
	private int minY;
	/** Maximal value for y. */
	private int maxY;
	/** Distance between values. */
	private int distance;

	/**
	 * Constructor.
	 * 
	 * @param list      List of values in the chart.
	 * @param xDescript Description for x values.
	 * @param yDescript Description for y values.
	 * @param minY      Minimal value for y.
	 * @param maxY      Maximal value for y.
	 * @param distance  Distance between values.
	 */
	public BarChart(List<XYValue> list, String xDescript, String yDescript, int minY, int maxY, int distance) {
		if (minY < 0 || maxY <= minY)
			throw new IllegalArgumentException("Invalid value for Y!");

		for (XYValue value : list) {
			if (value.getY() < minY)
				throw new IllegalArgumentException("List value not greater than the minimum!");
		}

		this.list = list;
		this.xDescript = xDescript;
		this.yDescript = yDescript;
		this.minY = minY;
		this.maxY = maxY;
		this.distance = distance;
	}

	/**
	 * @return the list of values in the chart.
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * @return the Description for x values.
	 */
	public String getxDescript() {
		return xDescript;
	}

	/**
	 * @return the Description for y values.
	 */
	public String getyDescript() {
		return yDescript;
	}

	/**
	 * @return the Minimal value for y.
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @return the Maximal value for y.
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * @return the Distance between values.
	 */
	public int getDistance() {
		return distance;
	}

}
