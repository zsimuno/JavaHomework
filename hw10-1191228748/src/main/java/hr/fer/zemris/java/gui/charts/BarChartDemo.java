package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo for the BarChartComponent.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/** Chart that needs to be drawn. */
	private BarChart chart;

	/** Name of the file we get the chart data from. */
	private String fileName;

	/**
	 * Constructor.
	 */
	public BarChartDemo(BarChart chart, String fileName) {
		this.chart = chart;
		this.fileName = fileName;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JLabel name = new JLabel(fileName);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(name, BorderLayout.PAGE_START);
		getContentPane().add(new BarChartComponent(chart), BorderLayout.CENTER);
	}

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments. Must have one argument - path to a file.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of command line arguments!");
			return;
		}

		String fileName = args[0];
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Error while opening the file!");
			return;
		}

		if (lines.size() < 6) {
			System.out.println("Invalid number of lines in the file!");
			return;
		}

		String xDescript = lines.get(0);
		String yDescript = lines.get(1);

		List<XYValue> values = parseValues(lines.get(2));

		int minY, maxY, distance;
		try {
			minY = Integer.parseInt(lines.get(3));
			maxY = Integer.parseInt(lines.get(4));
			distance = Integer.parseInt(lines.get(5));
		} catch (Exception e) {
			System.out.println("Invalid input in file!");
			return;
		}

		BarChart chart;
		try {
			chart = new BarChart(values, xDescript, yDescript, minY, maxY, distance);

		} catch (Exception e) {
			System.out.println("Invalid input for chart in file!");
			return;
		}

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(chart, fileName).setVisible(true);
		});
	}

	/**
	 * Parses list of {@link XYValue} objects from the given string. Format is
	 * "x1,y1 x2,y2 x3,y3...".
	 * 
	 * @param string string to be parsed.
	 * @return list of {@link XYValue} objects from the given string.
	 */
	private static List<XYValue> parseValues(String string) {
		List<XYValue> list = new ArrayList<>();
		String[] values = string.split("\\s+");
		for (int i = 0; i < values.length; i++) {
			String[] xy = values[i].split(",");
			int x = 0, y = 0;
			try {
				x = Integer.parseInt(xy[0]);
				y = Integer.parseInt(xy[1]);
			} catch (Exception e) {
				System.out.println("Invalid input in file!");
				System.exit(1);
			}
			list.add(new XYValue(x, y));
		}
		return list;
	}

}
