package hr.fer.zemris.java.hw17.jvdraw.drawing;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * List model that gets all his elements from a {@link DrawingModel} object.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/** */
	private static final long serialVersionUID = 1L;

	/** Drawing model of this application. */
	DrawingModel model;

	/**
	 * Constructor.
	 * 
	 * @param model drawing model of the application
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		this.model.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
