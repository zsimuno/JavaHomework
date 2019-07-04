package hr.fer.zemris.java.hw17.jvdraw.geometry.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;

/**
 * Represents non-filled circle geometrical object.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class FilledCircle extends GeometricalObject {

	/** Center point of the circle */
	private Point center;

	/** Radius of the circle */
	private int radius;

	/** Color of the line */
	private Color lineColor;

	/** Fill color of the circle */
	private Color fillColor;

	/**
	 * Contructor.
	 * 
	 * @param center    point
	 * @param radius    of the circle
	 * @param lineColor color of the line
	 * @param fillColor color that fills
	 */
	public FilledCircle(Point center, int radius, Color lineColor, Color fillColor) {
		this.center = center;
		this.radius = radius;
		this.lineColor = lineColor;
		this.fillColor = fillColor;
	}

	/**
	 * @return the center point
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * @return the radius of the circle
	 */ 
	public int getRadius() {
		return radius;
	}

	/**
	 * @return the line color
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * @return the fill color
	 */
	public Color getFillColor() {
		return fillColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);

	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor();
	}

	@Override
	public String toString() {
		return "Circle (" + center.x + "," + center.y + "), " + radius + ","
				+ String.format("#%02x%02x%02x", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
	}

}
