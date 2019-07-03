package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw17.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw17.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.shapes.Line;

/**
 * Provides actions needed in the {@link JVDraw} application.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ActionsProvider {

	/** Parent of all these actions that must be set before use. */
	public JVDraw drawApp;

	/**
	 * Constructor that sets the parent for all these actions.
	 * 
	 * @param app parent of the all the buttons.
	 */
	public ActionsProvider(JVDraw drawApp) {
		this.drawApp = drawApp;
		initMenuActions();
	}

	/**
	 * Initializes actions that are in the menu.
	 */
	private void initMenuActions() {
		open.putValue(Action.NAME, "Open");
		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		open.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		open.putValue(Action.SHORT_DESCRIPTION, "Open file from disk");

		save.putValue(Action.NAME, "Save");
		save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		save.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		save.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");

		saveAs.putValue(Action.NAME, "Save");
		saveAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAs.putValue(Action.SHORT_DESCRIPTION, "Save file as...");

		export.putValue(Action.NAME, "Export");
		export.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		export.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		export.putValue(Action.SHORT_DESCRIPTION, "Export file");

		exitApplication.putValue(Action.NAME, "Exit");
		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitApplication.putValue(Action.SHORT_DESCRIPTION, "Terminates application");

		line.putValue(Action.NAME, "Line");
		line.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		line.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		line.putValue(Action.SHORT_DESCRIPTION, "Draw lines");

		circle.putValue(Action.NAME, "Circle");
		circle.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		circle.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		circle.putValue(Action.SHORT_DESCRIPTION, "Draw non-filled circle");

		filledCircle.putValue(Action.NAME, "Filled Circle");
		filledCircle.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));
		filledCircle.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
		filledCircle.putValue(Action.SHORT_DESCRIPTION, "Draw filled circle");

	}

	/** Opens a new file from the path that user chooses. */
	public Action open = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			if (jfc.showOpenDialog(drawApp) != JFileChooser.APPROVE_OPTION)
				return;

			Path file = jfc.getSelectedFile().toPath();

			if (!Files.isReadable(file)) {
				JOptionPane.showMessageDialog(drawApp, "Cannot read the file!", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String text;
			try {
				text = Files.readString(file);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(drawApp, "Error while reading the file!", "Error!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// TODO read file
		}
	};

	/** Saves the currently open file. */
	public Action save = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	};

	/** Saves file with the given file name. */
	public Action saveAs = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	};

	/** Export current file. */
	public Action export = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	};

	/** Exits the application. */
	public Action exitApplication = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			drawApp.dispose();
		}
	};

	/** Tool for drawing lines. */
	private Line lineTool = new Line();
	/** Tool for drawing non-filled circles. */
	private Circle circleTool = new Circle();
	/** Tool for drawing filled circles. */
	private FilledCircle filledCircleTool = new FilledCircle();

	/** Draw lines. */
	public Action line = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			drawApp.setCurrentState(lineTool);
		}
	};

	/** Draw non filled circle. */
	public Action circle = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			drawApp.setCurrentState(circleTool);
		}
	};

	/** Draw filled circle. */
	public Action filledCircle = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			drawApp.setCurrentState(filledCircleTool);
		}
	};

}
