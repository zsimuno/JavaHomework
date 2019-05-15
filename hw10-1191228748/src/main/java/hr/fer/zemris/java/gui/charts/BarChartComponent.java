package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

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
	/** Width of the container. */
	int width;
	/** Height of the container. */
	int height;
	/** Y value for the end of the y axis. */
	int yGraphEnd = 10;
	/** X value for the end of the x axis. */
	int xGraphEnd;
	/** Length of the line that is drawn to indicate steps. */
	int lineLength = 5;
	/** Distance of the text from the axes. */
	int textDistance = lineLength + 5;
	/**
	 * Point that represents the starting point of the axes (i.e. (0,0) on the
	 * graph).
	 */
	Point graphStart;
	/** Maximum value of y. */
	int maxY;
	/** Minimal value of y. */
	int minY;
	/** Distance between values on graph from chart. */
	int elementDistance;
	/** Color of the grid. */
	Color gridColor = new Color(239, 224, 198);
	/** Color of the bars. */
	Color barColor = new Color(245, 134, 99);
	/** Color of the shadow of the bars. */
	Color shadowColor = Color.lightGray;
	/** Padding for shadow bars. */
	int shadowPadding = 5;
	/** Color of the axes. */
	Color axisColor = Color.gray;
	/** Color of the text. */
	Color textColor = Color.black;
	/** Color of the line that is drawn to indicate steps. */
	Color lineColor = Color.gray;
	/** List of values on the graph. */
	List<XYValue> list;
	/** padding of bars. */
	int padding = 1;
	/** Dimensions of arrows at the end of axes. */
	Dimension arrowDim = new Dimension(5, 7);

	/**
	 * Constructor.
	 * 
	 * @param chart BarChart to draw.
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
		maxY = chart.getMaxY();
		minY = chart.getMinY();
		elementDistance = chart.getDistance();
		while ((maxY - minY) % elementDistance != 0) {
			maxY++;
		}
		list = chart.getList();

	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;

		width = getWidth();
		height = getHeight();
		xGraphEnd = width - yGraphEnd;

		g.setFont(new Font("SansSerif", Font.BOLD, 15));
		FontMetrics fm = g.getFontMetrics();
		graphStart = new Point(40 + fm.stringWidth(Integer.toString(maxY)), height - 60);

		drawValuesAndBars(g);

		drawDescriptions(g);

		// Draw x and y axes
		g.setColor(axisColor);
		g.drawLine(graphStart.x, graphStart.y, width, graphStart.y);
		g.drawLine(graphStart.x, graphStart.y + lineLength, graphStart.x, 0);

		// Draw arrows
		Polygon p = new Polygon();
		p.addPoint(graphStart.x, 0);
		p.addPoint(graphStart.x - arrowDim.width, arrowDim.height);
		p.addPoint(graphStart.x + arrowDim.width, arrowDim.height);
		g.fillPolygon(p);
		Polygon q = new Polygon();
		q.addPoint(width, graphStart.y);
		q.addPoint(width - arrowDim.height, graphStart.y - arrowDim.width);
		q.addPoint(width - arrowDim.height, graphStart.y + arrowDim.width);
		g.fillPolygon(q);
	}

	/**
	 * Draw descriptions for x and y values.
	 * 
	 * @param g graphics we draw with.
	 */
	private void drawDescriptions(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		// Draw descriptions
		g.setFont(new Font("SansSerif", Font.PLAIN, 15));
		fm = g.getFontMetrics();

		g.setColor(textColor);
		String xDescript = chart.getxDescript();
		g.drawString(xDescript, ((graphStart.x + xGraphEnd) / 2) - fm.stringWidth(xDescript) / 2,
				height - fm.getHeight());

		AffineTransform saved = g.getTransform(); // Get the current transform
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g.transform(at);
		String yDescript = chart.getyDescript();
		g.drawString(yDescript, (-1) * (((graphStart.y + yGraphEnd) / 2) + fm.stringWidth(yDescript) / 2),
				0 + fm.getHeight());
		g.setTransform(saved);

	}

	/**
	 * Drawing values of x and y and drawing bars.
	 * 
	 * @param g graphics we draw with.
	 */
	private void drawValuesAndBars(Graphics2D g) {
		g.setStroke(new BasicStroke(2));

		FontMetrics fm = g.getFontMetrics();

		// Draw Y values
		int distanceY = (graphStart.y - yGraphEnd) / ((maxY - minY) / elementDistance);
		for (int i = minY, j = 0; i <= maxY; i += elementDistance, j++) {
			String number = Integer.toString(i);
			int yPosition = graphStart.y - j * distanceY;
			g.setColor(textColor);
			g.drawString(number, graphStart.x - textDistance - fm.stringWidth(number), yPosition + fm.getAscent() / 3);
			g.setColor(lineColor);
			g.drawLine(graphStart.x - lineLength, yPosition, graphStart.x, yPosition);
			g.setColor(gridColor);
			g.drawLine(graphStart.x, yPosition, width, yPosition);
		}

		// Draw X values and draw bars
		int distanceX = (xGraphEnd - graphStart.x) / list.size();
		int i = 0;
		for (XYValue val : list) {
			String number = Integer.toString(val.getX());
			int xPosition = graphStart.x + i * distanceX;
			int nextXPosition = graphStart.x + (i + 1) * distanceX;

			// Draw lines and values
			g.setColor(textColor);
			g.drawString(number, (xPosition + nextXPosition) / 2, graphStart.y + fm.getAscent() + textDistance / 2);
			g.setColor(lineColor);
			g.drawLine(nextXPosition, graphStart.y + lineLength, nextXPosition, graphStart.y);
			g.setColor(gridColor);
			g.drawLine(nextXPosition, graphStart.y, nextXPosition, 0);

			// Draw the bar
			Rectangle bar = new Rectangle();
			bar.width = distanceX - padding;
			bar.height = (val.getY() - minY) * distanceY / elementDistance;
			bar.x = xPosition + padding;
			bar.y = graphStart.y - (val.getY() - minY) * distanceY / elementDistance;
			g.setColor(barColor);
			g.fill(bar);

			// Draw shadow.
			Rectangle shadow = new Rectangle();
			shadow.width = shadowPadding;
			shadow.height = bar.height - shadowPadding;
			shadow.x = bar.x + bar.width;
			shadow.y = bar.y + shadowPadding;
			g.setColor(shadowColor);
			g.fill(shadow);

			i++;
		}

	}

}
