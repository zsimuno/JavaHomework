package hr.fer.zemris.java.gui.layouts.demo;

import static hr.fer.zemris.java.gui.layouts.demo.DemoFrame1.l;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.*;

/**
 * Demo class for {@link CalcLayout}.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DemoFrame2 extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DemoFrame2() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(200, 500));
		initGUI();
		pack();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(l("x"), new RCPosition(1, 1));
		p.add(l("y"), new RCPosition(2, 3));
		p.add(l("z"), new RCPosition(2, 7));
		p.add(l("w"), new RCPosition(4, 2));
		p.add(l("a"), new RCPosition(4, 5));
		p.add(l("b"), new RCPosition(4, 7));

		getContentPane().add(p);
	}

	/**
	 * Method that starts the program.
	 * 
	 * @param args command line arguments (not used here).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame2().setVisible(true);
		});
	}
}