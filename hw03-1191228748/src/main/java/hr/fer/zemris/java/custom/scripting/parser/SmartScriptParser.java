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
	private ObjectStack stack = new ObjectStack();

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

		stack.push(new DocumentNode());
		
		try {
			lexer = new SmartScriptLexer(text);
		} catch (SmartScriptLexerException e) {
			throw new SmartScriptParserException(e.getMessage());
		}
		
		// Get the first token
		nextToken();

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

	/**
	 * Moves lexer to the next token
	 */
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
		while (!isTokenType(SmartScriptTokenType.EOF)) {

			switch (currentTokenType()) {
			case OPENBRACES:
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
	 */
	private void tagParser() {
		nextToken();
		if (!isTokenType(SmartScriptTokenType.DOLLARSIGN)) {
			parserException();
		}
		nextToken();
		if(isTokenType(SmartScriptTokenType.VARIABLE)) {
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
			
		} else if(isTokenType(SmartScriptTokenType.EQUALSSIGN)) {
			parseEquals();
			
		} else {
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

		Element startExpression = getCurrentTagElement();
		nextToken();

		Element endExpression = getCurrentTagElement();
		nextToken();

		Element stepExpression;
		if (isTokenType(SmartScriptTokenType.DOLLARSIGN)) {
			stepExpression = null;
		} else {
			stepExpression = getCurrentTagElement();
			nextToken();
		}

		parseEndOfTag();

		ForLoopNode forLoop = new ForLoopNode(variable, startExpression, endExpression, stepExpression);

		addToTopStackElement(forLoop);

		stack.push(forLoop);

		parse();
	}

	/**
	 * Returns the {@link Element} 
	 * 
	 * @return
	 */
	private Element getCurrentTagElement() {
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
	 * Parses equals tag
	 * 
	 */
	private void parseEquals() {
		nextToken(); // Skip =

		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		while (!isTokenType(SmartScriptTokenType.DOLLARSIGN)) {

			elements.add(getCurrentTagElement());

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
		lexer.setState(SmartScriptLexerState.TEXT);
		nextToken();

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
