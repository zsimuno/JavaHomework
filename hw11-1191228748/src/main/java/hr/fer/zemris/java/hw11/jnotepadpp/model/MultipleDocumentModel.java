package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;

/**
 * 
 * Represents a model capable of holding zero, one or more documents, where each
 * document and having a concept of current document – the one which is shown to
 * the user and on which user works.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a new document as a {@link SingleDocumentModel} object and returns
	 * it.
	 * 
	 * @return newly created document.
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns the current document.
	 * 
	 * @return current document.
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads a document to a new tab from the given path. Path cannot be
	 * {@code null}.
	 * 
	 * @param path path of the document to be opened.
	 * @return newly opened document.
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves the given document with the given path. If the given path is
	 * {@code null}, document is saved using path associated from document,
	 * otherwise, new path is used and after saving is completed, document’s path is
	 * updated to {@code newPath}.
	 * 
	 * @param model   document to be saved.
	 * @param newPath new path of the document.

	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Removes the given document.
	 * 
	 * @param model document to be removed.
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds a listener to this model.
	 * 
	 * @param l listener to be added.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes a listener from this model.
	 * 
	 * @param l listener to be removed.
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns the number of documents in this multiple document model.
	 * 
	 * @return number of documents.
	 */
	int getNumberOfDocuments();

	/**
	 * Retrieves the document at the given {@code index}.
	 * 
	 * @param index index of the document.
	 * @return retrieves the document at the given {@code index}.
	 */
	SingleDocumentModel getDocument(int index);
	
	/**
	 * Activates given document.
	 * 
	 * @param model document to activate.
	 * 
	 */
	void activateDocument(SingleDocumentModel model);
}