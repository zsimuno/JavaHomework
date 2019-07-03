package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw17.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.color.JColorLabel;
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
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(1024, 768);
		setTitle("JVDraw");
		initGUI();

	}

	/**
	 * Initializes the user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

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

		JColorArea fgColorArea = new JColorArea(Color.black);
		JColorArea bgColorArea = new JColorArea(Color.white);

		// Set toolbar
		JToolBar tb = new JToolBar();
		tb.setFloatable(false);
		tb.add(fgColorArea);
		tb.addSeparator();
		tb.add(bgColorArea);
		tb.addSeparator();

		ButtonGroup bg = new ButtonGroup();
		JToggleButton lineButton = new JToggleButton(actions.line);
		JToggleButton circleButton = new JToggleButton(actions.circle);
		JToggleButton filledCircleButton = new JToggleButton(actions.filledCircle);
		bg.add(lineButton);
		bg.add(circleButton);
		bg.add(filledCircleButton);

		tb.add(lineButton);
		tb.add(circleButton);
		tb.add(filledCircleButton);

		cp.add(tb, BorderLayout.PAGE_START);
		
		JDrawingCanvas canvas = new JDrawingCanvas();
		canvas.addMouseListener(drawingMouseListener);
		cp.add(canvas, BorderLayout.CENTER);

		cp.add(new JColorLabel(fgColorArea, bgColorArea), BorderLayout.PAGE_END);
	}

	/** Current tool used in drawing. */
	private Tool currentState;

	/**
	 * Set new current state.
	 * 
	 * @param state new state
	 */
	public void setCurrentState(Tool state) {
		this.currentState = state;
	}

	private MouseListener drawingMouseListener = new MouseAdapter() {

		public void mouseClicked(MouseEvent e) {
			currentState.mouseClicked(e);
		};

		public void mouseDragged(MouseEvent e) {
			currentState.mouseDragged(e);
		};

		public void mouseMoved(MouseEvent e) {
			currentState.mouseMoved(e);
		};

		public void mousePressed(MouseEvent e) {
			currentState.mousePressed(e);
		};

		public void mouseReleased(MouseEvent e) {
			currentState.mouseReleased(e);
		};
	};

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
