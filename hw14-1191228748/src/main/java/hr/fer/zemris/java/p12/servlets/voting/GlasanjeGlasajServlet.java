package hr.fer.zemris.java.p12.servlets.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlet implementation class GlasanjeGlasajServlet which gets the ID of the
 * band that user voted on and increments that bands vote count. After that
 * redirects to a servlet that shows the results of the vote (
 * {@link GlasanjeRezultatiServlet} ).
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object voteID = request.getParameter("id");
		if (voteID == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp"); // TODO popravit
			return;
		}
		Long optionId = null;
		try {
			optionId = Long.parseLong(voteID.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}

		DAOProvider.getDao().voteOnOption(optionId);

		// TODO kako poslati id od polla?
		// Redirect
		response.sendRedirect(request.getContextPath() + "/glasanje-rezultati");
	}

}
