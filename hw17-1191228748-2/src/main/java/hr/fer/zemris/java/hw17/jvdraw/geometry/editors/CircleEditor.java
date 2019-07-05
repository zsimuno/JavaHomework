package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;

/**
 * Editor for circles. (non-filled)
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	/** */
	private static final long serialVersionUID = 1L;

	/** Circle geometrical object. */
	private Circle circle;

	/** Center of the circle */
	private Point center;
	/** Radius of the circle */
	private int radius;
	/** Line color of the circle */
	private Color lineColor;

	/** X point of the center */
	JTextField centerX;
	/** Y point of the center */
	JTextField centerY;
	/** Radius value */
	JTextField radiusField;
	/** Chooser for line color */
	JColorChooser lineColorChoose;

	/**
	 * Constructor.
	 * 
	 * @param circle Circle geometrical object
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;

		centerX = new JTextField(Integer.toString(circle.getCenter().x), 5);
		centerY = new JTextField(Integer.toString(circle.getCenter().y), 5);
		radiusField = new JTextField(Integer.toString(circle.getRadius()), 5);
		lineColorChoose = new JColorChooser(circle.getLineColor());

		this.setLayout(new GridLayout(3, 2));
		this.add(new JLabel("Center: "));
		this.add(centerX);
		this.add(centerY);

		this.add(new JLabel("Radius: "));
		this.add(radiusField);

		this.add(new JLabel("RGB Line: "));
		this.add(lineColorChoose);

	}

	@Override
	public void checkEditing() {
		center = new Point(Integer.parseInt(centerX.getText()), Integer.parseInt(centerY.getText()));
		radius = Integer.parseInt(radiusField.getText());

		lineColor = lineColorChoose.getColor();

		if (lineColor == null) {
			throw new RuntimeException("Error while reading color.");
		}

	}

	@Override
	public void acceptEditing() {
		circle.setChange(center, radius, lineColor);

	}

}
