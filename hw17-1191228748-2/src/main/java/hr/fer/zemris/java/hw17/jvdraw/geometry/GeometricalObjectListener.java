package hr.fer.zemris.java.hw17.jvdraw.geometry;

/**
 * Listeners to when geometrical objects state changes.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Called when the geometrical objects state changes.
	 * 
	 * @param o object which state has changed
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}