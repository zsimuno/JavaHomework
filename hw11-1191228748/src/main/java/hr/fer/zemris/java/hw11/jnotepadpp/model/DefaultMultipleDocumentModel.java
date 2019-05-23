package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.Util;

/**
 * Implementation of {@link MultipleDocumentModel} that stores documents in
 * multiple tabs (this class extends {@link JTabbedPane} ).
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	/** Collection of documents in this model. */
	private List<SingleDocumentModel> documents = new ArrayList<>();

	/** Listeners for this model. */
	private Set<MultipleDocumentListener> listeners = new HashSet<>();

	/** Currently open document */
	private SingleDocumentModel currentDocument;


	/**
	 * Creates an empty <code>DefaultMultipleDocumentModel</code> with a default tab
	 * placement of <code>JTabbedPane.TOP</code>.
	 * 
	 */
	public DefaultMultipleDocumentModel() {
		super();
		// Set a listener that notifies document listeners when tab changes.
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// No tabs
				if (getSelectedIndex() < 0) {
					currentDocument = null;
					return;
				}

				// Only tab
				if (currentDocument == null) {
					currentDocument = documents.get(getSelectedIndex());
					return;
				}

				// Multiple tabs
				SingleDocumentModel old = currentDocument;
				currentDocument = documents.get(getSelectedIndex());
				notifyChangeDocument(old, currentDocument);

			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
		documents.add(doc);
		addTab("(unnamed)", Util.getIcon("saved.png", getTopLevelAncestor()), new JScrollPane(doc.getTextComponent()),
				"(unnamed)");
		currentDocument = doc;
		doc.addSingleDocumentListener(docListener);
		notifyAddDocument(doc);
		setSelectedIndex(getTabCount() - 1);
		return doc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException     if the given {@code path} is {@code null}.
	 * @throws IllegalArgumentException if there is an error with the given
	 *                                  {@code path}.
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null)
			throw new NullPointerException("Path cannot be null (load).");

		// Check if file is already opened
		for (int i = 0; i < documents.size(); i++) {
			SingleDocumentModel doc = documents.get(i);
			if (doc.getFilePath() == null)
				continue;

			if (doc.getFilePath().equals(path)) {
				setSelectedIndex(i);
				return doc;
			}
		}

		if (!Files.isReadable(path)) {
			throw new IllegalArgumentException("Given path must be readable.");
		}

		// Read from file
		String text;
		try {
			text = Files.readString(path, StandardCharsets.UTF_8);
		} catch (IOException e1) {
			throw new IllegalArgumentException("Can't read from file.");
		}
		
		// Create document
		SingleDocumentModel document = new DefaultSingleDocumentModel(path, text);
		documents.add(document);
		if (getSelectedIndex() < 0) {
			notifyAddDocument(document);
		} else {
			SingleDocumentModel old = getCurrentDocument();
			notifyChangeDocument(old, document);
		}

		addTab(path.getFileName().toString(), Util.getIcon("saved.png", getTopLevelAncestor()),
				new JScrollPane(document.getTextComponent()), path.toString());
		setSelectedIndex(getTabCount() - 1);
		currentDocument = document;
		document.addSingleDocumentListener(docListener);
		return document;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException if the given {@code newPath} is a path of
	 *                                  some existing {@code SingleDocumentModel}.
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {

		if (newPath == null) {
			newPath = model.getFilePath();
		}
		
		// If the document is already opened
		for (SingleDocumentModel doc : documents) {
			if (doc.equals(model))
				continue;

			Path path = doc.getFilePath();
			if (path != null && doc.getFilePath().equals(newPath))
				throw new IllegalArgumentException("Given path already exists.");

		}
		try {
			Files.writeString(newPath, model.getTextComponent().getText(), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			throw new IllegalArgumentException("Error with saving.");
		}
		setIconAt(getSelectedIndex(), Util.getIcon("saved.png", getTopLevelAncestor()));
		model.setFilePath(newPath);
		model.setModified(false);

	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = getSelectedIndex();
		removeTabAt(index);
		documents.remove(index);
		notifyRemoveDocument(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	/** Listens to documents and changes tab title if the path changes. */
	private SingleDocumentListener docListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			if (model.isModified()) {
				setIconAt(getSelectedIndex(), Util.getIcon("unsaved.png", getTopLevelAncestor()));
			} else {
				setIconAt(getSelectedIndex(), Util.getIcon("saved.png", getTopLevelAncestor()));
			}

		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			Path path = model.getFilePath();
			int index = getSelectedIndex();
			if (path == null) {
				setTitleAt(index, "(unnamed)");
				setToolTipTextAt(index, "(unnamed)");
			} else {
				setTitleAt(getSelectedIndex(), path.getFileName().toString());
				setToolTipTextAt(index, path.toString());
			}
		}
	};

	/**
	 * Notify listeners that a document has been added.
	 * 
	 * @param model document that was added.
	 */
	private void notifyAddDocument(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(model);
		}
	}

	/**
	 * Notify listeners that a document has been removed.
	 * 
	 * @param model document that was removed.
	 */
	private void notifyRemoveDocument(SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
	}

	/**
	 * Notify listeners that current document has been changed.
	 * 
	 * @param oldModel old document.
	 * @param newModel new document.
	 */
	private void notifyChangeDocument(SingleDocumentModel oldModel, SingleDocumentModel newModel) {
		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(oldModel, newModel);
		}
	}

	@Override
	public void activateDocument(SingleDocumentModel model) {
		setSelectedIndex(documents.indexOf(model));
		
	}

}
