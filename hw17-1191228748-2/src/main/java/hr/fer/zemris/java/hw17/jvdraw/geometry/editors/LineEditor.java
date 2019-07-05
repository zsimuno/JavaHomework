package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * Editor for lines.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/** */
	private static final long serialVersionUID = 1L;

	/** Line geometrical object. */
	private Line line;

	/** Start of the line */
	private Point start;
	/** End of the line */
	private Point end;
	/** Line color of the line */
	private Color lineColor;

	/** X point of the start */
	JTextField startX;
	/** Y point of the start */
	JTextField startY;
	/** X point of the end */
	JTextField endX;
	/** Y point of the end */
	JTextField endY;
	/** Chooser for line color */
	JColorChooser lineColorChoose;
	/** Chooser for fill color */
	JColorChooser fillColorChoose;

	/**
	 * Constructor.
	 * 
	 * @param filledCircle FilledCircle geometrical object
	 */
	public LineEditor(Line line) {
		this.line = line;

		startX = new JTextField(Integer.toString(line.getStart().x), 5);
		startY = new JTextField(Integer.toString(line.getStart().y), 5);
		endX = new JTextField(Integer.toString(line.getEnd().x), 5);
		endY = new JTextField(Integer.toString(line.getEnd().y), 5);
		lineColorChoose = new JColorChooser(line.getLineColor());

		this.add(new JLabel("Center: "));
		this.add(startX);
		this.add(startY);

		this.add(new JLabel("End: "));
		this.add(endX);
		this.add(endY);

		this.add(new JLabel("RGB Line: "));
		this.add(lineColorChoose);

	}

	@Override
	public void checkEditing() {
		start = new Point(Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()));
		end = new Point(Integer.parseInt(endX.getText()), Integer.parseInt(endY.getText()));

		lineColor = lineColorChoose.getColor();

		if (lineColor == null) {
			throw new RuntimeException("Error while reading color.");
		}

	}

	@Override
	public void acceptEditing() {
		line.setChange(start, end, lineColor);
	}

}
