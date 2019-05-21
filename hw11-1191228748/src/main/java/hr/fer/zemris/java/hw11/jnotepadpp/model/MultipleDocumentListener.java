package hr.fer.zemris.java.hw11.jnotepadpp.model;

/**
 * Listener for the {@link MultipleDocumentModel} objects. Objects that
 * implement this interface are notified when {@link MultipleDocumentModel}
 * objects they listen to change.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Called when the current document is changed in the
	 * {@link MultipleDocumentModel} object.
	 * 
	 * @param previousModel previous document.
	 * @param currentModel  current document.
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Called when a new document is added.
	 * 
	 * @param model newly added document.
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Called when a document is removed.
	 * 
	 * @param model document that was removed.
	 */
	void documentRemoved(SingleDocumentModel model);
}