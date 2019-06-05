package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.utils.Band;

/**
 * Servlet implementation class GlasanjeRezultatiServlet that reads the results
 * of the voting for bands and stores them in attributes as a map with band
 * names as ID and voting results as the value.
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<Band, Integer> results = Band.readResultsWithBands(request);
		request.setAttribute("results", results);
		
		// Band name and link
		Map<String, String> winnerBands = new HashMap<>();
		
		int winnerVoteCount = -1;
		for (Map.Entry<Band, Integer> result : results.entrySet()) {
			if(winnerVoteCount == -1)  {
				winnerVoteCount = result.getValue();
				winnerBands.put(result.getKey().getName(), result.getKey().getSongLink());
				continue;
			}
			
			// Are there multiple winners? If not, stop.
			if(result.getValue() != winnerVoteCount) {
				break;
			}
			
			winnerBands.put(result.getKey().getName(), result.getKey().getSongLink());
		}
		request.setAttribute("winners", winnerBands);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(request, response);
	}

}
