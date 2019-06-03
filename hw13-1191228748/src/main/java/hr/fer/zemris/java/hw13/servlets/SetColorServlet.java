package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SetColor that sets the background color for
 * index and color pages. Lasts only during the session.
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String color = request.getParameter("color");
		if (color == null) {
			// TODO treba li ovako?
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		String colorCode;

		switch (color) {
		case "white":
			colorCode = "#FFFFFF";
			break;
		case "red":
			colorCode = "#FF0000";
			break;
		case "green":
			colorCode = "#00FF00";
			break;
		case "cyan":
			colorCode = "#00FFFF";
			break;
		default:
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		request.getSession().setAttribute("pickedBgCol", colorCode);
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
