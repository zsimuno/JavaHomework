package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

/**
 * Contains all actions used in JNotepad++ application.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class NotepadActions {

	/** Application that will use actions in this object. */
	private JNotepadPP notepad;

	/** Multiple document model used in the application. */
	private MultipleDocumentModel model;

	/** Represents a clipboard for cut/copy/paste. */
	private String clipboard = "";

	/**
	 * Constructs actions that have the given {@code notepad} application as a
	 * context and uses given document model.
	 * 
	 * @param notepad       application that will use actions in this object.
	 * @param documentModel multiple document model used in the application.
	 */
	public NotepadActions(JNotepadPP notepad, MultipleDocumentModel documentModel) {
		this.notepad = notepad;
		this.model = documentModel;
		configureActions();
	}

	/**
	 * Configures actions created in this class.
	 */
	private void configureActions() {
		// TODO check action configurations.
		newDocument.putValue(Action.NAME, "New");
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocument.putValue(Action.SHORT_DESCRIPTION, "Opens a new blank file");

		openDocument.putValue(Action.NAME, "Open");
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.SHORT_DESCRIPTION, "Open file from disk");

		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");

		saveAsDocument.putValue(Action.NAME, "Save As...");
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S")); // TODO
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk with given name");

		closeDocument.putValue(Action.NAME, "Close");
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeDocument.putValue(Action.SHORT_DESCRIPTION, "Closes current file");

		cutSelectedPart.putValue(Action.NAME, "Cut");
		cutSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		cutSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Cut selected part");

		copySelectedPart.putValue(Action.NAME, "Copy");
		copySelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copySelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copySelectedPart.putValue(Action.SHORT_DESCRIPTION, "Copy selected part");

		pasteFromClipboard.putValue(Action.NAME, "Paste");
		pasteFromClipboard.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteFromClipboard.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteFromClipboard.putValue(Action.SHORT_DESCRIPTION, "Paste to selected position");

		statisticalInfo.putValue(Action.NAME, "Stats");
		statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));
		statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		statisticalInfo.putValue(Action.SHORT_DESCRIPTION, "Statistical info");

		exitApplication.putValue(Action.NAME, "Exit");
		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitApplication.putValue(Action.SHORT_DESCRIPTION, "Terminates application");

	}

	/** Opens a new blank document. */
	public final Action newDocument = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};

	/** Opens a new document from the path that user chooses. */
	public final Action openDocument = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			if (jfc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION)
				return;

			model.loadDocument(jfc.getSelectedFile().toPath());

		}
	};

	/** Saves the currently open document. */
	public final Action saveDocument = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel doc = model.getCurrentDocument();
			// TODO unnamed document
			model.saveDocument(doc, null);
		}
	};

	/** Saves document with the given file name. */
	public final Action saveAsDocument = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save file as...");
			if (jfc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(notepad, "File is not saved.", "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Path newPath = jfc.getSelectedFile().toPath();

			model.saveDocument(model.getCurrentDocument(), newPath);
			JOptionPane.showMessageDialog(notepad, "Document saved", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/** Closes current document. */
	public final Action closeDocument = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel document = model.getCurrentDocument();
			if (notepad.checkDocumentToSave(document)) {
				model.closeDocument(document);
			}
		}
	};

	/** Cut selected part of text. */
	public final Action cutSelectedPart = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO cut
			setClipboard(true);
		}
	};

	/** Copy selected part of text. */
	public final Action copySelectedPart = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO copy
			setClipboard(false);
		}
	};

	private void setClipboard(boolean remove) {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();
		Caret caret = textArea.getCaret();
		try {
			int length = Math.abs(caret.getDot() - caret.getMark());
			if (length == 0)
				return;
			int offset = Math.min(caret.getDot(), caret.getMark());
			clipboard = doc.getText(offset, length);
			if (remove) {
				doc.remove(offset, length);
			}
		} catch (BadLocationException ignorable) {
		}
	}

	/** Paste to text. */
	public final Action pasteFromClipboard = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO paste (clipboard as local var or?)
			JTextArea textArea = model.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			Caret caret = textArea.getCaret();
			try {
				doc.insertString(caret.getDot(), clipboard, null);
			} catch (BadLocationException ignorable) {
			}
		}
	};

	/**
	 * Show statistical info. <br>
	 * Shows:
	 * <ul>
	 * <li>a number of characters found in document (everything counts)</li>
	 * <li>a number of non-blank characters found in document (you don't count
	 * spaces, enters and tabs)</li>
	 * <li>a number of lines that the document contains</li>
	 * </ul>
	 */
	public final Action statisticalInfo = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO stats
		}
	};

	/** Exits the application. */
	public final Action exitApplication = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			notepad.closeApplication();
		}
	};
}
