package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;

/**
 * Abstract class that defines some properties every {@link Tool} should have.
 * 
 * @author Zvonimir Šimunović
 *
 */
public abstract class AbstractTool implements Tool {

	/** Model that holds objects. */
	protected DrawingModel model;

	/** Provides bg color. */
	protected IColorProvider bgColor;

	/** Provides fg color. */
	protected IColorProvider fgColor;

	/** Canvas that we draw on. */
	protected JDrawingCanvas canvas;

	/** Point of the first click. */
	protected Point firstClickPoint;

	/** Mouse move point. */
	protected Point mouseMovePoint;

	/** Is it first click? */
	protected boolean firstClick = true;

	/**
	 * Constructor.
	 * 
	 * @param model   Model that holds objects
	 * @param canvas  Canvas that we draw on
	 * @param fgColor Provides fg color
	 * @param bgColor Provides bg color
	 */
	protected AbstractTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgColor, IColorProvider bgColor) {
		this.model = model;
		this.canvas = canvas;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!firstClick) {
			mouseMovePoint = e.getPoint();
			canvas.repaint();
		}
	}

	/**
	 * Calculate distance between two points and return as integer.
	 * 
	 * @param p1 first point
	 * @param p2 second point
	 * @return distance between two given points as int
	 */
	protected int calculateDistance(Point p1, Point p2) {
		int arg1 = p1.x - p2.x;
		int arg2 = p1.y - p2.y;
		return (int) Math.sqrt(arg1 * arg1 + arg2 * arg2);
	}

	/**
	 * Draws the outline of the circle from the first point of the click to the
	 * mouse position.
	 * 
	 * @param e mouse event
	 */
	protected void drawCircleOutline(Graphics2D g2d) {
		int radius = calculateDistance(firstClickPoint, mouseMovePoint);
		g2d.setColor(fgColor.getCurrentColor());
		g2d.drawOval(firstClickPoint.x - radius, firstClickPoint.y - radius, 2 * radius, 2 * radius);
	}
}
