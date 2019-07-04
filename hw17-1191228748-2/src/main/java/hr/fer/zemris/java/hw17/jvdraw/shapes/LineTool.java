package hr.fer.zemris.java.hw17.jvdraw.shapes;

import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.tools.AbstractTool;

/**
 * Represents the geometrical object line.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LineTool extends AbstractTool {

	/**
	 * Constructor.
	 * 
	 * @param model   Model that holds objects
	 * @param canvas  Canvas that we draw on
	 * @param fgColor Provides fg color
	 * @param bgColor Provides bg color
	 */
	public LineTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgColor, IColorProvider bgColor) {
		super(model, canvas, fgColor, bgColor);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (firstClick) {
			firstClick = false;
			firstClickPoint = e.getPoint();
		} else {
			model.add(new Line(firstClickPoint, e.getPoint(), fgColor.getCurrentColor()));
			firstClick = true;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!firstClick) {
			g2d.setColor(fgColor.getCurrentColor());
			g2d.drawLine(firstClickPoint.x, firstClickPoint.y, e.getX(), e.getY());
		}

	}



}
