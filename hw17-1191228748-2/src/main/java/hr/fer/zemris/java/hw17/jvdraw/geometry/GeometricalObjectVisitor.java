package hr.fer.zemris.java.hw17.jvdraw.geometry;

import hr.fer.zemris.java.hw17.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw17.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.shapes.Line;

/**
 * Visitor interface for geometrical objects.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Visit line geometrical object.
	 * 
	 * @param line line geometrical object
	 */
	public abstract void visit(Line line);

	/**
	 * Visit (not filled) circle geometrical object.
	 * 
	 * @param circle circle geometrical object
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visit filled circle geometrical object.
	 * 
	 * @param filledCircle filled circle geometrical objec
	 */
	public abstract void visit(FilledCircle filledCircle);
}