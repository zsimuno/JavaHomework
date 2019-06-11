package hr.fer.zemris.java.p12.servlets.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class for servlets that are used in voting pages.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class VotingUtility {

	/**
	 * Retrieves id from attributes of the request session.
	 * 
	 * @param request  an HttpServletRequest object that contains the request the
	 *                 client has made of the servlet.
	 * @param response an HttpServletResponse object that contains the response the
	 *                 servlet sends to the client.
	 * @return ID of the current poll or -1 if the ID cannot be obtained.
	 * @throws IOException on raed/write error
	 */
	public static long getCurrentPollId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object id = request.getSession().getAttribute("currentPollID");
		if (id == null) {
			response.sendRedirect(request.getContextPath() + "/servleti/index.html");
			return -1;
		}
		long pollId = 0;
		try {
			pollId = Long.parseLong(id.toString());
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/servleti/index.html");
			return -1;
		}

		return pollId;
	}

}
