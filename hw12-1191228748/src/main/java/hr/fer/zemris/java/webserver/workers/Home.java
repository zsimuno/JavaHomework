package hr.fer.zemris.java.webserver.workers;


import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that creates a home page.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Home implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		String color = context.getPersistentParameter("bgcolor");

		context.setTemporaryParameter("background", color == null ? "7F7F7F" : color);
		try {
			context.getDispatcher().dispatchRequest("/private/pages/home.smscr");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
