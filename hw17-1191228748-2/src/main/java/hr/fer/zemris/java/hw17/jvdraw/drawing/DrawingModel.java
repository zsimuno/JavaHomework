package hr.fer.zemris.java.hw17.jvdraw.drawing;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * Model for the drawing that contains and manages all shapes.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface DrawingModel {
	/**
	 * 
	 * 
	 * @return Number of shapes in the model
	 */
	public int getSize();

	/**
	 * Get object at specified {@code index}.
	 * 
	 * @param index index of the object
	 * @return object at specified {@code index}.
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Add the given {@code object} to the model.
	 * 
	 * @param object object to be added
	 */
	public void add(GeometricalObject object);

	/**
	 * Remove the given {@code object} from the model.
	 * 
	 * @param object object to be removed
	 */
	public void remove(GeometricalObject object);

	/**
	 * Move the given {@code object} from current position to currentPosition +
	 * offset.
	 * 
	 * @param object object to move
	 * @param offset offset to move on
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Index of the given {@code object} in the model.
	 * 
	 * @param object object we look for the index of
	 * @return index of the given object or -1 if the object is not in the model
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Clear the model of all objects.
	 */
	public void clear();

	/**
	 * Sets the modified flag to {@code false}.
	 */
	public void clearModifiedFlag();

	/**
	 * Returns whether the model has been modified.
	 * 
	 * @return {@code true} if the model has been modified or {@code false} if it
	 *         wasn't
	 */
	public boolean isModified();

	/**
	 * Add a listener to this model.
	 * 
	 * @param l listener to be added
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Remove a listener from this model.
	 * 
	 * @param l listener to be removed
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
