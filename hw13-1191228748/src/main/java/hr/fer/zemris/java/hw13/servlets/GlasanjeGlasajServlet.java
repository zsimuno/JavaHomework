package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.utils.Band;

/**
 * Servlet implementation class GlasanjeGlasajServlet which gets the ID of the
 * band that user voted on and increments that bands vote count. After that
 * redirects to a servlet that shows the results of the vote (
 * {@link GlasanjeRezultatiServlet} ).
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object voteID = request.getParameter("id");
		if (voteID == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		String bandID = voteID.toString();

		Map<String, Integer> votingResult = Band.readResults(request);
		if (!votingResult.containsKey(bandID)) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		votingResult.put(bandID, votingResult.get(bandID) + 1);

		final List<String> result = new ArrayList<>();
		votingResult.forEach((id, votes) -> result.add(id + "\t" + votes));

		// Update data
		String fileName = request.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path file = Paths.get(fileName);
		Files.write(file, result, StandardCharsets.UTF_8);

		// Redirect
		response.sendRedirect(request.getContextPath() + "/glasanje-rezultati");
	}

}
