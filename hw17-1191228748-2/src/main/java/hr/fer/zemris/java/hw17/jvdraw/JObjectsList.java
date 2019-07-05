package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;

/**
 * List of geometric objects.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JObjectsList extends JList<GeometricalObject> {

	/** */
	private static final long serialVersionUID = 1L;

	/** Drawing application. */
	JVDraw drawApp;

	/**
	 * Constructor
	 * 
	 * @param dataModel data model for the list
	 * @param drawApp   drawing application
	 */
	public JObjectsList(ListModel<GeometricalObject> dataModel, JVDraw drawApp) {
		super(dataModel);
		this.drawApp = drawApp;

		addListeners();

		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
	}

	/**
	 * Add listeners to this object list.
	 */
	private void addListeners() {

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JObjectsList list = JObjectsList.this;
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					GeometricalObject clicked = list.getModel().getElementAt(index);
					GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
					if (JOptionPane.showConfirmDialog(drawApp, editor, "Edit",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(drawApp, "Data is invalid!", "Error",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				JObjectsList list = JObjectsList.this;
				int index = list.getSelectedIndex();
				GeometricalObject object = list.getModel().getElementAt(index);

				DrawingModel model = drawApp.getModel();
				

				switch (e.getKeyCode()) {
				case KeyEvent.VK_DELETE:
					model.remove(object);
					break;
				case KeyEvent.VK_PLUS:
				case KeyEvent.VK_ADD:
					if (model.indexOf(object) == model.getSize() - 1)
						break;

					model.changeOrder(object, 1);
					list.setSelectedIndex(index + 1);
					break;
				case KeyEvent.VK_MINUS:
				case KeyEvent.VK_SUBTRACT:
					if (model.indexOf(object) == 0)
						break;

					model.changeOrder(object, -1);
					list.setSelectedIndex(index - 1);
					break;
				}

			}
		});

	}

}
