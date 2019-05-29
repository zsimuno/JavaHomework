package hr.fer.zemris.java.webserver;

/**
 * TODO dispatcher javadoc
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface IDispatcher {
	/**
	 * @param urlPath
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}