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
import hr.fer.zemris.java.hw11.jnotepadpp.model.DefaultMultipleDocumentModel;
 

// TODO save existing file?
// TODO closing unnamed that is unmodified doesn't work 
// TODO ne koristim multiple document listener. Bolje ovo sve implementirati.
// TODO mozda da novi tab koji otvori bude i oni na koji je fokusiran
// TODO mozda treba paziti da bolje prati current file u modelu
// TODO akcije ako nema otvorenih tabova (npr close ili stats)
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
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		model = new DefaultMultipleDocumentModel();
		cp.add(model, BorderLayout.CENTER);

		NotepadBars bars = new NotepadBars(this, model);
		setJMenuBar(bars.createMenus());
		getContentPane().add(bars.createToolbar(), BorderLayout.PAGE_START);
		getContentPane().add(bars.createStatusBar(), BorderLayout.PAGE_END);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Util.closeApplication(JNotepadPP.this, model);
			}
		});

		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (model.getNumberOfDocuments() == 0) {
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
