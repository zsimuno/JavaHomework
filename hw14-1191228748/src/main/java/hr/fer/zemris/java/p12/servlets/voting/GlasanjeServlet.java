package hr.fer.zemris.java.p12.servlets.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlet which reads from the database all poll options of the given poll
 * (from parameters) that can be voted on and sets them as an attribute.
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object id = request.getParameter("pollID");
		if (id == null) {
			response.sendRedirect(request.getContextPath() + "/servleti/index.html");
			return;
		}
		long pollId = 0;
		try {
			pollId = Long.parseLong(id.toString());
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/servleti/index.html");
			return;
		}
		request.getSession().setAttribute("currentPollID", pollId);

		request.setAttribute("pollOptions", DAOProvider.getDao().getAllPollOptions(pollId)); 
		request.setAttribute("poll", DAOProvider.getDao().getPoll(pollId));
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(request, response);
	}

}
