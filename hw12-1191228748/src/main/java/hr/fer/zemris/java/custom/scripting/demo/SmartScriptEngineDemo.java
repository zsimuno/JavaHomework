package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demos the engine ( {@link SmartScriptEngine} ) that executes SmartScript
 * programs.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SmartScriptEngineDemo {

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {

		osnovni();
		System.out.println();
		zbrajanje();
		System.out.println();
		brojPoziva();
		System.out.println();
		fibonacci();
		System.out.println();
		fibonaccih();
	}

	/**
	 * Read file from disk.
	 * 
	 * @param path Path of the file.
	 * @return content of the file.
	 */
	private static String readFromDisk(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get("scripts/" + path)), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			throw new RuntimeException("Error while opening file: " + e1.getMessage());
		}
	}

	/**
	 * Reads and executes "osnovni.smscr".
	 */
	private static void osnovni() {
		String documentBody = readFromDisk("osnovni.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Reads and executes "zbrajanje.smscr".
	 */
	private static void zbrajanje() {
		String documentBody = readFromDisk("zbrajanje.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Reads and executes "brojPoziva.smscr".
	 */
	private static void brojPoziva() {
		String documentBody = readFromDisk("brojPoziva.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}

	/**
	 * Reads and executes "fibonacci.smscr".
	 */
	private static void fibonacci() {
		String documentBody = readFromDisk("fibonacci.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Reads and executes "fibonacci.smscr".
	 */
	private static void fibonaccih() {
		// TODO Implement to write to file?
		String documentBody = readFromDisk("fibonaccih.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

}
