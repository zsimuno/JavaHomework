package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * Class that calculates the bounding box of the {@link GeometricalObject}
 * elements it visits.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/** Bounding box for complete image. */
	private Rectangle boundingBox;

	/**
	 * @return the bounding box for the complete image
	 */
	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	@Override
	public void visit(Line line) {
		if (boundingBox == null) {
			boundingBox = new Rectangle(line.getStart());
		}
		checkPoint(line.getStart());
		checkPoint(line.getEnd());

	}

	@Override
	public void visit(Circle circle) {
		checkCircle(circle.getCenter(), circle.getRadius());
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		checkCircle(filledCircle.getCenter(), filledCircle.getRadius());
	}

	/**
	 * Check if the circle is within the bounding box and change the box if needed.
	 * 
	 * @param center center of the circle
	 * @param radius radius of the circle
	 */
	private void checkCircle(Point center, int radius) {
		if (boundingBox == null) {
			boundingBox = new Rectangle(center);
		}

		if (boundingBox.x > center.x - radius) {
			boundingBox.x = center.x - radius;
		}

		if (boundingBox.y > center.y - radius) {
			boundingBox.y = center.y - radius;
		}

		if (boundingBox.width < center.x + radius) {
			boundingBox.width = center.x + radius;
		}

		if (boundingBox.height < center.y + radius) {
			boundingBox.height = center.y + radius;
		}
	}

	/**
	 * Check if the point is within the bounding box and change the box if needed.
	 * 
	 * @param point point to be checked
	 */
	private void checkPoint(Point point) {
		if (boundingBox.x > point.x) {
			boundingBox.x = point.x;
		}

		if (boundingBox.y > point.y) {
			boundingBox.y = point.y;
		}

		if (boundingBox.width < point.x) {
			boundingBox.width = point.x;
		}

		if (boundingBox.height < point.y) {
			boundingBox.height = point.y;
		}
	}

}
