package hr.fer.zemris.java.hw11.jnotepadpp.model;

/**
 * Listener for the {@link SingleDocumentModel} objects. Objects that implement
 * this interface are notified when {@link SingleDocumentModel} objects they
 * listen to change.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface SingleDocumentListener {
	/**
	 * This method is called when the document's {@code modified} state is changed.
	 * 
	 * @param model model that this listener listens to and whose {@code modified}
	 *              state is changed.
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * This method is called when the document's file path is updated.
	 * 
	 * @param model model that this listener listens to and it's file path is
	 *              updated.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}