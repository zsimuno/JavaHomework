package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import javax.swing.JPanel;

/**
 * Represents an editor of a geometrical object.
 * 
 * @author Zvonimir Šimunović
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * Check the editing.
	 */
	public abstract void checkEditing();

	/**
	 * Accept the editing.
	 */
	public abstract void acceptEditing();
}