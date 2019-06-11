package hr.fer.zemris.java.p12.servlets.voting;

import java.io.IOException;
import java.util.List;

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

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet that shows the pie chart that shows the distribution of votes for the
 * given poll.
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/png");

		long pollId = 0; // TODO kako primiti?
		List<PollOption> pollOptions = DAOProvider.getDao().getAllPollOptions(pollId); // TODO Excepiton?

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (PollOption pollOption : pollOptions) {
			dataset.setValue(pollOption.getOptionTitle(), pollOption.getVotesCount());
		}

		JFreeChart chart = ChartFactory.createPieChart3D("Vote count", dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 500, 300);

	}

}
