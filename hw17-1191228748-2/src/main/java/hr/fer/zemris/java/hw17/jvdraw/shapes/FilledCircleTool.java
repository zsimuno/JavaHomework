package hr.fer.zemris.java.hw17.jvdraw.shapes;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.tools.AbstractTool;

/**
 * Represents the geometrical object filled circle.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class FilledCircleTool extends AbstractTool {

	/**
	 * Constructor.
	 * 
	 * @param model   Model that holds objects
	 * @param canvas  Canvas that we draw on
	 * @param fgColor Provides fg color
	 * @param bgColor Provides bg color
	 */
	public FilledCircleTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgColor, IColorProvider bgColor) {
		super(model, canvas, fgColor, bgColor);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (firstClick) {
			firstClick = false;
			firstClickPoint = e.getPoint();
		} else {
			int radius = calculateDistance(firstClickPoint, e.getPoint());
			model.add(new FilledCircle(firstClickPoint, radius, fgColor.getCurrentColor(), bgColor.getCurrentColor()));
			firstClick = true;
		}

	}

	@Override
	public void paint(Graphics2D g2d) {
		if (!firstClick) {
			int radius = calculateDistance(firstClickPoint, mouseMovePoint);
			g2d.setColor(bgColor.getCurrentColor());
			g2d.fillOval(firstClickPoint.x - radius, firstClickPoint.y - radius, 2 * radius, 2 * radius);
			drawCircleOutline(g2d);
		}
	}

}
