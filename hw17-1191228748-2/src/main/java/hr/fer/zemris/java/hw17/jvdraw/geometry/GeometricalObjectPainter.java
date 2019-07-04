package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;
/**
 * Class that paints geometrical objects.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/** Graphics we use to draw shapes. */
	Graphics2D g2d;

	/**
	 * Constructor.
	 * 
	 * @param g2d Graphics we use to draw shapes.
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		Point start = line.getStart();
		Point end = line.getEnd();
		g2d.setColor(line.getLineColor());
		g2d.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public void visit(Circle circle) {
		Point center = circle.getCenter();
		int radius = circle.getRadius();
		g2d.setColor(circle.getLineColor());
		g2d.drawOval(center.x, center.y, radius, radius);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point center = filledCircle.getCenter();
		int radius = filledCircle.getRadius();
		g2d.setColor(filledCircle.getFillColor());
		g2d.fillOval(center.x, center.y, radius, radius);
		g2d.setColor(filledCircle.getLineColor());
		g2d.drawOval(center.x, center.y, radius, radius);

	}

}
