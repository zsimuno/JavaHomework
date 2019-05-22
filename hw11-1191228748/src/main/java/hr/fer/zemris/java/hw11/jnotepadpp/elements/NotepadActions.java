package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.function.UnaryOperator;

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
import hr.fer.zemris.java.hw11.jnotepadpp.Util;
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

	/** System clipboard. */
	private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

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

		configureFileActions();

		configureEditActions();

		configureStatsActions();

		configureToolsActions();

	}

	/**
	 * Configures actions that are in the "File" menu.
	 */
	private void configureFileActions() {
		newDocument.putValue(Action.NAME, "New");
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocument.putValue(Action.SHORT_DESCRIPTION, "Opens a new blank file");
		newDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("newFile.png", notepad));

		openDocument.putValue(Action.NAME, "Open");
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.SHORT_DESCRIPTION, "Open file from disk");
		openDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("openFile.png", notepad));

		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
		saveDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("saveFile.png", notepad));

		saveAsDocument.putValue(Action.NAME, "Save As...");
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk with given name");
		saveAsDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("saveAs.png", notepad));

		closeDocument.putValue(Action.NAME, "Close");
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeDocument.putValue(Action.SHORT_DESCRIPTION, "Closes current file");
		closeDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("closeFile.png", notepad));

		exitApplication.putValue(Action.NAME, "Exit");
		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitApplication.putValue(Action.SHORT_DESCRIPTION, "Terminates application");
		exitApplication.putValue(Action.LARGE_ICON_KEY, Util.getIcon("exit.png", notepad));
	}

	/**
	 * Configures actions that are in the "Edit" menu.
	 */
	private void configureEditActions() {
		cutSelectedPart.putValue(Action.NAME, "Cut");
		cutSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		cutSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Cut selected part");
		cutSelectedPart.putValue(Action.LARGE_ICON_KEY, Util.getIcon("cut.png", notepad));

		copySelectedPart.putValue(Action.NAME, "Copy");
		copySelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copySelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copySelectedPart.putValue(Action.SHORT_DESCRIPTION, "Copy selected part");
		copySelectedPart.putValue(Action.LARGE_ICON_KEY, Util.getIcon("copy.png", notepad));

		pasteFromClipboard.putValue(Action.NAME, "Paste");
		pasteFromClipboard.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteFromClipboard.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteFromClipboard.putValue(Action.SHORT_DESCRIPTION, "Paste to selected position");
		pasteFromClipboard.putValue(Action.LARGE_ICON_KEY, Util.getIcon("paste.png", notepad));
	}

	/**
	 * Configures actions that are in the "Stats" menu.
	 */
	private void configureStatsActions() {
		statisticalInfo.putValue(Action.NAME, "Stats");
		statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		statisticalInfo.putValue(Action.SHORT_DESCRIPTION, "Statistical info");
		statisticalInfo.putValue(Action.LARGE_ICON_KEY, Util.getIcon("stats.png", notepad));
	}

	/**
	 * Configures actions that are in the "Tools" menu.
	 */
	private void configureToolsActions() {

		// TODO configure descriptions and acc key
		toUppercase.putValue(Action.NAME, "To Uppercase");
		toUppercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUppercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUppercase.putValue(Action.SHORT_DESCRIPTION, "Sets the selection to uppercase");

		toLowercase.putValue(Action.NAME, "To Lowercase");
		toLowercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		toLowercase.putValue(Action.SHORT_DESCRIPTION, "Sets the selection to lowercase");

		invertCase.putValue(Action.NAME, "Invert Case");
		invertCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		invertCase.putValue(Action.SHORT_DESCRIPTION, "Inverts the case of the selection");

		ascendingSort.putValue(Action.NAME, "Ascending");
		ascendingSort.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
		ascendingSort.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		ascendingSort.putValue(Action.SHORT_DESCRIPTION, "Applies ascending sort on the selected lines");

		descendingSort.putValue(Action.NAME, "Descending");
		descendingSort.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift D"));
		descendingSort.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		descendingSort.putValue(Action.SHORT_DESCRIPTION, "Applies descending sort on the selected lines");

		removeDuplicates.putValue(Action.NAME, "Unique");
		removeDuplicates.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		removeDuplicates.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		removeDuplicates.putValue(Action.SHORT_DESCRIPTION, "Removes from selection all lines which are duplicates");

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
			if (doc.getFilePath() == null) {
				Util.saveAs(notepad, doc, model);
			} else {
				model.saveDocument(doc, null);
			}
		}
	};

	/** Saves document with the given file name. */
	public final Action saveAsDocument = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Util.saveAs(notepad, model.getCurrentDocument(), model);
		}
	};

	/** Closes current document. */
	public final Action closeDocument = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel document = model.getCurrentDocument();
			if (Util.checkDocumentToSave(notepad, document, model)) {
				model.closeDocument(document);
			}
		}
	};

	/** Cut selected part of text. */
	public final Action cutSelectedPart = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setClipboard(true);
		}
	};

	/** Copy selected part of text. */
	public final Action copySelectedPart = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setClipboard(false);
		}
	};

	/**
	 * Sets the content on the clipboard to the selected text and removes the text
	 * if necessary.
	 * 
	 * @param remove boolean that determines whether to remove the selected text
	 *               from the document or not.
	 */
	private void setClipboard(boolean remove) {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();
		Caret caret = textArea.getCaret();
		try {
			int length = Math.abs(caret.getDot() - caret.getMark());
			if (length == 0)
				return;
			int offset = Math.min(caret.getDot(), caret.getMark());
			clipboard.setContents(new StringSelection(doc.getText(offset, length)), null);
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

			if (!clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
				return;

			String text;
			try {
				text = (String) clipboard.getData(DataFlavor.stringFlavor);
			} catch (Exception exc) {
				return;
			}
			JTextArea textArea = model.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			Caret caret = textArea.getCaret();
			try {
				doc.insertString(caret.getDot(), text, null);
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
			JTextArea text = model.getCurrentDocument().getTextComponent();
			char[] textArray = text.getText().toCharArray();
			int charCount = textArray.length;
			int nonBlankCount = 0;
			for (int i = 0; i < charCount; i++) {
				if (!Character.isWhitespace(textArray[i])) {
					nonBlankCount++;
				}
			}
			int lineCount = text.getLineCount();
			String outputText = "Your document has " + charCount + " characters, " + nonBlankCount
					+ " non-blank characters and " + lineCount + " lines.";
			JOptionPane.showMessageDialog(notepad, outputText, "Stats", JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/** Exits the application. */
	public final Action exitApplication = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Util.closeApplication(notepad, model);

		}
	};

	/** Sets the selection to uppercase. */
	public final Action toUppercase = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			toggleSelectedText(Character::toUpperCase);
		}
	};

	/** Sets the selection to lowercase. */
	public final Action toLowercase = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			toggleSelectedText(Character::toLowerCase);
		}
	};

	/** Inverts the case of the selection. */
	public final Action invertCase = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			toggleSelectedText((c) -> {
				if (Character.isUpperCase(c)) {
					return Character.toLowerCase(c);
				} else if (Character.isLowerCase(c)) {
					return Character.toUpperCase(c);
				}
				return c;

			});
		}
	};

	/**
	 * Toggles the currently selected text in the editor with the given operator.
	 * 
	 * @param operator operator that changes characters in the selected text.
	 */
	private void toggleSelectedText(UnaryOperator<Character> operator) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document document = editor.getDocument();
		Caret caret = editor.getCaret();

		int start = Math.min(caret.getDot(), caret.getMark());
		int len = Math.abs(caret.getDot() - caret.getMark());

		if (len == 0)
			return;

		String text;
		try {
			text = document.getText(start, len);
			char[] chars = text.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				chars[i] = operator.apply(chars[i]);
			}
			text = new String(chars);

			document.remove(start, len);
			document.insertString(start, text, null);
		} catch (BadLocationException ignorable) {
		}

	}

	/**
	 * Applies ascending sort only on the selected lines of text using rules of
	 * currently defined language.
	 */
	public final Action ascendingSort = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO ascendingSort
		}
	};

	/**
	 * Applies descending sort only on the selected lines of text using rules of
	 * currently defined language.
	 */
	public final Action descendingSort = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO descendingSort
		}
	};

	/**
	 * Removes from selection all lines which are duplicates (only the first
	 * occurrence is retained).
	 */
	public final Action removeDuplicates = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO removeDuplicates
		}
	};

}
