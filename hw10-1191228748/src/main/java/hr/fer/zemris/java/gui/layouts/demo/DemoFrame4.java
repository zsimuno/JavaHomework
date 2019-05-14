package hr.fer.zemris.java.gui.layouts.demo;

import static hr.fer.zemris.java.gui.layouts.demo.DemoFrame1.l;
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
public class DemoFrame4 extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DemoFrame4() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(216,200);
		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(l("1,1"), new RCPosition(1, 1));
		p.add(l("1,6"), new RCPosition(1, 6));
		p.add(l("1,7"), new RCPosition(1, 7));
		for (int i = 2; i < 6; i++) {
			for (int j = 1; j < 8; j++) {
				p.add(l(i + "," + j), new RCPosition(i, j));
			}
		}
		getContentPane().add(p);
	}


	/**
	 * Method that starts the program.
	 * 
	 * @param args command line arguments (not used here).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame4().setVisible(true);
		});
	}
}