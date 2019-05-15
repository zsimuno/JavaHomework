package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
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

	/**
	 * Constructor.
	 * 
	 * @param chart BarChart to draw.
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;

		int width = getWidth();
		int height = getHeight();
		int yGraphEnd = 10, xGraphEnd = width - yGraphEnd;
		int lineLength = 5, textDistance = lineLength + 5;
		Point graphStart = new Point(60, height - 60);

		int maxY = chart.getMaxY(), minY = chart.getMinY();
		int elementDistance = chart.getDistance();

		if ((maxY - minY) % elementDistance != 0) {
			// TODO Sto tocno napraviti?
		}
		int distanceY = (graphStart.y - yGraphEnd) / ((maxY - minY) / elementDistance);

		g.setFont(new Font("SansSerif", Font.BOLD, 15));
		FontMetrics fm = g.getFontMetrics();

		Color gridColor = new Color(239, 224, 198);
		Color barColor = new Color(245, 134, 99);
		g.setStroke(new BasicStroke(2));

		// Draw Y values
		for (int i = minY, j = 0; i <= maxY; i += elementDistance, j++) {
			String number = Integer.toString(i);
			int yPosition = graphStart.y - j * distanceY;
			g.setColor(Color.black);
			g.drawString(number, graphStart.x - textDistance - fm.stringWidth(number), yPosition + fm.getAscent() / 3);
			g.setColor(Color.gray);
			g.drawLine(graphStart.x - lineLength, yPosition, graphStart.x, yPosition);
			g.setColor(gridColor);
			g.drawLine(graphStart.x, yPosition, width, yPosition);
		}

		// Draw X values and draw bars
		List<XYValue> list = chart.getList();
		int distanceX = (xGraphEnd - graphStart.x) / list.size();
		int padding = 1;
		int i = 0;
		for (XYValue val : list) {
			String number = Integer.toString(val.getX());
			int xPosition = graphStart.x + i * distanceX;
			int nextXPosition = graphStart.x + (i + 1) * distanceX;

			// Draw lines and values
			g.setColor(Color.black);
			g.drawString(number, (xPosition + nextXPosition) / 2, graphStart.y + fm.getAscent() + textDistance / 2);
			g.setColor(Color.gray);
			g.drawLine(nextXPosition, graphStart.y + lineLength, nextXPosition, graphStart.y);
			g.setColor(gridColor);
			g.drawLine(nextXPosition, graphStart.y, nextXPosition, 0);

			// Draw the bar
			Rectangle rec = new Rectangle();
			rec.width = distanceX - padding;
			rec.height = (val.getY() / elementDistance) * distanceY;
			rec.x = xPosition + padding;
			rec.y = graphStart.y - (val.getY() / elementDistance) * distanceY;
			g.setColor(barColor);
			g.fill(rec);
			i++;
		}

		// Draw descriptions
		g.setFont(new Font("SansSerif", Font.PLAIN, 15));
		fm = g.getFontMetrics();
		g.setColor(Color.black);
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

		g.setColor(Color.gray);
		// Draw x and y
		g.drawLine(graphStart.x, graphStart.y, width, graphStart.y);
		g.drawLine(graphStart.x, graphStart.y + lineLength, graphStart.x, 0);

		// Draw arrows
		int arrowWidth = 5, arrowLength = 7;
		Polygon p = new Polygon();
		p.addPoint(graphStart.x, 0);
		p.addPoint(graphStart.x - arrowWidth, arrowLength);
		p.addPoint(graphStart.x + arrowWidth, arrowLength);
		g.fillPolygon(p);
		Polygon q = new Polygon();
		q.addPoint(width, graphStart.y);
		q.addPoint(width - arrowLength, graphStart.y - arrowWidth);
		q.addPoint(width - arrowLength, graphStart.y + arrowWidth);
		g.fillPolygon(q);
	}

}
