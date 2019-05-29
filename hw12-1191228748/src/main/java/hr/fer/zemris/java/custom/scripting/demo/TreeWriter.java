package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Class that is used parse a SmartScript script into a tree and the reproducing
 * original code. Accepts one command line argument that is the path of the file
 * with the script.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class TreeWriter {
	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments. Accepts only one argument - path to the
	 *             file.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Must have one argument - path to the file.");
			return;
		}

		String docBody;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Error while reading the file!");
			return;
		}

		SmartScriptParser parser;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (Exception e) {
			System.out.println("Error while parsing.");
			return;
		}

		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
	}

	/**
	 * Visitor class that parses the document back into the original script.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.printf(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.printf(node.toString());
			for (int i = 0, n = node.numberOfChildren(); i < n; ++i) {
				node.getChild(i).accept(this);
			}
			System.out.printf("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.printf(node.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, n = node.numberOfChildren(); i < n; ++i) {
				node.getChild(i).accept(this);
			}
		}

	}
}
