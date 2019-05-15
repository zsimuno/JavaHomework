package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo class for {@link PrimListModel} class.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(300, 200);
		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<Integer>(model);
		JList<Integer> list2 = new JList<Integer>(model);
		JPanel lists = new JPanel(new GridLayout(1, 2));
		lists.add(new JScrollPane(list1));
		lists.add(new JScrollPane(list2));

		JButton button = new JButton("Sljedeći");
		button.addActionListener((a) -> {
			model.next();
		});

		getContentPane().add(lists, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.PAGE_END);
	}

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments (not used here).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});

	}
}
