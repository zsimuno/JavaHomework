package hr.fer.zemris.java.hw17.jvdraw.shapes;

import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.tools.AbstractTool;

/**
 * Represents the geometrical object (not filled) circle.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CircleTool extends AbstractTool {

	
	/**
	 * Constructor.
	 * 
	 * @param model   Model that holds objects
	 * @param canvas  Canvas that we draw on
	 * @param fgColor Provides fg color
	 * @param bgColor Provides bg color
	 */
	public CircleTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgColor, IColorProvider bgColor) {
		super(model, canvas, fgColor, bgColor);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (firstClick) {
			firstClick = false;
			firstClickPoint = e.getPoint();
		} else {
			int radius = 0;
			model.add(new Circle(firstClickPoint, radius, fgColor.getCurrentColor()));
			firstClick = true;
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!firstClick) {
			drawCircleOutline(e);
		}
	}



}
