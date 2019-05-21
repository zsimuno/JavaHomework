package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation of {@link SingleDocumentModel} that stores document data as
 * {@link JTextArea}.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/** File path from which document was loaded. */
	private Path filePath;

	/** Text area representing the document. */
	private JTextArea textArea;

	/** Is the document modified or not. */
	private boolean isModified = false;

	private Set<SingleDocumentListener> listeners = new HashSet<>();

	/**
	 * Constructor.
	 * 
	 * @param filePath File path from which document was loaded.
	 * @param textArea Text representing the document.
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		this.filePath = filePath;
		this.textArea = new JTextArea(text);
		this.textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if the given {@code path} is {@code null}.
	 */
	@Override
	public void setFilePath(Path path) {
		this.filePath = Objects.requireNonNull(path);
		listeners.forEach((l) -> {
			l.documentFilePathUpdated(this);
		});
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		this.isModified = modified;
		listeners.forEach((l) -> {
			l.documentModifyStatusUpdated(this);
		});
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
