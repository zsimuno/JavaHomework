package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw17.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.color.JColorLabel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Main program that starts the drawing application.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1440, 900);
		setLocationRelativeTo(null);
		setTitle("JVDraw");
		initGUI();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Util.closeApplication(JVDraw.this);
			}
		});

	}

	/**
	 * Initializes the user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout(1, 10));

		ActionsProvider actions = new ActionsProvider(this);

		JMenuBar mb = new JMenuBar();

		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(actions.open));
		file.addSeparator();
		file.add(new JMenuItem(actions.save));
		file.add(new JMenuItem(actions.saveAs));
		file.add(new JMenuItem(actions.export));
		file.addSeparator();
		file.add(new JMenuItem(actions.exitApplication));

		setJMenuBar(mb);

		// Set toolbar
		JToolBar tb = new JToolBar();
		tb.setFloatable(false);
		tb.add(fgColorArea);
		tb.addSeparator();
		tb.add(bgColorArea);
		tb.addSeparator();

		ButtonGroup bg = new ButtonGroup();
		JToggleButton lineButton = new JToggleButton(actions.line);
		lineButton.setSelected(true);
		JToggleButton circleButton = new JToggleButton(actions.circle);
		JToggleButton filledCircleButton = new JToggleButton(actions.filledCircle);
		bg.add(lineButton);
		bg.add(circleButton);
		bg.add(filledCircleButton);

		tb.add(lineButton);
		tb.add(circleButton);
		tb.add(filledCircleButton);

		cp.add(tb, BorderLayout.PAGE_START);

		cp.add(canvas, BorderLayout.CENTER);

		JObjectsList objectList = new JObjectsList(new DrawingObjectListModel(model), this);

		cp.add(new JScrollPane(objectList), BorderLayout.LINE_END);

		cp.add(new JColorLabel(fgColorArea, bgColorArea), BorderLayout.PAGE_END);
	}

	/** Drawing model. */
	private DrawingModel model = new DrawingModelImpl();

	/** Current tool used in drawing. */
	private Tool currentTool;

	/** Canvas we draw on. */
	private JDrawingCanvas canvas = new JDrawingCanvas(this);

	/** fg color provider */
	private JColorArea fgColorArea = new JColorArea(Color.black);
	/** bg color provider */
	private JColorArea bgColorArea = new JColorArea(Color.white);

	/** Path to the file if it has been already saved. */
	private Path savedFile = null;

	/**
	 * @return the fgColorArea
	 */
	public JColorArea getFgColorArea() {
		return fgColorArea;
	}

	/**
	 * @return the bgColorArea
	 */
	public JColorArea getBgColorArea() {
		return bgColorArea;
	}

	/**
	 * @return the canvas
	 */
	public JDrawingCanvas getCanvas() {
		return canvas;
	}

	/**
	 * @return the drawing model
	 */
	public DrawingModel getModel() {
		return model;
	}

	/**
	 * Set new current state.
	 * 
	 * @param state new state
	 */
	public void setCurrentTool(Tool state) {
		this.currentTool = state;
	}

	/**
	 * 
	 * @return current state
	 */
	public Tool getCurrentTool() {
		return currentTool;
	}

	/**
	 * @return the path to where this drawing is saved or {@code null} if the file
	 *         has not been saved
	 */
	public Path getSavedFile() {
		return savedFile;
	}

	/**
	 * @param savedFile the path to where this drawing is saved to set
	 */
	public void setSavedFile(Path savedFile) {
		this.savedFile = savedFile;
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
