package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * 
 * Represents a model of single document, having information about file path
 * from which document was loaded (can be {@code null} for new document),
 * document modification status and reference to Swing component which is used
 * for editing (each document has its own editor component).
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface SingleDocumentModel {
	/**
	 * Returns text component that we use to represent this document.
	 * 
	 * @return text component that we use to represent this document.
	 */
	JTextArea getTextComponent();

	/**
	 * Returns the path to the document in this model.
	 * 
	 * @return path to the document in this model.
	 */
	Path getFilePath();

	/**
	 * Sets the file path of this document model. Path cannot be {@code null}.
	 * 
	 * @param path path to be set in this model.
	 */
	void setFilePath(Path path);

	/**
	 * Returns if this document has been modified.
	 * 
	 * @return {@code true} if this document has been modified, {@code false}
	 *         otherwise.
	 */
	boolean isModified();

	/**
	 * Sets the modified parameter of this document.
	 * 
	 * @param modified parameter that we set this document to.
	 */
	void setModified(boolean modified);

	/**
	 * Adds a listener to this document.
	 * 
	 * @param l listener to be added.
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes a listener from this document.
	 * 
	 * @param l listener to be removed.
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}