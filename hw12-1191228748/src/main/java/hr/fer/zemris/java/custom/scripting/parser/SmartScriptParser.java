/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.SmartScriptTokenType;

/**
 * Parser that we use to parse SmartScript code.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SmartScriptParser {

	/**
	 * Lexer that will be user to get tokens
	 */
	private SmartScriptLexer lexer;

	/**
	 * Stack that we use to make the document tree
	 */
	private Stack<Node> stack = new Stack<>();

	/**
	 * Document node that is parsed
	 */
	private DocumentNode documentNode;

	/**
	 * Constructs a parser and parses given text
	 * 
	 * @param text text that will be parsed
	 * @throws SmartScriptParserException on parse error
	 */
	public SmartScriptParser(String text) {

		stack.push(new DocumentNode());

		// Construct the lexer
		try {
			lexer = new SmartScriptLexer(text);
		} catch (SmartScriptLexerException e) {
			throw new SmartScriptParserException(e.getMessage());
		}

		// Get the first token
		nextToken();

		parse();

		if (stack.size() > 1) {
			throw new SmartScriptParserException("Missing END tags!");
		}

		documentNode = (DocumentNode) stack.pop();
	}

	/**
	 * Returns the document node
	 * 
	 * @return the documentNode
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Adds the node to the top element of the stack as a child node.
	 * 
	 * @param node node that we add to the top node in the stack
	 */
	private void addToTopStackElement(Node node) {
		((Node) stack.peek()).addChildNode(node);
	}

	/**
	 * Moves lexer to the next token
	 * 
	 * @throws SmartScriptParserException on parse error
	 */
	private void nextToken() {
		try {
			lexer.nextSmartScriptToken();
		} catch (SmartScriptLexerException e) {
			parserException(e.getMessage());
		}
	}

	/**
	 * Parses the code
	 * 
	 * @throws SmartScriptParserException on parse error
	 */
	private void parse() {
		while (!isTokenType(SmartScriptTokenType.EOF)) {

			switch (currentTokenType()) {
			case OPENTAG:
				lexer.setState(SmartScriptLexerState.TAG);
				tagParser();
				break;
			case TEXT:
				addToTopStackElement(new TextNode((String) lexer.getToken().getValue()));
				nextToken();
				break;
			default:
				parserException();
				break;
			}

		}
	}

	/**
	 * Parses a tag
	 * 
	 * @throws SmartScriptParserException on parse error
	 */
	private void tagParser() {
		nextToken();
		if (isTokenType(SmartScriptTokenType.VARIABLE)) { // Variable tag name
			switch (currentTokenValue().toString().toUpperCase()) {
			case "FOR":
				parseFor();
				break;
			case "END":
				parseEndTag();
				break;
			default:
				parserException();
				break;
			}

		} else if (isTokenType(SmartScriptTokenType.EQUALSSIGN)) { // Echo
			parseEquals();

		} else {
			parserException();
		}

	}

	/**
	 * Parses for loop
	 * 
	 * @throws SmartScriptParserException on parse error
	 */
	private void parseFor() {
		nextToken(); // Skip FOR

		if (!isTokenType(SmartScriptTokenType.VARIABLE)) {
			parserException("Variable expected! Instead got " + currentTokenValue());
		}

		ElementVariable variable = new ElementVariable((String) currentTokenValue());
		nextToken();

		Element startExpression = tagElementForLoop();
		nextToken();

		Element endExpression = tagElementForLoop();
		nextToken();

		Element stepExpression;
		if (isTokenType(SmartScriptTokenType.CLOSETAG)) {
			stepExpression = null;
		} else {
			stepExpression = tagElementForLoop();
			nextToken();
		}

		parseEndOfTag();

		ForLoopNode forLoop = new ForLoopNode(variable, startExpression, endExpression, stepExpression);

		addToTopStackElement(forLoop);

		stack.push(forLoop);
	}

	/**
	 * Returns the {@link Element} that can be in a for loop
	 * 
	 * @return the {@link Element} that can be in a for loop
	 * @throws SmartScriptParserException on parse error
	 */
	private Element tagElementForLoop() {
		switch (currentTokenType()) {
		case VARIABLE:
			return new ElementVariable((String) currentTokenValue());
		case STRING:
			return new ElementString((String) currentTokenValue());
		case INTEGER:
			return new ElementConstantInteger((Integer) currentTokenValue());
		case DOUBLE:
			return new ElementConstantDouble((Double) currentTokenValue());
		default:
			parserException();
			break;
		}

		return null;
	}

	/**
	 * Parses equals tag
	 * 
	 * @throws SmartScriptParserException on parse error
	 */
	private void parseEquals() {
		nextToken(); // Skip =

		List<Element> elements = new ArrayList<>();
		while (!isTokenType(SmartScriptTokenType.CLOSETAG)) {

			elements.add(tegElementEcho());

			nextToken();
		}

		Element[] elementsArray = new Element[elements.size()];
		for (int i = 0; i < elementsArray.length; i++) {
			elementsArray[i] = (Element) elements.get(i);
		}

		addToTopStackElement(new EchoNode(elementsArray));

		parseEndOfTag();
	}

	/**
	 * Returns the {@link Element} that can be in echo tag
	 * 
	 * @return the {@link Element} that can be in echo tag
	 * @throws SmartScriptParserException on parse error
	 */
	private Element tegElementEcho() {
		switch (currentTokenType()) {
		case VARIABLE:
			return new ElementVariable((String) currentTokenValue());
		case OPERATOR:
			return new ElementOperator((Character) currentTokenValue());
		case FUNCTION:
			return new ElementFunction((String) currentTokenValue());
		case STRING:
			return new ElementString((String) currentTokenValue());
		case INTEGER:
			return new ElementConstantInteger((Integer) currentTokenValue());
		case DOUBLE:
			return new ElementConstantDouble((Double) currentTokenValue());
		default:
			parserException();
			break;
		}

		return null;
	}

	/**
	 * Parses end tag
	 * 
	 * @throws SmartScriptParserException on parse error
	 */
	private void parseEndTag() {
		nextToken(); // Skip END token
		parseEndOfTag();

		stack.pop();
		if (stack.isEmpty()) {
			parserException("Document contains more {$END$} -s than opened non-empty tags!");
		}

	}

	/**
	 * Parses end of a tag (that is "$}" )
	 * 
	 * @throws SmartScriptParserException on parse error
	 */
	private void parseEndOfTag() {
		if (!isTokenType(SmartScriptTokenType.CLOSETAG)) {
			parserException();
		}

		lexer.setState(SmartScriptLexerState.TEXT);
		nextToken();

	}

	/**
	 * Checks if the current token is of the given {@code type}
	 * 
	 * @param type type that is compared with current tokens type
	 * @return {@code true} if current token is of the given {@code type},
	 *         {@code false} if it's not
	 * @throws SmartScriptParserException if can't parse because of lexer error
	 */
	private boolean isTokenType(SmartScriptTokenType type) {
		if (lexer == null || lexer.getToken() == null) {
			parserException("Problem with lexer");
		}
		return lexer.getToken().getType() == type;
	}

	/**
	 * Returns the value of the current token.
	 * 
	 * @return value of the current token.
	 */
	private Object currentTokenValue() {
		return lexer.getToken().getValue();
	}

	/**
	 * Returns the type of the current token.
	 * 
	 * @return type of the current token.
	 */
	private SmartScriptTokenType currentTokenType() {
		return lexer.getToken().getType();
	}

	/**
	 * Throws parser exception with appropriate message
	 * 
	 * @param message message to be show
	 * @throws SmartScriptParserException always
	 */
	private void parserException(String message) {
		throw new SmartScriptParserException(message);
	}

	/**
	 * Throws parser exception with appropriate message
	 * 
	 * @throws SmartScriptParserException always
	 */
	private void parserException() {
		parserException("Invalid token: " + currentTokenValue());
	}
}
