package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlet that shows the list of links to available polls.
 */
@WebServlet("/servleti/index.html")
public class ServletIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("polls", DAOProvider.getDao().getAllPolls()); 
		request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
	}

}
