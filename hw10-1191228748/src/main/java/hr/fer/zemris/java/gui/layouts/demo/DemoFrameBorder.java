package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import static hr.fer.zemris.java.gui.layouts.demo.DemoFrame1.l;

public class DemoFrameBorder extends JFrame {

	private static final long serialVersionUID = 1L;

	public DemoFrameBorder() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	private void initGUI() {
		Container cp = getContentPane();
		((JPanel) cp).setBorder(BorderFactory.createLineBorder(Color.RED, 20));
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrameBorder().setVisible(true);
		});
	}
}