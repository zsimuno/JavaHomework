package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;
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

		cp.add(canvas, BorderLayout.CENTER);

		JList<GeometricalObject> objectList = new JList<GeometricalObject>(new DrawingObjectListModel(model));
		cp.add(objectList, BorderLayout.LINE_END);

		objectList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				@SuppressWarnings("unchecked")
				JList<GeometricalObject> list = (JList<GeometricalObject>) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					GeometricalObject clicked = list.getModel().getElementAt(index);
					GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
					if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(JVDraw.this, "Data is invalid!", "Error",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		objectList.setBorder(BorderFactory.createLineBorder(Color.black, 2));

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
