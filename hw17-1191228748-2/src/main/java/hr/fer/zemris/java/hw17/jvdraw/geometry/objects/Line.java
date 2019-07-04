package hr.fer.zemris.java.hw17.jvdraw.geometry.objects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.LineEditor;

/**
 * Represents line geometrical object.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Line extends GeometricalObject {

	/** Starting point */
	private Point start;

	/** Ending point */
	private Point end;

	/** Color of the line */
	private Color lineColor;

	/**
	 * Contstructor.
	 * 
	 * @param start     start of the line
	 * @param end       end of the line
	 * @param lineColor color of the line
	 */
	public Line(Point start, Point end, Color lineColor) {
		this.start = start;
		this.end = end;
		this.lineColor = lineColor;
	}

	/**
	 * @return the start of the line
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * @return the end of the line
	 */
	public Point getEnd() {
		return end;
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
		return new LineEditor();
	}

	@Override
	public String toString() {
		return "Line (" + start.x + "," + start.y + ")-(" + end.x + "," + end.y + ")";
	}

}
