package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw13.utils.Band;

/**
 * Servlet implementation class ReportImageServlet that constructs a pie chart
 * that shows market share of top 3 desktop operating systems.
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/png");

		Map<Band, Integer> results = Band.readResultsWithBands(request);
		DefaultPieDataset dataset = new DefaultPieDataset();
		results.forEach((band, count) -> {
			dataset.setValue(band.getName(), count);
		}); 

		JFreeChart chart = ChartFactory.createPieChart3D("Vote count", dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 500, 300);

	}

}
