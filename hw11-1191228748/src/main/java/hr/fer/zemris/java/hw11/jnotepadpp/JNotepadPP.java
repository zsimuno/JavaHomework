package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.elements.NotepadBars;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.model.DefaultMultipleDocumentModel;
 


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
	
	/** Localization provider for this frame. */
	private FormLocalizationProvider flp;

	/**
	 * Constructor.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1024, 768);
		setTitle("JNotepad++");
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		initGUI();
		setLocationRelativeTo(null);

	}

	/**
	 * Initializes the user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		model = new DefaultMultipleDocumentModel();
		cp.add(model, BorderLayout.CENTER);

		NotepadBars bars = new NotepadBars(this, model, flp);
		setJMenuBar(bars.createMenus());
		getContentPane().add(bars.createToolbar(), BorderLayout.PAGE_START);
		getContentPane().add(bars.createStatusBar(), BorderLayout.PAGE_END);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Util.closeApplication(JNotepadPP.this, model, flp);
			}
		});

		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (model.getNumberOfDocuments() == 0 || model.getSelectedIndex() == -1) {
					setTitle("JNotepad++");
				} else {
					setTitle(model.getTitleAt(model.getSelectedIndex()) + " - JNotepad++");
				}
			}
		});

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
