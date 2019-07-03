package hr.fer.zemris.java.hw17.jvdraw.geometry;

import javax.swing.JPanel;

/**
 * TODO GO Editor
 * 
 * @author Zvonimir Šimunović
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public abstract void checkEditing();

	/**
	 * 
	 */
	public abstract void acceptEditing();
}