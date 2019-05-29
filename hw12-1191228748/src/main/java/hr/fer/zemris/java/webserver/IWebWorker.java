package hr.fer.zemris.java.webserver;

/**
 * TODO javadoc webWorker
 * @author Zvonimir Šimunović
 *
 */
public interface IWebWorker {
	/**
	 * @param context
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}