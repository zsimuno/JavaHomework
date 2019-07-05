package hr.fer.zemris.java.hw17.jvdraw;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Contains utility methods used by various classes.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Util {

	/**
	 * Checks if the model is saved before closing.
	 * 
	 * @param drawApp drawing application
	 */
	public static void closeApplication(JVDraw drawApp) {
		if (drawApp.getModel().isModified()) {
			int result = JOptionPane.showOptionDialog(drawApp, "Drawing has been modified. Do you want to save?",
					"Modified", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (result == JOptionPane.YES_OPTION) {
				save(drawApp);
			} else if (result == JOptionPane.CANCEL_OPTION) {
				return;
			}

		}
		drawApp.dispose();
	}

	/**
	 * Save file if already saved but ask user otherwise.
	 * 
	 * @param drawApp drawing application
	 */
	public static void save(JVDraw drawApp) {

		Path file = drawApp.getSavedFile();
		// if not saved already
		if (file == null) {
			saveAs(drawApp);
			return;
		}

		try {
			Files.writeString(file, FileParser.createText(drawApp.getModel()), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(drawApp, "File not saved", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		drawApp.getModel().clearModifiedFlag();
	}

	/**
	 * Save as the file that user picks.
	 * 
	 * @param drawApp drawing application
	 */
	public static void saveAs(JVDraw drawApp) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save file as...");
		Path newPath;
		while (true) {
			if (jfc.showSaveDialog(drawApp) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(drawApp, "File not saved", "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			newPath = jfc.getSelectedFile().toPath();
			String fileName = newPath.getFileName().toString();
			String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

			if (!ext.toLowerCase().equals("jvd")) {
				JOptionPane.showMessageDialog(drawApp, "Invalid extension. Must be .jvd", "Error",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}

			break;
		}

		try {
			Files.writeString(newPath, FileParser.createText(drawApp.getModel()), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(drawApp, "File not saved", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(drawApp, "File saved!", "Saved", JOptionPane.INFORMATION_MESSAGE);

		drawApp.getModel().clearModifiedFlag();
		drawApp.setSavedFile(newPath);

	}

}
