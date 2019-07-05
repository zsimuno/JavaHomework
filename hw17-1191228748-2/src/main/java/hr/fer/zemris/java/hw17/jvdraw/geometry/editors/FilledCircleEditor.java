package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;

/**
 * Editor for filled circles.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/** */
	private static final long serialVersionUID = 1L;

	/** Filled circle geometrical object. */
	private FilledCircle filledCircle;

	/** Center of the circle */
	private Point center;
	/** Radius of the circle */
	private int radius;
	/** Line color of the circle */
	private Color lineColor;
	/** Fill color of the circle */
	private Color fillColor;

	/** X point of the center */
	JTextField centerX;
	/** Y point of the center */
	JTextField centerY;
	/** Radius value */
	JTextField radiusField;
	/** Chooser for line color */
	JColorChooser lineColorChoose;
	/** Chooser for fill color */
	JColorChooser fillColorChoose;

	/**
	 * Constructor.
	 * 
	 * @param filledCircle FilledCircle geometrical object
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;

		centerX = new JTextField(Integer.toString(filledCircle.getCenter().x), 5);
		centerY = new JTextField(Integer.toString(filledCircle.getCenter().y), 5);
		radiusField = new JTextField(Integer.toString(filledCircle.getRadius()), 5);
		lineColorChoose = new JColorChooser(filledCircle.getLineColor());
		fillColorChoose = new JColorChooser(filledCircle.getFillColor());

		this.add(new JLabel("Center: "));
		this.add(centerX);
		this.add(centerY);

		this.add(new JLabel("Radius: "));
		this.add(radiusField);

		this.add(new JLabel("RGB Line: "));
		this.add(lineColorChoose);

		this.add(new JLabel("RGB Fill: "));
		this.add(fillColorChoose);

	}

	@Override
	public void checkEditing() {
		center = new Point(Integer.parseInt(centerX.getText()), Integer.parseInt(centerY.getText()));
		radius = Integer.parseInt(radiusField.getText());

		lineColor = lineColorChoose.getColor();
		fillColor = fillColorChoose.getColor();

		if (lineColor == null || fillColor == null) {
			throw new RuntimeException("Error while reading color.");
		}

	}

	@Override
	public void acceptEditing() {
		filledCircle.setChange(center, radius, lineColor, fillColor);

	}

}
