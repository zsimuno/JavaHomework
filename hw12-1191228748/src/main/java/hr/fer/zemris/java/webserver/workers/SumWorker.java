package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that sums given parameters and shows their sum and an image .
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SumWorker implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		int a = 1, b = 2;
		try {
			a = Integer.parseInt(context.getParameter("a"));
		} catch (Exception e) {
		}
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (Exception e) {
		}
		int sum = a + b;
		context.setTemporaryParameter("zbroj", Integer.toString(sum));
		context.setTemporaryParameter("varA", Integer.toString(a));
		context.setTemporaryParameter("varB", Integer.toString(b));
		context.setTemporaryParameter("imgName", sum % 2 == 0 ? "image1.jpg" : "image2.jpg");

		try {
			context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}