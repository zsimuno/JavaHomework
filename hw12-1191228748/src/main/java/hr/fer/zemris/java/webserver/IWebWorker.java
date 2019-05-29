package hr.fer.zemris.java.webserver;

/**
 * Interface of a web worker.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface IWebWorker {
	/**
	 * Process request in the given context.
	 * 
	 * @param context context of the request.
	 * @throws Exception If there's an error while reading/writing.
	 */
	public void processRequest(RequestContext context) throws Exception;
}