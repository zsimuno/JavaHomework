package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

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
	List<MultipleDocumentListener> listeners = new ArrayList<>();

	public DefaultMultipleDocumentModel() {
		super();
	}

	public DefaultMultipleDocumentModel(int tabPlacement) {
		super(tabPlacement);
	}

	public DefaultMultipleDocumentModel(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
		documents.add(doc);
		this.addTab("(unnamed)", new JScrollPane(doc.getTextComponent()));
		doc.addSingleDocumentListener(docListener);
		return doc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return documents.get(getSelectedIndex());
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

		for (int i = 0; i < documents.size(); i++) {
			SingleDocumentModel doc = documents.get(i);
			if (doc.getFilePath().equals(path)) {
				setSelectedIndex(i);
				return doc;
			}
		}

		if (!Files.isReadable(path)) {
			throw new IllegalArgumentException("Given path must be readable.");
		}

		String text;
		try {
			text = Files.readString(path);
		} catch (IOException e1) {
			throw new IllegalArgumentException("Can't read from file.");
		}
		SingleDocumentModel document = new DefaultSingleDocumentModel(path, text);
		documents.add(document);
		this.addTab(path.getFileName().toString(), new JScrollPane(document.getTextComponent()));
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
		
		if(newPath == null) {
			newPath = model.getFilePath();
		}
		for (SingleDocumentModel doc : documents) {
			Path path = doc.getFilePath();
			if (path != null && doc.getFilePath().equals(newPath))
				throw new IllegalArgumentException("Given path already exists.");

		}
		SingleDocumentModel current = documents.get(getSelectedIndex());
		try {
			Files.writeString(newPath, current.getTextComponent().getText());
		} catch (IOException e1) {
			throw new IllegalArgumentException("Error with saving.");
		}

		current.setFilePath(newPath);

	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		removeTabAt(documents.indexOf(model));
		documents.remove(model);
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

	private SingleDocumentListener docListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			Icon icon = null;
			if (model.isModified()) {
				// TODO modified or not icons
			} else {

			}
			setIconAt(getSelectedIndex(), icon);

		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			Path path = model.getFilePath();
			if (path == null) {
				setTitleAt(getSelectedIndex(), "(unnamed)");
			} else {
				setTitleAt(getSelectedIndex(), path.getFileName().toString());
			}
		}
	};

}
