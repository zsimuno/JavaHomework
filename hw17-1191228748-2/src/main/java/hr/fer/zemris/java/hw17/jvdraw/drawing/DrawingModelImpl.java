package hr.fer.zemris.java.hw17.jvdraw.drawing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectListener;

/**
 * Implementation of a drawing model.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {

	/** List of objects in the model. */
	List<GeometricalObject> objects = new ArrayList<>();

	/** Listeners to this model. */
	Set<DrawingModelListener> listeners = new HashSet<>();

	/** Flag that shows if the model has been modified or not. */
	boolean modified = false;

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		object.addGeometricalObjectListener(this);
		fireObjectsAdded(getSize(), getSize());
		modified = true;
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		if (index == -1)
			return;
		objects.remove(object);
		object.removeGeometricalObjectListener(this);
		fireObjectsRemoved(index, index);
		modified = true;
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int currentPosition = objects.indexOf(object);
		if (currentPosition == -1)
			throw new IllegalArgumentException("No such object in list.");
		int newPosition = currentPosition + offset;
		objects.remove(object);
		objects.add(newPosition, object);
		modified = true;
		fireObjectsChanged(currentPosition, newPosition);
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		int size = objects.size();
		objects.clear();
		fireObjectsRemoved(0, size);
		modified = true;
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);

	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);

	}

	/**
	 * Notify listeners that objects have been added.
	 * 
	 * @param index0 index of first object
	 * @param index1 index of last object
	 */
	private void fireObjectsAdded(int index0, int index1) {
		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, index0, index1);
		}
	}

	/**
	 * Notify listeners that objects have been removed.
	 * 
	 * @param index0 index of first object
	 * @param index1 index of last object
	 */
	private void fireObjectsRemoved(int index0, int index1) {
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index0, index1);
		}
	}

	/**
	 * Notify listeners that objects have been changed.
	 * 
	 * @param index0 index of first object
	 * @param index1 index of last object
	 */
	private void fireObjectsChanged(int index0, int index1) {
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, index0, index1);
		}
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = objects.indexOf(o);
		fireObjectsChanged(index, index);
	}
}
