package hr.fer.zemris.java.tecaj_13.web.servlets.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sends error to error.jsp to show it.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ErrorUtil {

	/**
	 * Sends error to the error.jsp and displays the given error {@code message}.
	 * 
	 * @param message  message to be shown to the user.
	 * @param request  an {@link HttpServletRequest} object that contains the
	 *                 request the client has made of the servlet
	 *
	 * @param response an {@link HttpServletResponse} object that contains the
	 *                 response the servlet sends to the client
	 * 
	 * @exception IOException      if an input or output error is detected when the
	 *                             servlet handles the request
	 *
	 * @exception ServletException if the request could not be handled
	 */
	public static void sendError(String message, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("errorMessage", message);
		request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
	}

}
