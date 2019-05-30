package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that changes the color of the background.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class BgColorWorker implements IWebWorker {

	/** Patter that checks the validity of a hex code. */
	private Pattern p = Pattern.compile("^([A-Fa-f0-9]{6}$");

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		String color = context.getParameter("bgcolor");
		if (p.matcher(color).matches()) {
			context.setPersistentParameter("bgcolor", color);
			colorUpdated(true, context);
		} else {
			colorUpdated(false, context);
		}

	}

	/**
	 * Prints to context whether the color has been updated and provides a link to
	 * the index page.
	 * 
	 * @param updated {@code true} if the color has been updated, {@code false} if
	 *                it was not.
	 * @param context context to write the html page in.
	 */
	private void colorUpdated(boolean updated, RequestContext context) {
		try {
			context.write("<html><body>");
			context.write("<h1>Color has " + (updated ? "" : "not") + " been updated!</h1>");
			context.write("<a href=\"/index2.html\">Index</a>");
			context.write("</body></html>");
		} catch (IOException ex) {
			// Log exception to servers log...
			ex.printStackTrace();
		}
	}
}