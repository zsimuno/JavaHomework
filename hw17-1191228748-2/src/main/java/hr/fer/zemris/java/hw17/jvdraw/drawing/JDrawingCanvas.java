package hr.fer.zemris.java.hw17.jvdraw.drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectPainter;

public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** */
	private static final long serialVersionUID = 1L;

	/** Main drawing application */
	JVDraw drawApp;

	/**
	 * Constructor.
	 * 
	 * @param app main application of this program.
	 */
	public JDrawingCanvas(JVDraw app) {
		this.drawApp = app;

		MouseListener canvasMouseListener = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				drawApp.getCurrentTool().mouseClicked(e);
			};

			@Override
			public void mouseDragged(MouseEvent e) {
				drawApp.getCurrentTool().mouseDragged(e);
			};

			@Override
			public void mouseMoved(MouseEvent e) {
				drawApp.getCurrentTool().mouseMoved(e);
			};

			@Override
			public void mousePressed(MouseEvent e) {
				drawApp.getCurrentTool().mousePressed(e);
			};

			@Override
			public void mouseReleased(MouseEvent e) {
				drawApp.getCurrentTool().mouseReleased(e);
			};
		};

		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

		this.addMouseListener(canvasMouseListener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		GeometricalObjectPainter painter = new GeometricalObjectPainter((Graphics2D) g);
		DrawingModel model = drawApp.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}

		if(drawApp.getCurrentTool() != null ) {
			drawApp.getCurrentTool().paint((Graphics2D) g);
		}
		
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
