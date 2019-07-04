package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.shapes.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.shapes.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.shapes.LineTool;

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
		lineTool = new LineTool(drawApp.getModel(), drawApp.getCanvas(), drawApp.getFgColorArea(),
				drawApp.getBgColorArea());
		circleTool = new CircleTool(drawApp.getModel(), drawApp.getCanvas(), drawApp.getFgColorArea(),
				drawApp.getBgColorArea());
		filledCircleTool = new FilledCircleTool(drawApp.getModel(), drawApp.getCanvas(), drawApp.getFgColorArea(),
				drawApp.getBgColorArea());
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

			List<String> lines;
			try {
				lines = Files.readAllLines(file);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(drawApp, "Error while reading the file!", "Error!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			List<GeometricalObject> objects;
			try {
				objects = FileParser.parseText(lines);
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(drawApp, "Error while reading the file!", "Error!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			DrawingModel model = drawApp.getModel();
			for (GeometricalObject geometricalObject : objects) {
				model.add(geometricalObject);
			}
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
	private LineTool lineTool;
	/** Tool for drawing non-filled circles. */
	private CircleTool circleTool;
	/** Tool for drawing filled circles. */
	private FilledCircleTool filledCircleTool;

	/** Draw lines. */
	public Action line = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			drawApp.setCurrentTool(lineTool);
		}
	};

	/** Draw non filled circle. */
	public Action circle = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			drawApp.setCurrentTool(circleTool);
		}
	};

	/** Draw filled circle. */
	public Action filledCircle = new AbstractAction() {

		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			drawApp.setCurrentTool(filledCircleTool);
		}
	};

}
