package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.util.HashUtil;

/**
 * Servlet that is the main page for this webapp. User can login or register
 * from this page and it shows a list of registered authors.
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("registeredAuthors", DAOProvider.getDAO().getAllAuthors());
		request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		
		if (nick == null || password == null) {
			loginError("Invalid request", nick, request, response);
			return;
		}

		if ( nick.isBlank() || password.isBlank()) {
			loginError("You must fill out all of the fields.", nick, request, response);
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		
		if(user == null || !user.getPasswordHash().equals(HashUtil.createHash(password))) {
			loginError("Login failed.", nick, request, response);
			return;
		}
		
		request.getSession().setAttribute("current.user.id", user.getId());
		request.getSession().setAttribute("current.user.fn", user.getFirstName());
		request.getSession().setAttribute("current.user.ln", user.getLastName());
		request.getSession().setAttribute("current.user.nick", user.getNick());
		response.sendRedirect(request.getContextPath() + "/servleti/main");
	}

	/**
	 * Sends an error message to the user if there is a problem while registering.
	 * 
	 * @param nick     entered nick.
	 * @param user     user data.
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
	private void loginError(String message, String nick, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("nick", nick);
		request.setAttribute("loginMessage", message);
		request.setAttribute("registeredAuthors", DAOProvider.getDAO().getAllAuthors());
		request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
	}
}
