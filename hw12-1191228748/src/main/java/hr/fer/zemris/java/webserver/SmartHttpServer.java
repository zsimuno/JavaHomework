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

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * TODO SmartHttpServer javadoc
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SmartHttpServer implements IDispatcher {
	/** */
	private String address;
	/** */
	private String domainName;
	/** */
	private int port;
	/** */
	private int workerThreads;
	/** */
	private int sessionTimeout;
	/** */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/** */
	private ServerThread serverThread;
	/** */
	private ExecutorService threadPool;
	/** */
	private Path documentRoot;

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
		// TODO sto sa ovim props?

	}

	/**
	 * 
	 */
	protected synchronized void start() {
		// … start server thread if not already running …
		// … init threadpool by Executors.newFixedThreadPool(...); …

		if (serverThread == null || !serverThread.isAlive()) {
			serverThread = new ServerThread();
			serverThread.start();
			threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // TODO izvan ili
																									// unutar ifa?
		}

	}

	/**
	 * 
	 */
	protected synchronized void stop() {
		// … signal server thread to stop running …
		// … shutdown threadpool …
		serverThread.stopServer(); // TODO dobro?
		threadPool.shutdown();
	}

	/**
	 * @author Zvonimir Šimunović
	 *
	 */
	protected class ServerThread extends Thread {

		/** Is stopping requested from the server thread. */
		private volatile boolean stopRequested = false;

		@Override
		public void run() {
			// given in pesudo-code:
			// open serverSocket on specified port
			// while(true) {
			// Socket client = serverSocket.accept();
			// ClientWorker cw = new ClientWorker(client);
			// submit cw to threadpool for execution
			// }
			@SuppressWarnings("resource")
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
	 * @author Zvonimir Šimunović
	 *
	 */
	private class ClientWorker implements Runnable {

		/** */
		private Socket csocket;
		/** */
		private PushbackInputStream istream;
		/** */
		private OutputStream ostream;
		/** */
		private String version;
		/** */
		private String method;
		/** */
		private String host;
		/** */
		private Map<String, String> params = new HashMap<String, String>();
		/** */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/** */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/** */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/** */
		private String SID;

		/**
		 * @param csocket
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
				Path reqPath = documentRoot.resolve(path.substring(1));
				if (!reqPath.startsWith(documentRoot)) {
					sendError(403, "Forbidden");
					return;
				}

				if (!Files.exists(reqPath) || !Files.isRegularFile(reqPath) || !Files.isReadable(reqPath)) {
					sendError(404, "Not Found");
					return;
				}
				String[] fileNamePath = reqPath.getFileName().toString().split("\\.");
				String extension = fileNamePath[fileNamePath.length - 1];

				String type = null;
				for (Map.Entry<String, String> mime : mimeTypes.entrySet()) {
					if (mime.getKey().equals(extension)) {
						type = mime.getValue();
					}
				}
				if (type == null) {
					type = "application/octet-stream";
				}

				RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
				rc.setMimeType(type);
				rc.setStatusCode(200);
				byte[] input = Files.readAllBytes(reqPath);
				rc.write(input);

				istream.close();
				ostream.flush();
				ostream.close();

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
		 * @return
		 * @throws IOException
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

		// Zaglavlje predstavljeno kao jedan string splita po enterima
		// pazeći na višeretčane atribute...
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

		// Pomoćna metoda za slanje odgovora bez tijela...
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected one argument - path to properties!");
			return;
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();

	}

	/**
	 * @param urlPath
	 * @throws Exception
	 */
	@Override
	public void dispatchRequest(String urlPath) throws Exception {
		internalDispatchRequest(urlPath, false);

	}

	/**
	 * @param urlPath
	 * @param directCall
	 * @throws Exception
	 */
	public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
	}


}
