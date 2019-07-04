package hr.fer.zemris.java.hw17.jvdraw.drawing;

/**
 * Listens to the drawing model and is called when something is done with
 * objects in the model.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface DrawingModelListener {
	/**
	 * Called when an object is added to the model.
	 * 
	 * @param source source of the call
	 * @param index0 index of the first element added
	 * @param index1 index of the last element added
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Called when an object is removed from the model.
	 * 
	 * @param source source of the call
	 * @param index0 index of the first element of the removal
	 * @param index1 index of the last element of the removal
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Called when an object is changed in the model.
	 * 
	 * @param source source of the call
	 * @param index0 index of the first element of the change
	 * @param index1 index of the last element of the change
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}