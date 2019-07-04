package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.objects.Line;

/**
 * Parses the painting files or creates new ones from given objects.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class FileParser {

	/**
	 * Parse the given {@code text} to a list of geometrical objects.
	 * 
	 * @param lines lines of text to be parsed
	 * @return a list of geometrical objects
	 * @throws IllegalArgumentException if the lines input is invalid.
	 */
	public static List<GeometricalObject> parseText(List<String> lines) {
		List<GeometricalObject> list = new ArrayList<>();

		for (String line : lines) {
			String[] sLine = line.split(" ");
			try {
				if (sLine[0].equals("LINE")) {
					Point start = new Point(Integer.parseInt(sLine[1]), Integer.parseInt(sLine[2]));
					Point end = new Point(Integer.parseInt(sLine[3]), Integer.parseInt(sLine[4]));
					Color lineColor = new Color(Integer.parseInt(sLine[5]), Integer.parseInt(sLine[6]),
							Integer.parseInt(sLine[7]));

					list.add(new Line(start, end, lineColor));
				} else {
					Point center = new Point(Integer.parseInt(sLine[1]), Integer.parseInt(sLine[2]));
					int radius = Integer.parseInt(sLine[3]);
					Color lineColor = new Color(Integer.parseInt(sLine[4]), Integer.parseInt(sLine[5]),
							Integer.parseInt(sLine[6]));

					if (sLine[0].equals("CIRCLE")) {
						list.add(new Circle(center, radius, lineColor));
						
					} else if (sLine[0].equals("FCIRCLE")) {
						Color fillColor = new Color(Integer.parseInt(sLine[7]), Integer.parseInt(sLine[8]),
								Integer.parseInt(sLine[9]));
						list.add(new FilledCircle(center, radius, lineColor, fillColor));
						
					} else {
						throw new IllegalArgumentException();
					}
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid lines input.");
			}
		}

		return list;
	}

	/**
	 * Creates and returns the textual representation of the given
	 * {@link DrawingModel} object.
	 * 
	 * @param model drawing model
	 * @return textual representation of the model
	 */
	public static String createText(DrawingModel model) {
		FileMakerVisitor fileMaker = new FileMakerVisitor();
		for (int i = 0, size = model.getSize(); i < size; i++) {
			GeometricalObject object = model.getObject(i);
			object.accept(fileMaker);
		}

		return fileMaker.getFileText();
	}

	private static class FileMakerVisitor implements GeometricalObjectVisitor {

		/** Text that will be written to the file. */
		private StringBuilder fileText = new StringBuilder();

		/**
		 * @return the fileText
		 */
		public String getFileText() {
			return fileText.toString();
		}

		@Override
		public void visit(Line line) {
			fileText.append("LINE ");
			fileText.append(line.getStart().x + " " + line.getStart().y + " ");
			fileText.append(line.getEnd().x + " " + line.getEnd().y + " ");
			fileText.append(line.getLineColor().getRed() + " " + line.getLineColor().getGreen() + " "
					+ line.getLineColor().getBlue());

			fileText.append("\n");
		}

		@Override
		public void visit(Circle circle) {
			fileText.append("CIRCLE ");
			fileText.append(circle.getCenter().x + " " + circle.getCenter().y + " ");
			fileText.append(circle.getRadius() + " ");
			fileText.append(circle.getLineColor().getRed() + " " + circle.getLineColor().getGreen() + " "
					+ circle.getLineColor().getBlue());

			fileText.append("\n");
		}

		@Override
		public void visit(FilledCircle filledCircle) {
			fileText.append("FCIRCLE ");
			fileText.append(filledCircle.getCenter().x + " " + filledCircle.getCenter().y + " ");
			fileText.append(filledCircle.getRadius() + " ");
			fileText.append(filledCircle.getLineColor().getRed() + " " + filledCircle.getLineColor().getGreen() + " "
					+ filledCircle.getLineColor().getBlue() + " ");
			fileText.append(filledCircle.getFillColor().getRed() + " " + filledCircle.getFillColor().getGreen() + " "
					+ filledCircle.getFillColor().getBlue() + " ");

			fileText.append("\n");
		}

	}
}
