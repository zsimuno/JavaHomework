package hr.fer.zemris.java.hw17.jvdraw.drawing;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * TODO javadoc DrawingModel
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface DrawingModel {
	/**
	 * @return
	 */
	public int getSize();

	/**
	 * @param index
	 * @return
	 */
	public GeometricalObject getObject(int index);

	/**
	 * @param object
	 */
	public void add(GeometricalObject object);

	/**
	 * @param object
	 */
	public void remove(GeometricalObject object);

	/**
	 * @param object
	 * @param offset
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * @param object
	 * @return
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * 
	 */
	public void clear();

	/**
	 * 
	 */
	public void clearModifiedFlag();

	/**
	 * @return
	 */
	public boolean isModified();

	/**
	 * @param l
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * @param l
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
