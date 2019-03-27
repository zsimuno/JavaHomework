/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
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
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * TODO javadoc
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
	private ObjectStack stack;
	
	/**
	 * Document node that is parsed
	 */
	private DocumentNode documentNode;



	/**
	 * Constructs a parser and parses given text
	 * 
	 * @param text
	 */
	public SmartScriptParser(String text) {
		// TODO In this constructor, parser should create an instance of lexer and
		// initialize
		// it with obtained text.
		ObjectStack stack = new ObjectStack();
		stack.push(new DocumentNode());
		try {
			lexer = new SmartScriptLexer(text);
		} catch (SmartScriptLexerException e) {
			throw new SmartScriptParserException(e.getMessage());
		}

		parse();
		
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

	private void nextToken() {
		try {
			lexer.nextSmartScriptToken();
		} catch (SmartScriptLexerException e) {
			parserException(e.getMessage());
		}
	}

	/**
	 * 
	 */
	private void parse() {
		while (isTokenType(SmartScriptTokenType.EOF)) {

			switch (currentTokenType()) {
			case OPENBRACES:
				lexer.setState(SmartScriptLexerState.TAG);
				tagParser();
				break;
			case TEXT:
				addToTopStackElement(new TextNode((String) lexer.getToken().getValue()));
				break;
			default:
				parserException();
				break;
			}

		}
	}

	/**
	 * Parses a tag
	 */
	private void tagParser() {
		nextToken();
		if (!isTokenType(SmartScriptTokenType.DOLLARSIGN)) {
			parserException();
		}
		nextToken();
		switch (currentTokenType()) {
		case FOR:
			parseFor();
			break;
		case EQUALSSIGN:
			parseEquals();
			break;
		case END:
			parseEndTag();
			break;
		default:
			parserException();
		}
	}

	/**
	 * Parses for loop
	 */
	private void parseFor() {
		nextToken(); // Skip FOR

		if (!isTokenType(SmartScriptTokenType.VARIABLE)) {
			parserException("Variable expected! Instead got " + currentTokenValue());
		}

		ElementVariable variable = new ElementVariable((String) currentTokenValue());
		nextToken();

		Element startExpression = getForLoopElement();
		nextToken();

		Element endExpression = getForLoopElement();
		nextToken();

		Element stepExpression;
		if (isTokenType(SmartScriptTokenType.DOLLARSIGN)) {
			stepExpression = null;
		} else {
			stepExpression = getForLoopElement();
			nextToken();
		}

		parseEndOfTag();

		ForLoopNode forLoop = new ForLoopNode(variable, startExpression, endExpression, stepExpression);

		addToTopStackElement(forLoop);

		stack.push(forLoop);

		parse();
	}

	/**
	 * Returns the element of a for loop
	 * 
	 * @return
	 * @throws SmartScriptParserException if there is an error in parsing
	 */
	private Element getForLoopElement() {
		if (isTokenType(SmartScriptTokenType.DOUBLE)) {
			return new ElementConstantInteger((Integer) currentTokenValue());
		} else if (isTokenType(SmartScriptTokenType.INTEGER)) {
			return new ElementConstantDouble((Double) currentTokenValue());
		} else {
			parserException();
		}
		return null;
	}

	/**
	 * Parses equals tag
	 * 
	 */
	private void parseEquals() {
		nextToken(); // Skip =

		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		while (!isTokenType(SmartScriptTokenType.DOLLARSIGN)) {
			switch (currentTokenType()) {
			case VARIABLE:
				elements.add(new ElementVariable((String) currentTokenValue()));
				break;
			case OPERATOR:
				elements.add(new ElementOperator((Character) currentTokenValue()));
				break;
			case FUNCTION:
				elements.add(new ElementFunction((String) currentTokenValue()));
				break;
			case STRING:
				elements.add(new ElementString((String) currentTokenValue()));
				break;
			default:
				parserException();
				break;
			}
			nextToken();
		}

		addToTopStackElement(new EchoNode((Element[]) elements.toArray()));

		parseEndOfTag();
	}

	/**
	 * Parses end tag
	 * 
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
	 * Parses end of a tag (tha is "$}" )
	 */
	private void parseEndOfTag() {
		if (!isTokenType(SmartScriptTokenType.DOLLARSIGN)) {
			parserException();
		}
		nextToken();

		if (!isTokenType(SmartScriptTokenType.CLOSEDBRACES)) {
			parserException();
		}
		nextToken();
		
		lexer.setState(SmartScriptLexerState.TEXT);
	}

	/**
	 * Checks if the current token is of the given {@code type}
	 * 
	 * @param type type that is compared with current tokens type
	 * @return {@code true} if current token is of the given {@code type},
	 *         {@code false} if it's not
	 */
	private boolean isTokenType(SmartScriptTokenType type) {
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
