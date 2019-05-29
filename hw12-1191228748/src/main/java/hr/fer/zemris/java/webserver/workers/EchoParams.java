package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Map.Entry;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that prints all given parameters in a table.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class EchoParams implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");

		try {
			context.write("<html><body>");
			context.write("<head><style>table, td { border: 1px solid black;}</style></head>");
			context.write("<table>");
			for (Entry<String, String> param : context.getParameters().entrySet()) {
				context.write("<tr><td>" + param.getKey() + "</td>");
				context.write("<td>" + param.getValue() + "</td></tr>");
			}
			context.write("</table>");
			context.write("</body></html>");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}