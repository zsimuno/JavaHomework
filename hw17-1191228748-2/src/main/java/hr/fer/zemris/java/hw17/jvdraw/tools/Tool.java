package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Represents one tool as the State in state design pattern.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface Tool {
	/**
	 * Action on mouse pressed.
	 * 
	 * @param e mouse event.
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Action on mouse released.
	 * 
	 * @param e mouse event.
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Action on mouse clicked.
	 * 
	 * @param e mouse event.
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Action on mouse moved.
	 * 
	 * @param e mouse event.
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Action on mouse dragged.
	 * 
	 * @param e mouse event.
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paint with thiw tool.
	 * 
	 * @param g2d graphics we use to paint
	 */
	public void paint(Graphics2D g2d);
}