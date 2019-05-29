package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
	/** Map of web workers. */
	private Map<String, IWebWorker> workersMap = new HashMap<>();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		address = properties.get("server.address").toString();
		domainName = properties.get("server.domainName").toString();
		port = Integer.parseInt(properties.get("server.port").toString());
		workerThreads = Integer.parseInt(properties.get("server.workerThreads").toString());
		documentRoot = Paths.get(properties.get("server.documentRoot").toString());
		sessionTimeout = Integer.parseInt(properties.get("session.timeout").toString());

		String mimeConfig = properties.get("server.mimeConfig").toString();
		Properties mimeProps = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get(mimeConfig))) {
			mimeProps.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mimeProps.forEach((t, u) -> {
			mimeTypes.put(t.toString(), u.toString());
		});

		String workers = properties.get("server.workers").toString();
		Properties workersProps = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get(workers))) {
			workersProps.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	@SuppressWarnings("deprecation")
	private IWebWorker instanceWorker(Object fqcn) {
		Class<?> referenceToClass;
		Object newObject = null;
		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(fqcn.toString());
			newObject = referenceToClass.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
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
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}

	}

	/**
	 * Stop the server running and shut down the thread pool.
	 */
	protected synchronized void stop() {
		serverThread.stopServer(); // TODO dobro?
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
				serverSocket.bind(new InetSocketAddress((InetAddress) null, port));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (true) {
				if (stopRequested)
					break;
				Socket client = null;
				try {
					client = serverSocket.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ClientWorker cw = new ClientWorker(client);
				threadPool.submit(cw);
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

		/** Client scoket. */
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

				List<String> request = readRequest();

				if (request.size() < 1) {
					sendError(400, "Bad request");
					return;
				}

				String[] firstLine = request.get(0).split(" ");

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(405, "Method Not Allowed");
					return;
				}

				String requestedPath = firstLine[1];

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(400, "HTTP Version Not Supported");
					return;
				}

				for (String header : request) {
					if (header.startsWith("Host:")) {
						host = header.split(":")[1];
						break;
					}
				}
				if (host == null) {
					host = domainName;
				}

				String[] splitReq = requestedPath.split("\\?");
				String path = splitReq[0];
				if (splitReq.length > 1) {
					String paramString = splitReq[1];
					parseParameters(paramString);
				}

				internalDispatchRequest(path, true);

			} catch (IOException e) {
				try {
					sendError(500, "Internal Server Error");
				} catch (IOException e1) {
					System.out.println("Internal error: " + e1.getMessage());
				}
				return;

			} catch (Exception ex) {
				ex.printStackTrace();

			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					System.out.println("Cannot close the socket: " + e.getMessage());
				}
			}
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
		 * Reads headers from the request.
		 * 
		 * @return headers list from the request.
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
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}

			if (urlPath.startsWith("/ext")) {
				instanceWorker("hr.fer.zemris.java.webserver.workers." + urlPath.substring(5)).processRequest(context);
				closeStreams();
				return;
			}

			if (workersMap.containsKey(urlPath)) {
				workersMap.get(urlPath).processRequest(context);
				closeStreams();
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

			closeStreams();
		}

		/**
		 * Closes input and output streams.
		 * 
		 * @throws IOException
		 */
		private void closeStreams() throws IOException {
			istream.close();
			ostream.flush();
			ostream.close();
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
