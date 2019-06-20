package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet that TODO javadoc for author servlet
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reqPath = request.getPathInfo();
		if( reqPath.startsWith("/") )
		{
			reqPath = reqPath.substring(1);
		}
		String[] path = reqPath.split("/");
		
		
		if(path.length < 1 || path.length > 2) {
			noPageError(request, response);
			return;
		}
		
		String nick = path[0];
		
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		if(user == null) {
			sendError("User does not exist.", request, response);
			return;
		}
		request.setAttribute("nick", nick);
		
		if(path.length == 1) {
			request.setAttribute("entries", DAOProvider.getDAO().getUserEntries(user));
			request.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(request, response);
			return;
		}
		
		
		String data = path[1];
		
		if(data.equals("new")) {
			request.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(request, response);
			return;
		}
		
		if(data.equals("edit")) {
			
		}

		// It's entry ID
		Long id;
		try {
			id = Long.parseLong(data);
		} catch (Exception e) {
			noPageError(request, response);
			return;
		}
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		
		if(entry == null)  {
			sendError("No such entry", request, response);
			return;
		}
		
		request.setAttribute("entry", DAOProvider.getDAO().getUserEntries(user));
		request.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reqPath = request.getPathInfo();
		if( reqPath.startsWith("/") )
		{
			reqPath = reqPath.substring(1);
		}
		String[] path = reqPath.split("/");
		
		String nick = path[0];
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		if(user == null) {
			sendError("User does not exist.", request, response);
			return;
		}
		
		String data = path[1];

		
		if(data.equals("new")) {
			String title = request.getParameter("title");
			String text = request.getParameter("text");
			
			// TODO popraviti error handling da pokazuje info iznad forme
			
			if (title == null || text == null) {
				sendError("Invalid request", request, response);
				return;
			}

			if (title.isBlank() || text.isBlank()) {
				sendError("You must fill all of the fields!", request, response);
				return;
			}
			BlogEntry entry = new BlogEntry();
			
			entry.setCreator(user);
			entry.setText(text);
			entry.setTitle(title);
			entry.setCreatedAt(new Date());
			entry.setLastModifiedAt(new Date());
			
			DAOProvider.getDAO().newEntry(entry);
			return;
		}
		
		if(data.equals("edit")) {
			
		}
		
		// Add new comment
		Long id;
		try {
			id = Long.parseLong(data);
		} catch (Exception e) {
			noPageError(request, response);
			return;
		}
		
		String commentText = request.getParameter("comment");
		String userNick = request.getSession().getAttribute("current.user.nick").toString();
		
		if(commentText == null || userNick == null || commentText.isBlank() || userNick.isBlank()) {
			response.sendRedirect(request.getContextPath() + "/servleti/author/" + reqPath);
			return;
		}
		
		BlogComment comment = new BlogComment();
		comment.setBlogEntry(DAOProvider.getDAO().getBlogEntry(id));
		comment.setMessage(commentText);
		comment.setPostedOn(new Date());
		comment.setUsersEMail(DAOProvider.getDAO().getUser(userNick).getEmail());
		response.sendRedirect(request.getContextPath() + "/servleti/author/" + reqPath);
	}
	
	/**
	 * Sends an error message to the user if there is a problem.
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
	private void sendError(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("registerMessage", message);
		request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
	}
	
	/**
	 * Sends an error message to the user if the page user asked for does not exist.
	 * 
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
	private void noPageError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendError("Page does not exist!", request, response);
	}

}
