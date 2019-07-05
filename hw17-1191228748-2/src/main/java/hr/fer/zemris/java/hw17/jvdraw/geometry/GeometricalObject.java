package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;


/**
 * Represents one geometrical object.
 * 
 * @author Zvonimir Šimunović
 *
 */
public abstract class GeometricalObject {

	/** Listeners that listen to this geometrical object. */
	private Set<GeometricalObjectListener> listeners = new HashSet<>();

	/**
	 * Calls appropriate visit method of the visitor on this object.
	 * 
	 * @param v visitor that visits geometrical objects.
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Creates a geometrical object editor for this object.
	 * 
	 * @return newly created geometrical object editor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Add a listener to this geometrical object.
	 * 
	 * @param l listener that listens when the state of this object has changed
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	/**
	 * Remove a listener from this geometrical object.
	 * 
	 * @param l listener that listens when the state of this object has changed
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

	/**
	 * Notify all listeners that listen to this geometrical object.
	 */
	protected void notifyListeners() {
		for (GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}
	

}
