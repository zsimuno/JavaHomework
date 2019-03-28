/**
 * 
 */
package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.*;

/**
 * Class that is used to test SmartScript parser and lexer. Accepts one command
 * line argument that is the name of the file that we get the code from.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SmartScriptTester {

	/**
	 * Main method that starts the program
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
//		if (args.length != 1) {
//			System.out.println("Invalid number of command line arguments!");
//			return;
//		}
//
//		String docBody = "";
//		try {
//			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
//		} catch (IOException e1) {
//			System.out.println("Invalid file name!");
//			return;
//		}
		
		String docBody = "    This is sample text.\r\n" + "{$    FOR i 1   10 1 $}\r\n"
				+ "	This is {$= i $}-th time this message is generated.\r\n" + "{$END$}\r\n" + "{$FOR i 0 10 2 $}\r\n"
				+ "	sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + "{$END$}";

		SmartScriptParser parser = null;
		parser = new SmartScriptParser(docBody);
		// TODO Odkomentirat
//		try {
//			parser = new SmartScriptParser(docBody);
//		} catch (SmartScriptParserException e) {
//			System.out.println("Unable to parse document!");
//			System.exit(-1);
//		} catch (Exception e) {
//			System.out.println("If this line ever executes, you have failed this class!");
//			System.exit(-1);
//		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody + "\n"); // should write something like original
		// content of docBody
		System.out.println(documentTree(document));
		documentTester(docBody);
	}

	/**
	 * Recreates the original document body that can be parsable again
	 * 
	 * @param document document that we make the original body of
	 * @return original document body that can be parsable again in {@code String} form
	 */
	private static String createOriginalDocumentBody(Node node) {
		String originalBody = "";
		int childrenCount = node.numberOfChildren();
		
		for (int i = 0; i < childrenCount; ++i) {
			Node child = node.getChild(i);
			
			originalBody += node.getChild(i).toString() + createOriginalDocumentBody(child);
			
			if (child instanceof ForLoopNode) {
				originalBody += "{$END$}";
			}
		}
		return originalBody;
	}

	/**
	 * Tests if the {@code createOriginalDocumentBody} method returns the string that is parsable again
	 * 
	 * @param docBody document body that will be tested
	 */
	private static void documentTester(String docBody) {
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String secondDocumentBody = createOriginalDocumentBody(document2);
		System.out.println(document.equals(document2));
		System.out.println(originalDocumentBody.equals(secondDocumentBody));
		// now document and document2 should be structurally identical trees
	}

	/**
	 * Returns the string of document tree
	 * 
	 * @param node node that we make the string representation of
	 * @return returns the string representation of document tree
	 */
	private static String documentTree(Node node) {
		return documentTree(node, "");
	}

	/**
	 * Returns the string of document tree
	 * 
	 * @param node node that we make the string representation of
	 * @param tabs current tab count (represents the depth in the tree)
	 * @return returns the string representation of document tree
	 */
	private static String documentTree(Node node, String tabs) {
		String tree = "";
		
		if (node instanceof DocumentNode) {
			tree += "DocumentNode\n";
		}
		
		int childrenCount = node.numberOfChildren();
		
		for (int i = 0; i < childrenCount; ++i) {
			Node child = node.getChild(i);
			if (child instanceof EchoNode) {
				tree += tabs + "*-EchoNode = " + child.toString() + "\n";
				
			} else if (child instanceof ForLoopNode) {
				tree += tabs + "*-ForLoopNode = " + child.toString() + "\n" + documentTree(child, tabs + "\t");
				
			} else if (child instanceof TextNode) {
				tree += tabs + "*-TextNode = " + ((TextNode) child).toStringWithoutEscaping() + "\n";
			}
		}
		return tree;
	}
}
