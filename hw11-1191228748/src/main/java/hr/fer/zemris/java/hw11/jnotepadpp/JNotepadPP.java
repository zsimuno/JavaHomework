package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.elements.NotepadBars;
import hr.fer.zemris.java.hw11.jnotepadpp.model.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

/**
 * JNotepad++ application is a notepad application that supports various
 * features such as multiple tabs.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 5729111831966518041L;

	/** Component that contains tabs of editors. */
	private DefaultMultipleDocumentModel model;

	/**
	 * Constructor.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1024, 768);
		setTitle("JNotepad++");
		initGUI();
		setLocationRelativeTo(null);

	}

	/**
	 * Initializes the user interface.
	 */
	private void initGUI() {
		// TODO Auto-generated method stub
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		model = new DefaultMultipleDocumentModel();
		cp.add(model, BorderLayout.CENTER);

		NotepadBars bars = new NotepadBars(this, model);
		setJMenuBar(bars.createMenus());
		getContentPane().add(bars.createToolbar(), BorderLayout.PAGE_START);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeApplication();
			}
		});

		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel current = model.getCurrentDocument();
				if (current == null) {
					setTitle(model.getTitleAt(model.getSelectedIndex()) + " - JNotepad++");
				} else {
					setTitle("JNotepad++");
				}
			}
		});

	}

	/**
	 * Checks if there is unsaved files and asks the user if he wants to save them
	 * and then closes the app (unless user aborts).
	 * 
	 */
	public void closeApplication() {
		for (SingleDocumentModel document : model) {
			if (!checkDocumentToSave(document))
				return;
		}
		dispose();
	}

	/** Options for the dialog if the files have been unsaved. */
	private Object[] options = { "Yes", "No", "Abort" };

	/**
	 * Checks if document is modified and prompts user to save it.
	 * 
	 * @return {@code true} if user picked to close or not close and {@code false}
	 *         if user aborted.
	 */
	public boolean checkDocumentToSave(SingleDocumentModel document) {
		if (document.isModified()) {
			Path path = document.getFilePath();

			String fileName = path == null ? "(unnamed) " : path.getFileName().toString();
			int result = JOptionPane.showOptionDialog(JNotepadPP.this,
					fileName + " has been modified. Would you like to save?", "File modified",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			// TODO paziti kada sprema unnamed dokument
			switch (result) {
			case JOptionPane.YES_OPTION:
				model.saveDocument(document, path);
				break;

			case JOptionPane.CANCEL_OPTION:
				return false;
			}
		}
		return true;
	}
	
	public void saveUnnamedDocument(SingleDocumentModel document) {
		// TODO
	}

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
