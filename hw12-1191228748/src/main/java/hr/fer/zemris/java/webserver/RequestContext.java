package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents one request context for a HTTP request.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class RequestContext {

	/**
	 * Represents a cookie in a {@link RequestContext}.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	public static class RCCookie {

		/** Name of the cookie. */
		private final String name;
		/** Value of the cookie. */
		private final String value;
		/** Domain of the cookie. */
		private final String domain;
		/** Path of the cookie. */
		private final String path;
		/** Maximal age of this cookie. */
		private final Integer maxAge;

		/**
		 * Constructor.
		 * 
		 * @param name   Name of the cookie.
		 * @param value  Value of the cookie.
		 * @param domain Domain of the cookie.
		 * @param path   Path of the cookie.
		 * @param maxAge Maximal age of the cookie.
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * @return the name of the cookie
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the value of the cookie
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @return the domain of the cookie
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * @return the path of the cookie
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @return the maxAge of the cookie
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

	}

	/** Output stream to write to. */
	private OutputStream outputStream;
	/** Charset used in encoding. */
	private Charset charset;
	/** Encoding used to encode characters in this context. */
	private String encoding = "UTF-8";
	/** Status code of the request. */
	private int statusCode = 200;
	/** Status message of the request. */
	private String statusText = "OK";
	/** Mime type of the request. */
	private String mimeType = "text/html";
	/** Content length of the request. */
	private Long contentLength = null;
	/** Dispatcher of the request. */
	private IDispatcher dispatcher;

	/** Parameters of the request. */
	private final Map<String, String> parameters;
	/** Temporary parameters of the request. */
	private Map<String, String> temporaryParameters = new HashMap<>();
	/** Persistent parameters of the request. */
	private Map<String, String> persistentParameters;
	/** Cookies of the request. */
	private List<RCCookie> outputCookies = new ArrayList<>();
	/** Determines whether the header was already generated. */
	private boolean headerGenerated = false;
	/** Session ID.*/
	private String sid;

	/**
	 * Constructor.
	 * 
	 * @param outputStream         Output stream to write to.
	 * @param parameters           Parameters of the request.
	 * @param persistentParameters Persistent parameters of the request.
	 * @param outputCookies        Cookies of the request.
	 * 
	 * @throws NullPointerException if given output stream {@code null}.
	 */
	public RequestContext(OutputStream outputStream, // must not be null!
			Map<String, String> parameters, // if null, treat as empty
			Map<String, String> persistentParameters, // if null, treat as empty
			List<RCCookie> outputCookies) { // if null, treat as empty

		this.outputStream = Objects.requireNonNull(outputStream);
		// TODO if null, treat as empty.
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
	}

	/**
	 * Constructor.
	 * 
	 * @param outputStream         Output stream to write to.
	 * @param parameters           Parameters of the request.
	 * @param persistentParameters Persistent parameters of the request.
	 * @param outputCookies        Cookies of the request.
	 * @param temporaryParameters  Temporary parameters of the request.
	 * @param dispatcher           dispatcher of the request.
	 * @param sID 
	 * 
	 * @throws NullPointerException if given output stream {@code null}.
	 * 
	 */
	public RequestContext(OutputStream outputStream, // must not be null!
			Map<String, String> parameters, // if null, treat as empty
			Map<String, String> persistentParameters, // if null, treat as empty
			List<RCCookie> outputCookies, // if null, treat as empty
			Map<String, String> temporaryParameters, 
			IDispatcher dispatcher, String sID) {

		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.sid = sID;
	}

	/**
	 * Retrieves value from parameters with the given {@code name} (or {@code null}
	 * if no association exists).
	 * 
	 * @param name name of the parameter to be retrieved.
	 * @return parameter with the given {@code name} or {@code null} if no
	 *         association exist.
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Retrieves names of all parameters (read-only).
	 * 
	 * @return names of all parameters.
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Retrieves value from persistent parameters with the given {@code name} (or
	 * {@code null} if no association exists).
	 * 
	 * @param name name of the persistent parameter to be retrieved.
	 * @return value with the given {@code name} or {@code null} if no association
	 *         exists.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Retrieves names of all parameters in persistent parameters (read-only).
	 * 
	 * @return names of all parameters in persistent parameters.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Stores the given value to persistent parameters.
	 * 
	 * @param name  name (or the key) of the value.
	 * @param value value to be stored with the given name.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes a value from persistent parameters.
	 * 
	 * @param name name of the value to be removed.
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Retrieves value from temporary parameters with the given {@code name} (or
	 * {@code null} if no association exists).
	 * 
	 * @param name name of the parameter to be retrieved.
	 * @return value with the given {@code name} (or {@code null} if no association
	 *         exists).
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Retrieves names of all parameters in temporary parameters (read-only).
	 * 
	 * @return names of all parameters in temporary parameters.
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Retrieves an identifier which is unique for current user session.
	 * 
	 * @return identifier which is unique for current user session.
	 */
	public String getSessionID() {
		return sid;
	}

	/**
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Method that stores a value to temporary parameters.
	 * 
	 * @param name  name of the value.
	 * @param value value to be stored.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporary parameters.
	 * 
	 * @param name name of the value to be removed.
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Write the data to the output stream.
	 * 
	 * @param data data to be written.
	 * @return this context.
	 * @throws IOException If there was an error while writing.
	 */
	public RequestContext write(byte[] data) throws IOException {
		checkHeaderToGenerate();

		outputStream.write(data);
		outputStream.flush();

		return this;
	}

	/**
	 * Write the data to the output stream from the given {@code offset} and with a
	 * given length.
	 * 
	 * @param data   data to be written.
	 * @param offset offset to start writing from.
	 * @param len    length of the data.
	 * @return this context.
	 * @throws IOException If there was an error while writing.
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		checkHeaderToGenerate();

		outputStream.write(data, offset, len);
		outputStream.flush();

		return this;
	}

	/**
	 * Write the text to the output stream.
	 * 
	 * @param text text to be written.
	 * @return this context.
	 * @throws IOException If there was an error while writing.
	 */
	public RequestContext write(String text) throws IOException {
		checkHeaderToGenerate();

		outputStream.write(text.getBytes(charset));
		outputStream.flush();

		return this;
	}

	/**
	 * Generates the header and sends (it was not already) it to
	 * {@link OutputStream} that is stored in this context.
	 * 
	 * @throws IOException if it can't write the header to file.
	 */
	private void checkHeaderToGenerate() throws IOException {
		if (headerGenerated)
			return;

		StringBuilder header = new StringBuilder();
		charset = Charset.forName(encoding);

		header.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");

		if (mimeType.startsWith("text/")) {
			mimeType += "; charset=" + encoding;
		}
		header.append("Content-Type: " + mimeType + "\r\n");

		if (contentLength != null) {
			header.append(" Content-Length: " + contentLength + "\r\n");
		}

		for (RCCookie rcCookie : outputCookies) {
			header.append("Set-Cookie: ");
			header.append(rcCookie.getName() + "=\"" + rcCookie.getValue() + "\"");
			if (rcCookie.getDomain() != null) {
				header.append("; Domain=" + rcCookie.getDomain());
			}
			if (rcCookie.getPath() != null) {
				header.append("; Path=" + rcCookie.getPath());
			}
			if (rcCookie.getMaxAge() != null) {
				header.append("; Max-Age=" + rcCookie.getMaxAge());
			}
			header.append("\r\n");
		}

		// End of header
		header.append("\r\n");

		outputStream.write(header.toString().getBytes(charset));

		headerGenerated = true;
	}

	/**
	 * @return the temporaryParameters
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * @param temporaryParameters the temporaryParameters to set
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * @return the persistentParameters
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * @param persistentParameters the persistentParameters to set
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Adds a cookie to the cookie list.
	 * 
	 * @param cookie cookie to be added.
	 * @throws RuntimeException if the header is already generated.
	 */
	public void addRCCookie(RCCookie cookie) {
		throwIfHeaderGenerated();
		outputCookies.add(cookie);
	}

	/**
	 * @param outputStream the outputStream to set
	 * @throws RuntimeException if the header is already generated.
	 */
	public void setOutputStream(OutputStream outputStream) {
		throwIfHeaderGenerated();
		this.outputStream = outputStream;
	}

	/**
	 * @param encoding the encoding to set
	 * @throws RuntimeException if the header is already generated.
	 */
	public void setEncoding(String encoding) {
		throwIfHeaderGenerated();
		this.encoding = encoding;
	}

	/**
	 * @param statusCode the statusCode to set
	 * @throws RuntimeException if the header is already generated.
	 */
	public void setStatusCode(int statusCode) {
		throwIfHeaderGenerated();
		this.statusCode = statusCode;
	}

	/**
	 * @param statusText the statusText to set
	 * @throws RuntimeException if the header is already generated.
	 */
	public void setStatusText(String statusText) {
		throwIfHeaderGenerated();
		this.statusText = statusText;
	}

	/**
	 * @param mimeType the mimeType to set
	 * @throws RuntimeException if the header is already generated.
	 */
	public void setMimeType(String mimeType) {
		throwIfHeaderGenerated();
		this.mimeType = mimeType;
	}

	/**
	 * @param contentLength the contentLength to set
	 * @throws RuntimeException if the header is already generated.
	 */
	public void setContentLength(Long contentLength) {
		throwIfHeaderGenerated();
		this.contentLength = contentLength;
	}

	/**
	 * Throws a {@link RuntimeException} if the header is already generated.
	 * 
	 * @throws RuntimeException if the header is already generated.
	 */
	private void throwIfHeaderGenerated() {
		if (headerGenerated)
			throw new RuntimeException("No changes if the header is already generated!");
	}

}
