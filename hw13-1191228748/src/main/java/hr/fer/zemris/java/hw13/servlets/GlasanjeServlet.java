package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.utils.Band;

/**
 * Servlet implementation class GlasanjeServlet which reads from the file all
 * bands that can be voted on and sets them as an attribute.
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("bands", Band.readBands(request));
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(request, response);
	}

}
