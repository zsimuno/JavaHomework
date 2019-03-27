/**
 * 
 */
package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.*;
/**
 * @author Zvonimir Šimunović
 *
 */
public class SmartScriptTester {

	/** TODO javadoc
	 * @param args
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
	 * @param document
	 * @return
	 */
	private static String createOriginalDocumentBody(Node node) {
		String originalBody = "";
		for(int i = 0; i < node.numberOfChildren(); ++i) {
			Node child = node.getChild(i);
			String childString;
			if(child instanceof TextNode) {
				childString = child.toString().replace("\\", "\\\\").replace("{", "\\{");
			} else {
				childString = child.toString();
			}
			originalBody += childString + createOriginalDocumentBody(child);
			if(child instanceof ForLoopNode) {
				originalBody += "{$END$}";
			}
		}
		return originalBody;
	}

	/**
	 * @param docBody
	 */
	private static void documentTester(String docBody) {
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String secondDocumentBody = createOriginalDocumentBody(document2);
		System.out.println(originalDocumentBody.equals(secondDocumentBody));
		// now document and document2 should be structurally identical trees
	}

	
	
	/**
	 * @param node
	 * @return
	 */
	private static String documentTree(Node node) {
		return documentTree(node, "");
	}
	
	/**
	 * @param node
	 * @param tabs
	 * @return
	 */
	private static String documentTree(Node node, String tabs) {
		String tree = "";
		if(node instanceof DocumentNode) {
			tree += "DocumentNode\n";
		}
		for(int i = 0; i < node.numberOfChildren(); ++i) {
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
