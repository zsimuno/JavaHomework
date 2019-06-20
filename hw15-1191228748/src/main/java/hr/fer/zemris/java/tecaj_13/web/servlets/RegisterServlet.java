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
 * Servlet that allows the user to register.
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		BlogUser user = new BlogUser();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNick(nick);

		if (firstName == null || lastName == null || email == null || nick == null || password == null) {
			registerError("Invalid request", user, request, response);
			return;
		}

		if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || nick.isBlank() || password.isBlank()) {
			registerError("You must fill all of the fields!", user, request, response);
			return;
		}
		
		if(DAOProvider.getDAO().nickExists(nick)) {
			registerError("Nick already exists", user, request, response);
			return;
		}

		String passwordHash = HashUtil.createHash(password);
		user.setPasswordHash(passwordHash);
		
		DAOProvider.getDAO().register(user);
		
        response.sendRedirect("main");

	}

	/**
	 * Sends an error message to the user if there is a problem while registering.
	 * 
	 * @param message  message to be shown to the user.
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
	private void registerError(String message, BlogUser user, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("user", user);
		request.setAttribute("registerMessage", message);
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}

}
