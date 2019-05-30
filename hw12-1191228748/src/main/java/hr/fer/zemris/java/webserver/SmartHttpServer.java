package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Server for HTTP requests.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SmartHttpServer {
	/** address on which the server listens. */
	private String address;
	/** Domain name of the web server, */
	private String domainName;
	/** Port on which the server listens. */
	private int port;
	/** Number of threads for the thread pool. */
	private int workerThreads;
	/** Duration of user session in seconds. */
	private int sessionTimeout;
	/** Extensions to mime-type mapping. */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/** Thread of the server. */
	private ServerThread serverThread;
	/** Thread pool of workers. */
	private ExecutorService threadPool;
	/** Path to root directory from which we serve files. */
	private Path documentRoot;
	/** Map of web workers. Web url to class name mapping. */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	/** Map of sessions. Session ID to session map entry mapping. */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/** Random session generator. */
	private Random sessionRandom = new Random();
	/** Letters that are used in creating a random ID. */
	private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/** Worker thread (daemon) that cleans expired sessions from sessions map. */
	private CleanerThread cleaner = null;
	/**
	 * Number by which we divide current system time in milliseconds to get seconds.
	 */
	private static final int timeDivider = 1000;

	/**
	 * Constructor that sets the configuration based on the file with the given
	 * path.
	 * 
	 * @param configFileName path to the configuration file.
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get(configFileName))) {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Get properties from server.properties
		address = properties.get("server.address").toString();
		domainName = properties.get("server.domainName").toString();
		port = Integer.parseInt(properties.get("server.port").toString());
		workerThreads = Integer.parseInt(properties.get("server.workerThreads").toString());
		documentRoot = Paths.get(properties.get("server.documentRoot").toString());
		sessionTimeout = Integer.parseInt(properties.get("session.timeout").toString());

		// Get mime configuration
		String mimeConfig = properties.get("server.mimeConfig").toString();

		Properties mimeProps = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get(mimeConfig))) {
			mimeProps.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		mimeProps.forEach((t, u) -> {
			mimeTypes.put(t.toString(), u.toString());
		});

		// Get workers properties
		String workers = properties.get("server.workers").toString();

		Properties workersProps = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get(workers))) {
			workersProps.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create workers
		List<String> paths = new ArrayList<>();
		workersProps.forEach((path, fqcn) -> {
			if (paths.contains(fqcn.toString()))
				throw new IllegalArgumentException();

			paths.add(path.toString());

			workersMap.put(path.toString(), instanceWorker(fqcn));
		});

	}

	/**
	 * Creates an instance of a {@link IWebWorker} with the given {@code fqcn}.
	 * 
	 * @param fqcn fully qualified class name.
	 * @return an instance of a {@link IWebWorker} with the given {@code fqcn}.
	 */
	private IWebWorker instanceWorker(Object fqcn) {
		Class<?> referenceToClass;
		Object newObject = null;
		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(fqcn.toString());
			newObject = referenceToClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		IWebWorker iww = (IWebWorker) newObject;
		return iww;
	}

	/**
	 * Start server thread if not already and initialize thread pool.
	 */
	protected synchronized void start() {
		if (serverThread == null || !serverThread.isAlive()) {
			serverThread = new ServerThread();
			serverThread.start();
			cleaner = new CleanerThread();
			cleaner.start();
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}

	}

	/**
	 * Stop the server running and shut down the thread pool.
	 */
	protected synchronized void stop() {
		serverThread.stopServer();
		threadPool.shutdown();
	}

	/**
	 * Thread that represents the server.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	protected class ServerThread extends Thread {

		/** Is stopping requested from the server thread. */
		private volatile boolean stopRequested = false;

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (true) {
				Socket client = null;
				try {
					client = serverSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (stopRequested)
					break;
				ClientWorker cw = new ClientWorker(client);
				threadPool.submit(cw);
			}
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Stop the server execution.
		 */
		public void stopServer() {
			stopRequested = true;
		}
	}

	/**
	 * Represents a client worker that will respond to client requests.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/** Client socket. */
		private Socket csocket;
		/** Input stream of the data. */
		private PushbackInputStream istream;
		/** Output stream of the data. */
		private OutputStream ostream;
		/** Version of the request. */
		private String version;
		/** Method of the request. */
		private String method;
		/** Host of the request. */
		private String host;
		/** Parameters of the request. */
		private Map<String, String> params = new HashMap<String, String>();
		/** Temporary parameters of the request. */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/** Permanent parameters of the request. */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/** Cookies of the request. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/** Session ID of the request. */
		private String SID;
		/** Context of the request. */
		private RequestContext context;

		/**
		 * Constructor.
		 * 
		 * @param csocket client socket.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				List<String> requestHeaders = readRequest();

				// Error on getting headers (error already sent)
				if (requestHeaders == null) {
					closeStreams();
					return;
				}

				getHost(requestHeaders);

				checkSession(requestHeaders);

				String[] firstLine = requestHeaders.get(0).split(" ");

				// Check method and version
				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(405, "Method Not Allowed");
					closeStreams();
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(400, "HTTP Version Not Supported");
					closeStreams();
					return;
				}

				// Get path and parameters
				String requestedPath = firstLine[1];

				String[] splitReq = requestedPath.split("\\?");
				String path = splitReq[0];
				if (splitReq.length > 1) {
					String paramString = splitReq[1];
					parseParameters(paramString);
				}

				internalDispatchRequest(path, true);

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					closeStreams();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Closes socket and flushes output stream.
		 * 
		 * @throws IOException
		 */
		private void closeStreams() throws IOException {
			ostream.flush();
			csocket.close();
		}

		/**
		 * Reads host from headers (if no host header then sets host to domain name set
		 * in server.properties)
		 * 
		 * @param requestHeaders headers from the request.
		 */
		private void getHost(List<String> requestHeaders) {
			for (String header : requestHeaders) {
				if (header.startsWith("Host:")) {
					host = header.split(":")[1];
					break;
				}
			}
			if (host == null) {
				host = domainName;
			}

		}

		/**
		 * Reads headers from the request.
		 * 
		 * @return headers list from the request, {@code null} on error.
		 * @throws IOException if there is an error while reading from input stream.
		 */
		private List<String> readRequest() throws IOException {
			byte[] request = readRequest(istream);
			if (request == null) {
				sendError(400, "Bad request");
				return null;
			}

			String requestStr = new String(request, StandardCharsets.US_ASCII);

			List<String> headers = extractHeaders(requestStr);

			if (headers.size() < 1) {
				sendError(400, "Bad request");
				return null;
			}

			return headers;
		}

		/**
		 * Reads the request from the input stream.
		 * 
		 * @param is input stream.
		 * @return byte array gotten from the input.
		 * @throws IOException if there is an error while reading from input stream.
		 */
		private byte[] readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Checks if there is an open session already.
		 * 
		 * @param requestHeaders headers of the request.
		 */
		private void checkSession(List<String> requestHeaders) {
			String sidCandidate = null;
			for (String header : requestHeaders) {
				if (!header.startsWith("Cookie:"))
					continue;

				// Is there a sid in cookies?
				String[] cookies = header.substring(7).split(";");

				for (int i = 0; i < cookies.length; i++) {
					String[] cookie = cookies[i].split("=");
					if (cookie[0].trim().equals("sid")) {
						sidCandidate = cookie[1].trim().replace("\"", "");
						break;
					}
				}
			}

			// Create new session or get data from existing one
			synchronized (SmartHttpServer.this) {

				if (sidCandidate == null) {
					createNewSID();
					return;
				}

				SessionMapEntry entry = sessions.get(sidCandidate);

				if (entry == null || !entry.host.equals(host)) {
					createNewSID();
					return;
				}

				if (entry.validUntil <= System.currentTimeMillis() / timeDivider) {
					sessions.remove(sidCandidate);
					createNewSID();
					return;
				}

				entry.validUntil = System.currentTimeMillis() / timeDivider + sessionTimeout;
				permPrams = entry.map;
			}

		}

		/**
		 * Creates new ID for current session.
		 */
		private void createNewSID() {
			StringBuilder id = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				id.append(letters.charAt(sessionRandom.nextInt(letters.length())));
			}
			long validUntil = System.currentTimeMillis() / timeDivider + sessionTimeout;
			SessionMapEntry entry = new SessionMapEntry(id.toString(), host, validUntil,
					Collections.synchronizedMap(new HashMap<>()));
			String tempId = id.toString();
			sessions.put(tempId, entry);
			outputCookies.add(new RCCookie("sid", tempId, null, host, "/"));
			permPrams = entry.map;
		}

		/**
		 * Parse parameters form the given {@code paramString}.
		 * 
		 * @param paramString string that contains parameters.
		 */
		private void parseParameters(String paramString) {
			String[] paramsArr = paramString.split("&");

			for (int i = 0; i < paramsArr.length; i++) {
				String[] param = paramsArr[i].split("=");
				params.put(param[0], param[1]);
			}

		}

		/**
		 * Extracts headers from the given string.
		 * 
		 * @param requestHeader String that contains the headers.
		 * @return List of headers.
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Helper method for sending error responses without a body.
		 * 
		 * @param statusCode status code of the response.
		 * @param statusText status message of the response.
		 * @throws IOException if there is an error while writing to the output stream.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);

		}

		/**
		 * Internal method to dispatch requests.
		 * 
		 * @param urlPath    path to root directory from which we serve files
		 * @param directCall determines is it a direct call to the method.
		 * @throws Exception if there is an error while dispatching.
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
			}

			if (urlPath.startsWith("/ext")) {
				instanceWorker("hr.fer.zemris.java.webserver.workers." + urlPath.substring(5)).processRequest(context);
				return;
			}

			if (workersMap.containsKey(urlPath)) {
				workersMap.get(urlPath).processRequest(context);
				return;
			}

			String[] fileNamePath = urlPath.split("\\.");
			String extension = fileNamePath[fileNamePath.length - 1];

			Path reqPath;
			if (urlPath.startsWith("/private")) {
				if (directCall) {
					sendError(404, "Not Found");
					return;
				}
				reqPath = documentRoot.resolve(urlPath.substring(1));
			} else {
				reqPath = extension.equals("smscr") ? documentRoot.getParent().resolve(urlPath.substring(1))
						: documentRoot.resolve(urlPath.substring(1));
			}

			if (!reqPath.startsWith(documentRoot.getParent())) {
				sendError(403, "Forbidden");
				return;
			}

			if (!Files.exists(reqPath) || !Files.isRegularFile(reqPath) || !Files.isReadable(reqPath)) {
				sendError(404, "Not Found");
				return;
			}

			// If it's a script
			if (extension.equals("smscr")) {
				byte[] input = Files.readAllBytes(reqPath);

				new SmartScriptEngine(new SmartScriptParser(new String(input)).getDocumentNode(), context).execute();

			} else {

				String type = null;
				for (Map.Entry<String, String> mime : mimeTypes.entrySet()) {
					if (mime.getKey().equals(extension)) {
						type = mime.getValue();
					}
				}
				if (type == null) {
					type = "application/octet-stream";
				}

				context.setMimeType(type);
				context.setStatusCode(200);
				byte[] input = Files.readAllBytes(reqPath);
				context.write(input);

			}

		}

	}

	/**
	 * One map entry of the session.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class SessionMapEntry {

		/** Session ID. */
		String sid;
		/** Host of the session. */
		String host;
		/** Session is valid until this. */
		long validUntil;
		/** Map entry. */
		Map<String, String> map;

		/**
		 * Constructor.
		 * 
		 * @param sid        Session ID.
		 * @param host       Host of the session.
		 * @param validUntil Session is valid until this.
		 * @param map        Map entry.
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}

	}

	/**
	 * Daemon thread that checks for expired sessions every 5 minutes and cleans
	 * them if there is any.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	public class CleanerThread extends Thread {

		/**
		 * Constructor, sets this thread to daemon.
		 */
		public CleanerThread() {
			this.setDaemon(true);
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
				}

				synchronized (SmartHttpServer.this) {
					Iterator<Entry<String, SessionMapEntry>> it = sessions.entrySet().iterator();

					while (it.hasNext()) {
						if (it.next().getValue().validUntil <= System.currentTimeMillis() / timeDivider) {
							it.remove();
						}
					}
				}
			}
		}
	}

	/**
	 * Main method that starts the program. Accepts one argument - path to
	 * properties.
	 * 
	 * @param args Command line arguments. One is accepted - path to properties.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected one argument - path to properties!");
			return;
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();

	}

}
