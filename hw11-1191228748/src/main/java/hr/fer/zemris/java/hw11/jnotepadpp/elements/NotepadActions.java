package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Util;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentListener;
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

	/** Localization provider for this application. */
	private FormLocalizationProvider flp;

	/**
	 * List of actions that are dependent on whether there are open files or not.
	 */
	private List<Action> dependentActions = new ArrayList<>();

	/**
	 * Constructs actions that have the given {@code notepad} application as a
	 * context and uses given document model.
	 * 
	 * @param notepad       application that will use actions in this object.
	 * @param documentModel multiple document model used in the application.
	 * @param flp           provider for localization.
	 */
	public NotepadActions(JNotepadPP notepad, MultipleDocumentModel documentModel, FormLocalizationProvider flp) {
		this.notepad = notepad;
		this.model = documentModel;
		this.flp = flp;
		constructActions();
		configureActions();

		
		// These actions depend if there are any documents open so we set initially to disabled
		dependentActions.addAll(Arrays.asList(saveDocument, saveAsDocument, closeDocument, cutSelectedPart,
				copySelectedPart, pasteFromClipboard, toUppercase, toLowercase, invertCase, ascendingSort,
				descendingSort, unique));

		for (Action action : dependentActions) {
			action.setEnabled(false);
		}

		// Listen when number of documents change so change the "enabled" property
		documentModel.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if (documentModel.getNumberOfDocuments() == 0) {
					for (Action action : dependentActions) {
						action.setEnabled(false);
					}
				}

			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				if (documentModel.getNumberOfDocuments() == 1) {
					for (Action action : dependentActions) {
						action.setEnabled(true);
					}
				}

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			}
		});
	}

	/**
	 * Constructs all actions used in the application.
	 */
	private void constructActions() {
		constructFileActions();
		constructEditActions();
		constructStatsActions();
		constructToolsActions();
		constructLanguageActions();
	}

	/**
	 * Constructs file actions.
	 */
	private void constructFileActions() {
		newDocument = new LocalizableAction("new", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		};

		openDocument = new LocalizableAction("open", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Open file");
				if (jfc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION)
					return;

				try {
					model.loadDocument(jfc.getSelectedFile().toPath());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(notepad, "File opening error!", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		};

		saveDocument = new LocalizableAction("save", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel doc = model.getCurrentDocument();
				if (doc.getFilePath() == null) {
					Util.saveAs(notepad, doc, model, flp);
				} else {
					model.saveDocument(doc, null);
				}
			}
		};

		saveAsDocument = new LocalizableAction("saveas", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Util.saveAs(notepad, model.getCurrentDocument(), model, flp);
			}
		};
		
		closeDocument = new LocalizableAction("close", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SingleDocumentModel document = model.getCurrentDocument();
				if (Util.checkDocumentToSave(notepad, document, model, flp)) {
					model.closeDocument(document);
				}
			}
		};

		exitApplication = new LocalizableAction("exit", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Util.closeApplication(notepad, model, flp);

			}
		};

	}

	/**
	 * Constructs edit actions.
	 */
	private void constructEditActions() {
		cutSelectedPart = new LocalizableAction("cut", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setClipboard(true);
			}
		};

		copySelectedPart = new LocalizableAction("copy", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setClipboard(false);
			}
		};

		pasteFromClipboard = new LocalizableAction("paste", flp) {
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

	}

	/**
	 * Constructs stats actions.
	 */
	private void constructStatsActions() {
		statisticalInfo = new LocalizableAction("stats", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getNumberOfDocuments() == 0) {
					JOptionPane.showMessageDialog(notepad, flp.getString("statsnofile"), flp.getString("stats"),
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
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
				String outputText = String.format(flp.getString("statsoutput"), charCount, nonBlankCount, lineCount);
				JOptionPane.showMessageDialog(notepad, outputText, flp.getString("stats"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		};

	}

	/**
	 * Constructs tools actions.
	 */
	private void constructToolsActions() {
		constructCaseActions();

		ascendingSort = new LocalizableAction("ascending", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Locale locale = new Locale(flp.getCurrentLanguage());
				Collator collator = Collator.getInstance(locale);
				sortLines(collator::compare);
			}
		};

		descendingSort = new LocalizableAction("descending", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Locale locale = new Locale(flp.getCurrentLanguage());
				Collator collator = Collator.getInstance(locale);
				sortLines((a, b) -> collator.compare(b, a));
			}
		};

		unique = new LocalizableAction("unique", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeSelectedLines(new NotepadActions.LineChanger() {

					@Override
					public List<String> changeLines(List<String> list, int startingLine, int endingLine) {
						List<String> previousLines = new ArrayList<>();
						for (int i = startingLine; i <= endingLine; i++) {
							if (previousLines.contains(list.get(i))) {
								list.remove(i);
								i--;
								endingLine--;
							} else {
								previousLines.add(list.get(i));
							}
						}
						return list;
					}
				});

			}
		};

	}

	/**
	 * Constructs actions that change casing.
	 */
	private void constructCaseActions() {
		toUppercase = new LocalizableAction("upper", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				toggleSelectedText(Character::toUpperCase);
			}
		};

		toLowercase = new LocalizableAction("lower", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				toggleSelectedText(Character::toLowerCase);
			}
		};

		invertCase = new LocalizableAction("invertcase", flp) {
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

		CaretListener listener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				updateCaseEnabled((JTextComponent) e.getSource());
			}

		};

		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				model.getTextComponent().removeCaretListener(listener);

			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				model.getTextComponent().addCaretListener(listener);

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				previousModel.getTextComponent().removeCaretListener(listener);
				currentModel.getTextComponent().addCaretListener(listener);
				updateCaseEnabled(currentModel.getTextComponent());
			}
		});

	}

	/**
	 * Updates the case actions "enabled" property based on the given textual
	 * component.
	 * 
	 * @param textComp textual component.
	 */
	private void updateCaseEnabled(JTextComponent textComp) {
		Caret caret = textComp.getCaret();
		if (caret.getDot() == caret.getMark()) {
			setCaseEnabled(false);
		} else {
			setCaseEnabled(true);
		}
	}

	/**
	 * Sets "enabled" properties of actions that change casing.
	 * 
	 * @param enabled enable ({@code true}) or not ({@code false}).
	 */
	private void setCaseEnabled(boolean enabled) {
		toUppercase.setEnabled(enabled);
		toLowercase.setEnabled(enabled);
		invertCase.setEnabled(enabled);
	}

	/**
	 * Constructs actions that change the language.
	 */
	private void constructLanguageActions() {
		hr = new LocalizableAction("hr", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};

		en = new LocalizableAction("en", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};

		de = new LocalizableAction("de", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};

	}

	/**
	 * Configures actions created in this class.
	 */
	private void configureActions() {
		configureFileActions();
		configureEditActions();
		configureStatsActions();
		configureToolsActions();
		configureLanguageActions();
	}

	/**
	 * Configures actions that are in the "File" menu.
	 */
	private void configureFileActions() {
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("newFile.png", notepad));

		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("openFile.png", notepad));

		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("saveFile.png", notepad));

		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("saveAs.png", notepad));

		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeDocument.putValue(Action.LARGE_ICON_KEY, Util.getIcon("closeFile.png", notepad));

		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitApplication.putValue(Action.LARGE_ICON_KEY, Util.getIcon("exit.png", notepad));
	}

	/**
	 * Configures actions that are in the "Edit" menu.
	 */
	private void configureEditActions() {
		cutSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		cutSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Cut selected part");
		cutSelectedPart.putValue(Action.LARGE_ICON_KEY, Util.getIcon("cut.png", notepad));

		copySelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copySelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copySelectedPart.putValue(Action.LARGE_ICON_KEY, Util.getIcon("copy.png", notepad));

		pasteFromClipboard.putValue(Action.NAME, "Paste");
		pasteFromClipboard.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteFromClipboard.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteFromClipboard.putValue(Action.LARGE_ICON_KEY, Util.getIcon("paste.png", notepad));
	}

	/**
	 * Configures actions that are in the "Stats" menu.
	 */
	private void configureStatsActions() {
		statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		statisticalInfo.putValue(Action.LARGE_ICON_KEY, Util.getIcon("stats.png", notepad));
	}

	/**
	 * Configures actions that are in the "Tools" menu.
	 */
	private void configureToolsActions() {
		toUppercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUppercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		toLowercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		invertCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		ascendingSort.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
		ascendingSort.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		descendingSort.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift D"));
		descendingSort.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

	}

	/**
	 * Configures actions that are in the "Language" menu.
	 */
	private void configureLanguageActions() {
		hr.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		en.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		de.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

	}

	/**
	 * Sets the content on the clipboard to the selected text and removes the text
	 * if necessary.
	 * 
	 * @param remove boolean that determines whether to remove the selected text
	 *               from the document or not.
	 */
	private void setClipboard(boolean remove) {
		JTextComponent textArea = model.getCurrentDocument().getTextComponent();
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

	/**
	 * Toggles the currently selected text in the editor with the given operator.
	 * 
	 * @param operator operator that changes characters in the selected text.
	 */
	private void toggleSelectedText(UnaryOperator<Character> operator) {
		JTextComponent editor = model.getCurrentDocument().getTextComponent();
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
	 * Interface used by actions that change line of given text (sorting and such).
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private interface LineChanger {

		/**
		 * Change the lines.
		 * 
		 * @param list         list of line.
		 * @param startingLine first line we change.
		 * @param endingLine   last line we change.
		 * @return changed list of lines.
		 */
		List<String> changeLines(List<String> list, int startingLine, int endingLine);
	}

	/**
	 * Changes selected lines of the text based on the given line changer.
	 * 
	 * @param changer says how we change the given lines.
	 */
	private void changeSelectedLines(LineChanger changer) {
		JTextComponent c = model.getCurrentDocument().getTextComponent();
		Document document = c.getDocument();
		Element root = document.getDefaultRootElement();
		Caret caret = c.getCaret();
		int startPos = Math.min(caret.getDot(), caret.getMark());
		int endPos = Math.max(caret.getDot(), caret.getMark());

		int startLine = root.getElementIndex(startPos);
		int endLine = root.getElementIndex(endPos);

		List<String> lines = new ArrayList<>(Arrays.asList(c.getText().split("\\r?\\n")));

		List<String> result = changer.changeLines(lines, startLine, endLine);

		String text = String.join("\n", result);

		c.setText(text);

	}

	/**
	 * Sort the selected lines with the given comparator.
	 * 
	 * @param comparator comparator with which we compare lines with.
	 */
	private void sortLines(Comparator<String> comparator) {
		changeSelectedLines(new NotepadActions.LineChanger() {

			@Override
			public List<String> changeLines(List<String> list, int startingLine, int endingLine) {
				List<String> sort = new ArrayList<>();
				for (; startingLine <= endingLine; endingLine--) {
					sort.add(list.get(startingLine));
					list.remove(startingLine);
				}

				list.addAll(startingLine, sort.stream().sorted(comparator).collect(Collectors.toList()));
				return list;
			}
		});
	}

	/** Opens a new blank document. */
	public Action newDocument;

	/** Opens a new document from the path that user chooses. */
	public Action openDocument;

	/** Saves the currently open document. */
	public Action saveDocument;

	/** Saves document with the given file name. */
	public Action saveAsDocument;

	/** Closes current document. */
	public Action closeDocument;

	/** Cut selected part of text. */
	public Action cutSelectedPart;

	/** Copy selected part of text. */
	public Action copySelectedPart;

	/** Paste to text. */
	public Action pasteFromClipboard;

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
	public Action statisticalInfo;

	/** Exits the application. */
	public Action exitApplication;

	/** Sets the selection to uppercase. */
	public Action toUppercase;

	/** Sets the selection to lowercase. */
	public Action toLowercase;

	/** Inverts the case of the selection. */
	public Action invertCase;

	/**
	 * Applies ascending sort only on the selected lines of text using rules of
	 * currently defined language.
	 */
	public Action ascendingSort;

	/**
	 * Applies descending sort only on the selected lines of text using rules of
	 * currently defined language.
	 */
	public Action descendingSort;

	/**
	 * Removes from selection all lines which are duplicates (only the first
	 * occurrence is retained).
	 */
	public Action unique;

	/** Changes language to Croatian. */
	public Action hr;

	/** Changes language to English. */
	public Action en;

	/** Changes language to German. */
	public Action de;

}
