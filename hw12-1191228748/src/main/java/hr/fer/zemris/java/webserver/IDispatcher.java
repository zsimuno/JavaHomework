package hr.fer.zemris.java.webserver;

/**
 * Represents a dispacher that dispatches a request.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface IDispatcher {
	/**
	 * Dispatch the request with the given {@code urlPath}.
	 * 
	 * @param urlPath path of the request.
	 * @throws Exception if an error happens while dispatching.
	 */
	void dispatchRequest(String urlPath) throws Exception;
}