package hr.fer.zemris.java.p12.servlets.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlets which reads from the database all poll options of the given poll
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
			// TODO sto? vjerojatno redirect na listu svih pollova
		}
		long pollId = 0;
		try {
			pollId = Long.parseLong(id.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}

		request.setAttribute("pollOptions", DAOProvider.getDao().getAllPollOptions(pollId)); // TODO pazit na
																								// DaoException?
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(request, response);
	}

}
