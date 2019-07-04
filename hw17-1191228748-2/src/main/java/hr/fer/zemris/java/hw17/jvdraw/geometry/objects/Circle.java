package hr.fer.zemris.java.hw17.jvdraw.geometry.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;

/**
 * Represents filled circle geometrical object.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Circle extends GeometricalObject {

	/** Center point of the circle */
	private Point center;

	/** Radius of the circle */
	private int radius;

	/** Color of the line */
	private Color lineColor;

	/**
	 * Contructor.
	 * 
	 * @param center    point
	 * @param radius    of the circle
	 * @param lineColor color of the line
	 */
	public Circle(Point center, int radius, Color lineColor) {
		this.center = center;
		this.radius = radius;
		this.lineColor = lineColor;
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

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);

	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor();
	}

	@Override
	public String toString() {
		return "Circle (" + center.x + "," + center.y + "), " + radius + "";
	}

}
