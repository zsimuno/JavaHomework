package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw17.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.color.JColorLabel;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Main program that starts the drawing application.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/** Current tool used in drawing. */
	private Tool currentState;

	/**
	 * Constructor.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1024, 768);
		setTitle("JVDraw");
		initGUI();
		setLocationRelativeTo(null);

	}

	/**
	 * Initializes the user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JMenuBar mb = new JMenuBar();

		JMenu file = new JMenu("File");
		mb.add(file);
//		file.add(new JMenuItem(actions.newDocument));
//		file.add(new JMenuItem(actions.openDocument));
		file.addSeparator();

		setJMenuBar(mb);

		JColorArea fgColorArea = new JColorArea(Color.black);
		JColorArea bgColorArea = new JColorArea(Color.white);
		
		// Set toolbar
		JToolBar tb = new JToolBar();
		tb.add(fgColorArea);
		tb.add(bgColorArea);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(new JToggleButton());
		bg.add(new JToggleButton());
		bg.add(new JToggleButton());

//		tb.add(new JButton(actions.newDocument));

		getContentPane().add(tb, BorderLayout.PAGE_START);

		getContentPane().add(new JColorLabel(fgColorArea, bgColorArea), BorderLayout.PAGE_END);
	}

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments (not used here)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

}
