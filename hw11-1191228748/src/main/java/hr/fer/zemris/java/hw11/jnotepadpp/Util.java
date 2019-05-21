package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Container;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

/**
 * Contains utility methods used by various classes.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Util {
	/**
	 * Checks if there is unsaved files and asks the user if he wants to save them
	 * and then closes the app (unless user aborts).
	 * 
	 */
	/**
	 * @param notepad notepad in which the documents are open.
	 * @param model   model that contains documents that are open in the notepad.
	 */
	public static void closeApplication(JNotepadPP notepad, MultipleDocumentModel model) {
		for (SingleDocumentModel document : model) {
			if (!checkDocumentToSave(notepad, document, model))
				return;
		}
		notepad.dispose();
	}

	/** Options for the dialog if the files have been unsaved. */
	private static Object[] options = { "Yes", "No", "Abort" };

	/**
	 * Checks if document is modified and prompts user to save it.
	 * 
	 * @param notepad  notepad in which the documents are open.
	 * @param document document to check and save if needed.
	 * @param model    model that contains documents that are open in the notepad.
	 * @return {@code true} if user picked to close or not close and {@code false}
	 *         if user aborted.
	 */
	public static boolean checkDocumentToSave(JNotepadPP notepad, SingleDocumentModel document,
			MultipleDocumentModel model) {
		if (document.isModified()) {
			Path path = document.getFilePath();

			String fileName = path == null ? "(unnamed) " : path.getFileName().toString();
			int result = JOptionPane.showOptionDialog(notepad, fileName + " has been modified. Would you like to save?",
					"File modified", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[0]);

			switch (result) {
			case JOptionPane.YES_OPTION:
				if (path == null) {
					saveAs(notepad, document, model);
				} else {
					model.saveDocument(document, path);
				}
				break;

			case JOptionPane.CANCEL_OPTION:
				return false;
			}
		}
		return true;
	}

	/**
	 * Saves the document with the path and name given by the user.
	 * 
	 * @param notepad  notepad in which the documents are open.
	 * @param document document to save.
	 * @param model    model that contains documents that are open in the notepad.
	 */
	public static void saveAs(JNotepadPP notepad, SingleDocumentModel document, MultipleDocumentModel model) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save file as...");
		if (jfc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(notepad, "File is not saved.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Path newPath = jfc.getSelectedFile().toPath();
		if (Files.exists(newPath)) {
			JOptionPane.showMessageDialog(notepad, "File already exists!", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		model.saveDocument(document, newPath);

		JOptionPane.showMessageDialog(notepad, "Document saved", "Info", JOptionPane.INFORMATION_MESSAGE);

	}

	/**
	 * Gets the icon as {@link ImageIcon} object with the given {@code fileName}.
	 * 
	 * @param fileName  file name of the icon.
	 * @param container container of the component where the icon goes to.
	 * @return icon with the given file name.
	 */
	public static ImageIcon getIcon(String fileName, Container container) {

		byte[] bytes;
		try (InputStream is = container.getClass().getResourceAsStream("icons/" + fileName)) {
			if (is == null) {
				throw new IllegalArgumentException("No file with the name " + fileName);
			}
			bytes = is.readAllBytes();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return new ImageIcon(bytes);
	}
}